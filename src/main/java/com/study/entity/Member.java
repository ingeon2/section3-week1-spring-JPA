package com.study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity //JPA에서 해당 클래스를 엔티티 클래스로 인식하게 하기 위해
public class Member { //영속성 컨텍스트(JPA의 P)에 저장될 엔티티
    @Id //JPA에서 해당 클래스를 엔티티 클래스로 인식하게 하기 위해
    @GeneratedValue //@GeneratedValue 애너테이션은 식별자를 생성해주는 전략을 지정 (위의 노아규먼트일지 아래의 생성자일지)
    private Long memberId;

    private String email;

    public Member (String email){
        this.email = email;
    }
}

//여기까지 작성해주면 JPA가 내부적으로 테이블을 자동 생성하고, 테이블의 기본키를 할당.