package com.study.jdbcex2.service;


import com.study.jdbcex2.TodoDTO;
import com.study.jdbcex2.dao.TodoDAO;
import com.study.jdbcex2.domain.TodoVO;
import com.study.jdbcex2.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {

    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService() {
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    // TodoDTO를 파라미터로 받아서 TodoVO로 변환하는 과정이 필요하다.
    // ModelMapper로 처리된 TodoVO를 sout을 이용해 확인한다.
    // TodoDAO를 이용해 insert()를 실행하고 TodoVO를 등록한다.
    public void register(TodoDTO todoDTO) throws Exception {
        // TodoDTO 객체인 todoDTO를 TodoVO 클래스로 변환한다.
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
//        System.out.println("todoVO: " + todoVO);
        log.info(todoVO);

        // int를 반환하므로 이를 이용해 예외 처리도 가능
        dao.insert(todoVO);
    }

    public List<TodoDTO> listAll() throws Exception {

        List<TodoVO> voList = dao.selectAll();

        log.info("voList..........");
        log.info(voList);

        // voList를 스트림으로 변환하고
        // 각 TodoVO 객체를 modelMapper.map() 메서드로 TodoDTO 객체로 변환한다.
        // 변환된 TodoDTO 객체들은 스트림의 요소로 매핑된다.
        // collec()로 스트림의 요소를 리스트로 수집한다.
        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;

    }

    public TodoDTO get(Long tno) throws Exception {

        log.info("tno: " + tno);

        TodoVO todoVO = dao.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);

        return todoDTO;

    }

    public void modify(TodoDTO todoDTO) throws Exception {

        log.info("todoDTO:" + todoDTO);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        dao.UpdateOne(todoVO);

    }

    public void remove(Long tno) throws Exception {

        log.info("tno: " + tno);

        dao.deleteOne(tno);

    }

}
