package com.study.w2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
// getter, setter, toString, equals, hashCode, requiredArgsConstructor를 모두 컴파일할 때 생성해준다.
@Data
// @AllArgs.., @NoArgs.. 파라미터가 없는 기본 생성자와 모든 필드값이 필요한 생성자를 만든다.
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String mid;
    private String mpw;
    private String mname;
    private String uuid;

}
