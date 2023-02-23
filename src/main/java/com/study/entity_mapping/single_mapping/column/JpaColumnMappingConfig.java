package com.study.entity_mapping.single_mapping.column;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaColumnMappingConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaSingleMappingRunner(EntityManagerFactory emFactory){
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
//            testEmailNotNull();   // email 필드에 아무 값도 입력하지 않고 데이터를 저장, 우리가 설정한 email 필드는 nullable=false이기 때문에 에러가 발생
//            testEmailUpdatable(); // 이미 등록한 email 주소를 다시 수정하고 있습니다. 우리가 설정한 email 필드는 updatable=false이기 때문에 이미 등록한 email 주소는 수정되지 않아야 함.
//            testEmailUnique();    // 이미 등록된 emial 주소를 한번 더 등록하고 있습니다. 우리가 설정한 email 필드는 unique=true이기 때문에 에러가 발생
        };
    }

    private void testEmailNotNull() {
        tx.begin();
        em.persist(new Member()); //널값~
        tx.commit();
    }

    private void testEmailUpdatable() {
        tx.begin();
        em.persist(new Member("hgd@gmail.com"));
        Member member = em.find(Member.class, 1L);
        member.setEmail("hgd@yahoo.co.kr");
        tx.commit();
    }

    private void testEmailUnique() {
        tx.begin();
        em.persist(new Member("hgd@gmail.com"));
        em.persist(new Member("hgd@gmail.com"));
        tx.commit();
    }
}

//1 결과
//java.lang.IllegalStateException: Failed to execute CommandLineRunner
//
//Caused by: javax.persistence.PersistenceException:
//                                                   org.hibernate.PropertyValueException:
//							not-null property references a null or transient value :
//				com.study.entity_mapping.single_mapping.column.Member.email

//(페일, 이메일 널값넣어서 일리걸)


//2 결과
// Hibernate: insert into member (member_id, email) values (default, ?)

//INSERT 쿼리가 발생했지만(persist) UPDATE 쿼리가 발생하지 않았습니다(setEmail).
//따라서 updatable=false 설정이 정상적으로 동작.


//3 결과
//`java.lang.IllegalStateException: Failed to execute CommandLineRunner`
//`Caused by: javax.persistence.PersistenceException: `
//org.hibernate.exception.ConstraintViolationException: could not execute statement
//
//Caused by: org.hibernate.exception.ConstraintViolationException:
//could not execute statement
//
//Caused by: org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException:
//Unique index or primary key violation:
//"PUBLIC.UK_MBMCQELTY0FBRVXP1Q58DN57T_INDEX_8 ON PUBLIC.MEMBER(EMAIL NULLS FIRST) VALUES
//( /* 1 */ 'hgd@gmail.com' )"; SQL statement:
//insert into member (member_id, email) values (default, ?) [23505-212]

//unique = true 즉, email 주소는 고유한 값이어야 하는데, 동일한 email 주소가 INSERT 되면서 일리걸.



//위 세개 매서드로 예외 발생과 관련한 테스트를 진행한 것과 마찬가지.