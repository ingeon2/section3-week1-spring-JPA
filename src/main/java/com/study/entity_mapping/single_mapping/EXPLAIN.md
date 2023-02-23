여기는
기본키 직접 할당  
애플리케이션 코드 상에서 기본키를 직접 할당 해주는 방식입니다.  
  
기본키 자동 생성  
IDENTITY  
기본키 생성을 데이터베이스에 위임하는 전략입니다.  
데이터베이스에서 기본키를 생성해주는 대표적인 방식은 MySQL의 AUTO_INCREMENT 기능을 통해 자동 증가 숫자를 기본키로 사용하는 방식이 있습니다.  
  
SEQUENCE  
데이터베이스에서 제공하는 시퀀스를 사용해서 기본키를 생성하는 전략입니다.  
  
AUTO 전략은 구현 안함 여기서 설명.
마지막으로 @Id 필드에 @GeneratedValue(strategy = GenerationType.AUTO)를 지정하면 JPA가 데이터베이스의 Dialect에 따라서 적절한 전략을 자동으로 선택  
  
이러한 전략들을 사용하는것을 보여줌.
  
  
근데, 이미 entity 패키지에 있는 Member 변수 등등때문에 어플리케이션은 실행되지는 않음.  
  
column 테이블은  
필드(멤버 변수)와 컬럼 간의 매핑  
앞에서 엔티티와 테이블, 그리고 기본키에 대한 매핑을 살펴보았으니 마지막으로 엔티티 필드(멤버 변수)와 테이블 컬럼 간의 매핑을 통해 엔티티 매핑을 완성.  

@Column 애너테이션이 생략되었거나 애트리뷰트가 기본값을 사용할 경우 주의 사항  
int나 long 같은 원시 타입일 경우, @Column 애너테이션이 생략되면 기본적으로 nullable=false입니다.  
그런데 예를 들어서 개발자가 int price not null 이라는 조건으로 컬럼을 설정하길 원하는데 nullable에 대한 명시적인 설정없이  
단순히 @Column 애너테이션만 추가하면 nullable=true가 기본값이 되기 때문에 테이블에는 int price not null로 컬럼이 설정되는 것이 아니라 int price 와 같이 설정이 될 것입니다.  
따라서 개발자가 의도하는 바가 int price not null일 경우에는 @Column(nullable=false) 라고 명시적으로 지정하든가 아예 @Column 애너테이션 자체를 사용하지 않는 것이 권장됩니다.  
  
  
column패키지에서 예외실습한것과 마찬가지.  
엔티티 클래스에서 발생한 예외는 API 계층까지 전파되므로 API 계층의 GlobalExceptionAdvice 에서 캐치(catch)한 후, 처리할 수 있다는 사실을 기억.  

