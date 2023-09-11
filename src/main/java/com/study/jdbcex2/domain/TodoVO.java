package com.study.jdbcex2.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
// @AllArgs.., @NoArgs.. 파라미터가 없는 생성자와 모든 필드값이 필요한 생성자를 만든다.
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

}
