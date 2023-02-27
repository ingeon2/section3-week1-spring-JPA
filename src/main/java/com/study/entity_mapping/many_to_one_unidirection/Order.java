package com.study.entity_mapping.many_to_one_unidirection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ORDERS")
public class Order { //다(N)에 해당하는 Order 클래스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING) //아래의 이눔에서 가져온것.
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne // 다대일의 연관관계 매핑, 다대일의 관계를 명시
    @JoinColumn(name = "MEMBER_ID") // ORDERS 테이블에서 외래키에 해당하는 컬럼명
    //일반적으로 부모 테이블에(MEMBER)서 기본키로 설정된 컬럼명과 동일하게 외래키 컬럼을 만듦.
    //여기서는 MEMBER 테이블의 기본키 컬럼명이 “MEMBER_ID” 이기 때문에 동일하게 적음.
    private Member member;
    public void addMember (Member member) { //오더에 멤버 추가해주는 매서드(이해하기)
        this.member = member;
    }

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
