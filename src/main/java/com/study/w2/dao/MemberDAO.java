package com.study.w2.dao;


import com.study.w2.domain.MemberVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    // mid와 mpw를 이용해서 member 조회하기
    public MemberVO getWithPassword(String mid, String mpw) throws Exception {

        String sql = "select mid, mpw, mname from tbl_member where mid = ? and mpw = ?";

        MemberVO memberVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, mid);
        preparedStatement.setString(2, mpw);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        memberVO = MemberVO.builder()
                .mid(resultSet.getString("mid"))    // "mid" 대신 컬럼 인덱스인 1 으로 적어도 됨.
                .mpw(resultSet.getString("mpw"))
                .mname(resultSet.getString("mname"))
                .build();

        return memberVO;

    }
}
