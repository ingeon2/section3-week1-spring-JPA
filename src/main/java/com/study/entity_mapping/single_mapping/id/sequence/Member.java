package com.study.entity_mapping.single_mapping.id.sequence;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Member { //SEQUENCE 기본키 생성 전략 설정
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // (1)
    private Long memberId;

    public Member(Long memberId) {
        this.memberId = memberId;
    }
}
