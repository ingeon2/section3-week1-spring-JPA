package com.study.entity_mapping.single_mapping.table;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "USERS") //JPA 관리 대상 엔티티를 위한 에너테이션
@Table(name = "USERS")
//테이블과 엔티티명을 직접 지을 수 있다는것을 보여주기 위한 것
public class Member { //엔티티와 테이블 간의 매핑
    @Id
    private Long memberId;

    public Member(Long memberId) {
        this.memberId = memberId;
    }
}

//@Table 애너테이션은 옵션이지만 @Entity 애너테이션과 @Id 애너테이션은 필수.
//@Entity 애너테이션과 @Id 애너테이션은 함께 사용해야함.