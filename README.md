# User CRUD Web Application

간단한 회원 관리 CRUD 웹 애플리케이션 프로젝트입니다.  
Spring Boot를 기반으로 하고, Docker와 AWS EC2를 활용해 배포까지 경험했습니다.

▶️ [프로젝트 시연 영상](https://youtu.be/fOPCDN_jA1M)

## 프로젝트 개요
- 목표: 단순 CRUD 기능 구현과 배포 경험 습득
- 학습 목표: Spring Boot, Spring Security, Docker, AWS EC2
- 주요 기능:
    - 회원가입(Sign Up)
    - 로그인(Login) / 로그아웃(Logout)
    - 마이페이지(Profile) 조회 및 정보 수정(Update)
    - 회원 탈퇴(Delete)

## 주요 구현 사항
- Spring Boot 기반 CRUD 기능 구현
- Spring Security를 통한 인증 처리
- Docker를 이용한 컨테이너화
- AWS EC2에 배포 및 외부 접속 확인
- URL 패턴은 전통적인 웹 MVC 스타일 사용  
  (예: `/mypage`, `/mypage/update`, `/mypage/delete`, `/signup`, `/signup/process`)

## 프로젝트 회고
- 맨땅에 헤딩식으로 구현하며 강의 내용을 몸으로 체득
- RESTful API 설계보다는 웹 MVC 구조 경험
- 클린 코드, 테스트 코드 작성 경험 부족
- Docker + AWS EC2 배포 경험을 통해 실전 환경 이해

> 자세한 일기와 회고는 [diary.txt](docs/diary.txt)에서 확인 가능합니다.

## 기술 스택
- Java 17
- Spring Boot 3.5.5
- Spring Security
- Docker
- AWS EC2
