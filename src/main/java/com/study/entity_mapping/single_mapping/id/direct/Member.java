package com.study.entity_mapping.single_mapping.id.direct;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id //기본키 직접 할당 엔티티 !!!!!!!!!!! 다들 기본키 만드는 전략이 다르거든!
    private Long memberId;

    public Member(Long memberId) {
        this.memberId = memberId;
    }
}
