package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Section3Week2TemplateJpaApplication {


	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "basic");  // Chapter - JPA(Java Persistence API)란?
//		System.setProperty("spring.profiles.active", "table");  // Chapter - 엔티티 매핑 > 엔티티와 테이블 간의 매핑
//		System.setProperty("spring.profiles.active", "id-direct");  // Chapter - 기본키 매핑 > 기본키 직접 할당 전략
//		System.setProperty("spring.profiles.active", "id-identity");  // Chapter - 기본키 매핑 > IDENTITY 전략
//		System.setProperty("spring.profiles.active", "id-sequence");  // Chapter - 기본키 매핑 > SEQUENCE 전략
//		System.setProperty("spring.profiles.active", "column"); // Chapter - 필드와 컬럼간의 매핑 > 엔티티와 테이블 간의 매핑
//		System.setProperty("spring.profiles.active", "many-to-one-uni");  // 다대일 단방향 연관 관계

		SpringApplication.run(Section3Week2TemplateJpaApplication.class, args);

	}
}
