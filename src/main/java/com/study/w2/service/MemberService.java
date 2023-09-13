package com.study.w2.service;

import com.study.w2.dao.MemberDAO;
import com.study.w2.domain.MemberVO;
import com.study.w2.dto.MemberDTO;
import com.study.w2.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {

    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService() {
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception {

        MemberVO memberVO = dao.getWithPassword(mid, mpw);
        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;

    }

    public void updateUuid(String mid, String uuid) throws Exception {

        dao.updateUuid(mid, uuid);

    }

    // uuid 값으로 사용자 찾기
    public MemberDTO getByUuid(String uuid) throws Exception {

        MemberVO memberVO = dao.selectUuid(uuid);
        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;
    }

}
