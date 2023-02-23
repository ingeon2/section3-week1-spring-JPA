package com.study.entity_mapping.single_mapping.id.direct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration //스프링에서 중심 역할 하겠다! (Bean 검색 대상인 Configuration 클래스로 간주)
public class JpaIdDirectMappingConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaSingleMappingRunner(EntityManagerFactory emFactory){
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            tx.begin();
            em.persist(new Member(1L));
            //기본키를 직접 할당해서 엔티티를 저장하는것.
            //기본키 없이 엔티티를 저장하면 아래와 같은 에러 메시지를 출력됨.
            tx.commit();
            Member member = em.find(Member.class, 1L);

            System.out.println("# memberId: " + member.getMemberId());
        };
    }
}
