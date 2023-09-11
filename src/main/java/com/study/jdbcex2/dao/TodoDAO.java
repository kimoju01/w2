package com.study.jdbcex2.dao;

import com.study.jdbcex2.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    public String getTime() {

        String now = null;

        // try-with-resources를 이용해 자동 close() => try() 내의 변수들은 모두 Auto Closeable 인터페이스 구현한 타입이어야함.
        try (Connection connection = ConnectionUtil.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select now()"); // SQL을 DB로 미리 보내기
             ResultSet resultSet = preparedStatement.executeQuery(); // 쿼리 실행해서 반환된 데이터 resultSet에 넣기
        ) {

            resultSet.next();  // 첫 번째 행 데이터 읽을 수 있도록 이동한다.

            now = resultSet.getString(1);  // 1번째 열의 값을 String 타입으로 읽어들이기

        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }

    // try-with-resources 대신 @Cleanup을 이용하는 코드
    public String getTime2() throws Exception {
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String now = resultSet.getString(1);

        return now;
    }

    // TodoVO 객체를 데이터베이스에 추가
    public void insert(TodoVO vo) throws Exception {

        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        try (Connection connection = ConnectionUtil.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            // PreparedStatement는 '?'를 이용해 나중에 전달할 데이터들을 지정한다.
            // 인덱스 번호는 0이 아닌 1부터 시작한다. '?'가 3개 있으므로 3개의 setXXX을 지정해야한다.
            preparedStatement.setString(1, vo.getTitle());
            preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
            preparedStatement.setBoolean(3, vo.isFinished());

            // '?'들 먼저 채우고 실행
            // executeUpdate()는 DML을 실행하고 결과를 int 타입으로 반환. '몇 개의 행이 영향을 받았는가?'
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 데이터베이스 내의 모든 데이터 가져오기
    public List<TodoVO> selectAll() throws Exception {

        String sql = "select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // select 문은 쿼리를 실행해야 하기 때문에 executeQuery()를 이용해 ResultSet을 구한다.
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();

        // ResultSet으로 각 행(row)을 이동하면서 각 행의 데이터를 TodoVO로 변환한다.
        // next()의 결과는 이동할 수 있는 행이 존재하면 true, 아닌 경우엔 false를 반환한다.
        while (resultSet.next()) {
            TodoVO vo = TodoVO.builder()
                    // getXXX()는 칼럼의 인덱스 번호(1부터)를 이용하거나 칼럼의 이름을 지정해서 가져올 수 있다.
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo);
        }

        return list;
    }

    // 특정한 번호의 데이터만 가져오기
    public TodoVO selectOne(Long tno) throws Exception {

        String sql = "select * from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // setLong(1, ..) -> sql의 첫번째 물음표라는 뜻
        preparedStatement.setLong(1, tno);

        // '?' 먼저 채우고 쿼리 실행
        // ResultSet을 try로 한번 더 묶어야 해서 가독성이 안 좋아져 @Cleanup으로 처리
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }

    // 삭제 기능
    public void deleteOne(Long tno) throws Exception {

        String sql = "delete from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        // 쿼리가 아니라 DML이기 때문에 Update()
        preparedStatement.executeUpdate();

    }

    // 수정 기능
    // 파라미터로 모든 정보가 담겨있는 TodoVO를 받아서 실행한다.
    public void UpdateOne(TodoVO vo) throws Exception {

        String sql = "update tbl_todo set title = ?, dueDate = ?, finished = ? where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isFinished());
        preparedStatement.setLong(4, vo.getTno());

        preparedStatement.executeUpdate();
    }
}
