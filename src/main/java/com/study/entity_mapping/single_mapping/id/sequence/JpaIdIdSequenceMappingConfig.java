package com.study.entity_mapping.single_mapping.id.sequence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Profile("id-sequence")
@EntityScan(basePackageClasses = {JpaIdIdSequenceMappingConfig.class})
@Configuration
public class JpaIdIdSequenceMappingConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaSingleMappingRunner(EntityManagerFactory emFactory){
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            tx.begin();
            em.persist(new Member());
            Member member = em.find(Member.class, 1L);
            System.out.println("# memberId: " + member.getMemberId());
            tx.commit();

        };
    }
}

//결과
//Hibernate: drop table if exists member CASCADE
//Hibernate: drop sequence if exists hibernate_sequence

//데이터베이스에 시퀀스를 생성
//Hibernate: create sequence hibernate_sequence start with 1 increment by 1
//Hibernate: create table member (member_id bigint not null, primary key (member_id))

//Member 엔티티를 영속성 컨텍스트에 저장하기 전에 데이터베이스에서 시퀀스 값을 조회
//Hibernate: call next value for hibernate_sequence
//# memberId: 1
//Hibernate: insert into member (member_id) values (?)