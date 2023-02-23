학습 목표
JPA가 무엇인지 이해할 수 있다.
JPA의 동작방식을 이해할 수 있다.
JPA 엔티티에 대한 매핑을 할 수 있다.
JPA 기반의 엔티티 간 연관 관계를 매핑할 수 있다.

JPA(Java Persistence API)는 Java 진영에서 사용하는 ORM(Object-Relational Mapping) 기술의 표준 사양(또는 명세, Specification)입니다.
JPA 표준 사양을 구현한 구현체로는 Hibernate ORM, EclipseLink, DataNucleus 등이 있는데, 우리가 학습할 구현체는 바로 Hibernate ORM입니다.

데이터 저장, 조회 등의 작업은 JPA를 거쳐 JPA의 구현체인 Hibernate ORM을 통해서 이루어지며 Hibernate ORM은 내부적으로 JDBC API를 이용해서 데이터베이스에 접근 (캡처)
따라서 이번 챕터에서는 데이터 액세스 계층 상단에 위치한 JPA에서 지원하는 API를 사용해서 데이터베이스에 접근하는 방법을 학습(캡처)

JPA의 P는 (Persistence) 영속성.
JPA에서는 테이블과 매핑되는 엔티티 객체 정보를 영속성 컨텍스트(Persistence Context)라는 곳에 보관해서 애플리케이션 내에서 오래 지속 되도록 합니다.

영속성 컨텍스트에는 1차 캐시라는 영역과 쓰기 지연 SQL 저장소라는 영역이 있습니다. (캡처 JPA의 P)
JPA API 중에서 엔티티 정보를 영속성 컨텍스트에 저장(persist)하는 API를 사용하면 영속성 컨텍스트의 1차 캐시에 엔티티 정보가 저장

application.yml에서
Spring Data JDBC에서는 schema.sql 파일을 이용해 테이블 생성을 위한 스키마를 직접 지정해 주어야 했지만 JPA에서는 ddl-auto: create의 설정을 추가하면 JPA가 자동으로 데이터베이스에 테이블을 생성

이후엔 엔티티와 config 코드의 주석에 이해되도록 자세하게 설명.

em.persist()를 호출하면 영속성 컨텍스트의 1차 캐시에 엔티티 클래스의 객체가 저장되고, 쓰기 지연 SQL 저장소에 INSERT 쿼리가 등록된다.
tx.commit()을 하는 순간 쓰기 지연 SQL 저장소에 등록된 INSERT 쿼리가 실행되고, 실행된 INSERT 쿼리는 쓰기 지연 SQL 저장소에서 제거된다.
em.find()를 호출하면 먼저 1차 캐시에서 해당 객체가 있는지 조회하고, 없으면 테이블에 SELECT 쿼리를 전송해서 조회한다.

tx.commit()을 하기 전까지는 em.persist()를 통해 쓰기 지연 SQL 저장소에 등록된 INSERT 쿼리가 실행이 되지 않음.
따라서 테이블에 데이터가 저장이 되지 않음.

exampl1 01 부터 계속해서 코드 수정됨. 전말을 보고싶으면 유어클래스 방문하기. ([Spring MVC] JPA 기반 데이터 액세스 계층 - JPA란?) ,그리고 example2 에 코드, 매서드에 대부분의 설명 있음.

핵심 포인트  
JPA(Java Persistence API)는 Java 진영에서 사용하는 ORM(Object-Relational Mapping) 기술의 표준 사양(또는 명세, Specification)이다.  
Hibernate ORM은 JPA에서 정의해둔 인터페이스를 구현한 구현체로써 JPA에서 지원하는 기능 이외에 Hibernate 자체적으로 사용할 수 있는 API 역시 지원하고있다.  
JPA에서는 테이블과 매핑되는 엔티티 객체 정보를 영속성 컨텍스트(Persistence Context)에 보관해서 애플리케이션 내에서 오래 지속 되도록 한다.  
영속성 컨텍스트 관련 JPA API  
em.persist()를 사용해서 엔티티 객체를 영속성 컨텍스트에 저장할 수 있다.  
엔티티 객체의 setter 메서드를 사용해서 영속성 컨텍스트에 저장된 엔티티 객체의 정보를 업데이트 할 수 있다.  
em.remove()를 사용해서 엔티티 객체를 영속성 컨텍스트에서 제거할 수 있다.  
em.flush()를 사용해서 영속성 컨텍스트의 변경 사항을 테이블에 반영할 수 있다.  
tx.commit()을 호출하면 내부적으로 em.flush()가 호출된다. 


핵심 포인트  
@Entity 애너테이션을 클래스 레벨에 추가하면 JPA의 관리대상 엔티티가 된다.  
@Table 애너테이션은 엔티티와 매핑할 테이블을 지정한다.  
@Entity 애너테이션과 @Id 애너테이션은 필수로 추가해야 한다.  
JPA는 IDENTITY, SEQUENCE, TABLE, AUTO 전략 같은 다양한 기본키 생성 전략을 지원한다.  
IDENTITY 전략  
기본키 생성을 데이터베이스에 위임하는 전략이다.  
SEQUENCE 전략  
데이터베이스에서 제공하는 시퀀스를 사용해서 기본키를 생성하는 전략이다.  
TABLE 전략  
별도의 키 생성 테이블을 사용하는 전략이다.  
AUTO 전략  
JPA가 데이터베이스의 Dialect에 따라서 적절한 전략을 자동으로 선택한다.  
Java의 원시 타입 필드에서 @Column 애너테이션이 없거나 @Column 애너테이션이 있지만 애트리뷰트를 생략한 경우, 최소한 nullable=false는 설정하는 것이 에러를 미연에 방지하는 길이다.  
java.util.Date, java.util.Calendar 타입으로 매핑하기 위해서는 @Temporal 애너테이션을 추가해야하지만 LocalDate, LocalDateTime 타입일 경우, @Temporal 애너테이션은 생략 가능하다.  
@Transient 애너테이션을 필드에 추가하면 JPA가 테이블 컬럼과 매핑하지 않겠다는 의미로 인식한다.  
테이블에 이미 저장되어 있는 enum 순서 번호와 enum에 정의되어 있는 순서가 일치하지 않게 되는 문제가 발생하지 않도록 EnumType.STRING 을 사용하는 것이 좋다.  
  
