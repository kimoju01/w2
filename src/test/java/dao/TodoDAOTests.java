package dao;

import com.study.jdbcex2.dao.TodoDAO;
import com.study.jdbcex2.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
    private TodoDAO todoDAO;

    // ready()를 통해서 모든 테스트 전에 TodoDAO 타입의 객체를 생성하도록 함
    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    // testTime()을 이용해서 정상 작동 확인
    @Test
    public void testTime() throws Exception {
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testInsert() throws Exception {
        // 빌더 패턴은 생성자와 달리 필요한 만큼만 데이터를 세팅할 수 있다.
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.now())
                // finished 속성은 false로 기본 지정이 되어 있다.
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception {
        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception {
        Long tno = 1L;

        TodoVO vo = todoDAO.selectOne(tno);

        System.out.println(vo);
    }

    @Test
    public void testDeleteOne() throws Exception {
        Long tno = 2L;
        todoDAO.deleteOne(tno);
    }

    @Test
    public void testUpdateOne() throws Exception {
        TodoVO vo = TodoVO.builder()
                .tno(1L)
                .title("Sample Title update...")
                .dueDate(LocalDate.now())
                .finished(true)
                .build();

        todoDAO.UpdateOne(vo);
    }

}
