package com.study.basic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Profile("basic")
@EntityScan(basePackageClasses = {JpaBasicConfig.class})
@Configuration
//@Configuration 애너테이션을 추가하면 Spring에서 Bean 검색 대상인 Configuration 클래스로 간주해서
//아래와 같이 @Bean 애너테이션이 추가된 메서드를 검색한 후, 해당 메서드에서 리턴하는 객체를 Spring Bean으로 추가
public class JpaBasicConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) { //구동 시점에 실행되는 코드가 자바 문자열 아규먼트 배열에 접근해야할 필요가 있는 경우에 사용 (즉, 로그 설명과 같음.ㄴ)
        //JPA의 영속성 컨텍스트는 EntityManager 클래스에 의해서 관리되는데 이 EntityManager 클래스의 객체는 위와 같이 EntityManagerFactory 객체를 Spring으로부터 DI받기 가능.

        this.em = emFactory.createEntityManager(); //EntityManagerFactory의 createEntityManager() 메서드를 이용해서 EntityManager 클래스의 객체를 얻음.
        this.tx = em.getTransaction(); //EntityManager를 통해서 Transaction 객체를 얻음. JPA에서는 이 Transaction 객체를 기준으로 데이터베이스의 테이블에 데이터를 저장.
        
        return args -> {
            example02();
        };
    }

    private void example01() { //영속성 컨텍스트에 엔티티 저장
        Member member = new Member("hgd@gmail.com");
        em.persist(member);

        //영속성 컨텍스트에 member 객체가 잘 저장되었는지 find(Member.class, 1L) 메서드로 조회
        // (★데이터베이스가 아닌 영속성 컨텍스트에서 조회)
        //이후에 영속성 컨텍스트에 없으면 DB로 간다.
        Member resultMember = em.find(Member.class, 1L); //첫 번째 파라미터는 조회 할 엔티티 클래스의 타입, 두 번째 파라미터는 조회 할 엔티티 클래스의 식별자 값.
        System.out.println("Id: " + resultMember.getMemberId() + ", email: " + resultMember.getEmail());
    }

    private void example02() { //영속성 컨텍스트와 테이블에 엔티티 저장.
        tx.begin();
        Member member = new Member("hgd@gmail.com");

        em.persist(member);

        tx.commit();

        Member resultMember1 = em.find(Member.class, 1L);
        System.out.println("Id: " + resultMember1.getMemberId() + ", email: " + resultMember1.getEmail());

        Member resultMember2 = em.find(Member.class, 2L);
        System.out.println(resultMember2 == null);
        //여기서 중요한것! 영속성 저장은 member 객체밖에 안했다! 리절트멤버2는 못찾아 그러니까 리절트멤버2는 널값이 맞지.
        //영속성 컨텍스트 + DB 에서 식별자 값이 2L인 member 객체가 존재하지 않기 때문에 테이블에 직접 SELECT 쿼리를 전송.(꼭 이해하기!!!!!!!!!)

    }


    //em.persist()를 호출하면 영속성 컨텍스트의 1차 캐시에 엔티티 클래스의 객체가 저장되고, 쓰기 지연 SQL 저장소에 INSERT 쿼리가 등록된다.
    //tx.commit()을 하는 순간 쓰기 지연 SQL 저장소에 등록된 INSERT 쿼리가 실행되고, 실행된 INSERT 쿼리는 쓰기 지연 SQL 저장소에서 제거된다.
    //em.find()를 호출하면 먼저 1차 캐시에서 해당 객체가 있는지 조회하고, 없으면 테이블에 SELECT 쿼리를 전송해서 조회한다.


    public void example03 () { //쓰기 지연을 통한 영속성 컨텍스트와 테이블에 엔티티 일괄 저장

        tx.begin(); //JPA에서는 Transaction을 시작하기 위해서 tx.begin() 메서드를 먼저 호출해 주어야 함.

        Member member1 = new Member("hgd1@gmail.com");
        Member member2 = new Member("hgd2@gmail.com");

        em.persist(member1); //persist(member) 메서드를 호출하면 영속성 컨텍스트에 member 객체의 정보들이 저장
        em.persist(member2);

        tx.commit(); //영속성 컨텍스트에 저장되어 있는 member 객체를 데이터베이스의 테이블에 저장.
        //member에 대한 INSERT 쿼리가 커밋에 의해 실행되어 영속성 컨텍스트에 있는(JPA의 P) 쓰기 지연 SQL 저장소에서 사라짐.
        //tx.commit()을 하기 전까지는 em.persist()를 통해 쓰기 지연 SQL 저장소에 등록된 INSERT 쿼리가 실행이 되지 않음.
        //따라서 테이블에 데이터가 저장이 되지 않음.
        
    }

    private void example04() { //영속성 컨텍스트와 테이블에 엔티티 업데이트
                                //테이블에 이미 저장되어 있는 데이터를 JPA를 이용해서 어떻게 업데이트 할 수 있는지
        tx.begin();
        em.persist(new Member("hgd1@gmail.com"));
        tx.commit();


        tx.begin();
        Member member1 = em.find(Member.class, 1L);
        member1.setEmail("hgd1@yahoo.co.kr"); //중요한 사실은 em.update() 같은 JPA API가 있을 것 같지만 이와 같이 setter 메서드로 값을 변경하기만 하면 업데이트 로직은 완성
        //set이 UPDATE 쿼리문 역할.
        tx.commit();
    }

    private void example05() { //영속성 컨텍스트와 테이블의 엔티티 삭제
        tx.begin();
        em.persist(new Member("hgd1@gmail.com"));
        tx.commit();

        tx.begin();
        Member member = em.find(Member.class, 1L);
        em.remove(member); //영속성 컨텍스트의 1차 캐시에 있는 엔티티를 제거를 요청 (쓰기 지연 SQL 저장소에 DELETE 쿼리 저장. persist는 INSERT쿼리 라고 볼 수 있음)
        tx.commit(); //tx.commit()을 실행하면 영속성 컨텍스트의 1차 캐시에 있는 엔티티를 제거하고, 쓰기 지연 SQL 저장소에 등록된 DELETE 쿼리가 실행
    }

}
