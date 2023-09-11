package com.study.jdbcex2.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public enum MapperUtil {

    INSTANCE;

    private ModelMapper modelMapper;

    // getConfiguration()을 이용해서 private으로 선언된 필드도 접근 가능하도록 설정 변경
    MapperUtil() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)  // 필드 이름을 기반으로 매핑
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // get()으로 ModelMapper 사용할 수 있음
    public ModelMapper get() {
        return modelMapper;
    }
}
