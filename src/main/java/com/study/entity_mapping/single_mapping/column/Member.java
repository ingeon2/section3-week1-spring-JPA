package com.study.entity_mapping.single_mapping.column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member { //@Column 실습 위한 멤버 클래스~
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    // @Column 애너테이션은 필드와 컬럼을 매핑해주는 애너테이션입니다.
    // 그런데 만약 @Column 애너테이션이 없고, 필드만 정의되어 있다면 JPA는 기본적으로 이 필드가 테이블의 컬럼과 매핑되는 필드라고 간주하게됩니다.
    // 또한, @Column 애너테이션에 사용되는 애트리뷰트의 값은 디폴트 값이 모두 적용 (nullable 트루, updatable 트루, unique 트루)
    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    @Column(nullable = false) //회원 정보가 등록 될 때의 시간 및 날짜를 매핑하기 위한 필드
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT") //회원 정보가 수정 될 때의 시간 및 날짜를 매핑하기 위한 필드
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Transient //테이블 컬럼과 매핑하지 않겠다는 의미로 JPA가 인식
    //주로 임시 데이터를 메모리에서 사용하기위한 용도
    private String age;

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

}
