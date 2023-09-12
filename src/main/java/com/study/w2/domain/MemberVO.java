package com.study.w2.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

    private String mid;
    private String mpw;
    private String mname;
    private String uuid;

}
