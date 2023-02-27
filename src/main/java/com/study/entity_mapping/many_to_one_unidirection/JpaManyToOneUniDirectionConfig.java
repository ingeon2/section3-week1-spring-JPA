package com.study.entity_mapping.many_to_one_unidirection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Profile("many-to-one-uni")
@EntityScan(basePackageClasses = {JpaManyToOneUniDirectionConfig.class})
@Configuration
public class JpaManyToOneUniDirectionConfig { //다대일 매핑을 이용한 회원과 주문 정보 저장
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaManyToOneRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            mappingManyToOneBiDirection(); //아래 매서드
        };
    }

    private void mappingManyToOneUniDirection() {
        tx.begin();
        Member member = new Member("hgd@gmail.com", "Hong Gil Dong", "010-1111-1111");

        em.persist(member); //위에서 선언한 member 저장(JPA의 캐시영역에)

        Order order = new Order();
        order.addMember(member); //위에서 선언한 member 에 주문 정보 저장.
        em.persist(order); //order 추가하여 저장.

        tx.commit(); //캐시영역에 저장한얘들 메모리에 저장 (메모리는 뭐 db, ram 둘다가능)

        //제대로 저장되었나 검증하는것. (주문 정보 조회, 주문에 해당하는 회원 정보 출력)
        Order findOrder = em.find(Order.class, 1L);
        System.out.println("findOrder: " + findOrder.getMember().getMemberId()
        + ", " +findOrder.getMember().getEmail());

    }

    public void mappingManyToOneBiDirection() {
        tx.begin();
        Member member = new Member("hgd@gmail.com", "Hong Gil Dong", "010-1111-1111");

        em.persist(member); //위에서 선언한 member 저장(JPA의 캐시영역에)

        Order order = new Order();
        order.addMember(member); //위에서 선언한 member 에 주문 정보 저장.
        em.persist(order); //order 추가하여 저장.

        tx.commit(); //캐시영역에 저장한얘들 메모리에 저장 (메모리는 뭐 db, ram 둘다가능)


        //주문한 회원의 정보를 통해 주문 정보 가져오기.
        //(양방향이라 가능, 이전엔 정보를 오더를 통해서 가져왔었음.)
        Member findMember = em.find(Member.class, 1L);

        findMember
                .getOrders() //멤버에서 가져온 오더가 아래의 파인드오더 객체,
                                //일대다 양방향 관계를 매핑했기 때문에 List<Order> 가져와서 stream
                .stream()
                .forEach(findOrder ->{
                    System.out.println("findOrder: " + findOrder.getOrderId() + ", " + findOrder.getOrderStatus());
                });

    }
}
