package com.study.jdbcex2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data  // getter, setter, toString, equals, hashCode, requiredArgsConstructor를 모두 컴파일할 때 생성해준다.
// @AllArgs.., @NoArgs.. 파라미터가 없는 기본 생성자와 모든 필드값이 필요한 생성자를 만든다.
@NoArgsConstructor
@AllArgsConstructor
// TodoVO와 완전히 같은 구조이지만 어노테이션이 다르다. VO는 setter 없이 주로 읽기 전용 작업을 위해 사용한다.
public class TodoDTO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

}
