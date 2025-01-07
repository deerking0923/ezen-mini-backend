# 프로젝트 개요

프로젝트 이름: 나만의 문장을 만나다

설명: 좋아하는 문장들을 가득 담아 매일 랜덤으로 받아보는 인문학 아카이브

## 기술 스택

- Java 17
- Spring Boot 3.1.5
- MySQL
- Maven
- Lombok
- Swagger (SpringDoc OpenAPI)
- MapStruct
- VSCode
- CI/CD
- RESTful API

## 버전 정보

- 3.0.0 (2025-01-07) - 글과 답변 형식의 기본형. 시큐리티 제외. 랜덤 문장 받아오기 기능 구현. 글 수정 / 삭제 가능. 답변 갯수 표시. 글 필드에 비밀번호가 포함되어 작성자만 수정 삭제 가능.

## 설치 및 실행 방법

1. 필요한 소프트웨어 설치 (JDK 17이상, MySQL, Maven)
2. 프로젝트 클론

```bash
git clone https://github.com/deerking0923/ezen-mini-backend.git
```

1. .env 파일에 DB 설정

```bash
DB_URL=
DB_USERNAME=
DB_PASSWORD=

# Server Configuration
SERVER_PORT=8080

# JWT Configuration (추후 사용)
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION_MS=86400000

# Timezone
TZ=Asia/Seoul

# Jackson Configurationmvn spring
JACKSON_WRITE_DATES_AS_TIMESTAMPS=false
JACKSON_FAIL_ON_EMPTY_BEANS=false
JACKSON_FAIL_ON_UNKNOWN_PROPERTIES=false
JACKSON_DEFAULT_PROPERTY_INCLUSION=NON_NULL
```

1. 실행

```bash
# 기존 빌드 결과물을 삭제(clean)하고 프로젝트를 컴파일, 테스트, 패키징하여 최종 빌드(install) 생성
mvn clean install

# Spring Boot 애플리케이션을 실행(run)
mvn spring-boot:run
```

## API 문서 확인

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API 문서: http://localhost:8080/v3/api-docs

## API 설명

1. Answer
- (POST) /api/v1/answers - 답변 생성
- (PUT) /api/v1/answers/{id} 답변 수정
1. Health
- (GET) /api/v1/health - 헬스 체크
1. Question
- (GET) /api/v1/questions - 질문 목록 조회
- (POST) /api/v1/questions - 질문 생성
- (GET) /api/v1/questions/{id} - 질문 상세 조회
- (PUT) /api/v1/questions/{id} - 질문 수정
- (DELETE) /api/v1/questions/{id} - 질문 삭제
- (POST) /api/v1/questions/{id}/check-password - 비밀번호 확인
- (GET) /api/v1/questions/random - 오늘의 질문

## 프로젝트 구조

```bash
src/
├── main/
│   ├── java/com/springboot/board/
│   │   ├── api/v1/                  # API 관련 클래스 (컨트롤러, DTO 등)
│   │   │   ├── controller/          # REST API 컨트롤러
│   │   │   ├── dto/                 # 요청(Request) 및 응답(Response) DTO
│   │   │   └── service/             # 비즈니스 로직 클래스
│   │   ├── application/mapper/      # Entity와 DTO 간 매핑 클래스
│   │   ├── common/                  # 공통 기능 (예외 처리, 유틸리티, 설정 등)
│   │   │   ├── config/              # 설정 클래스 (Swagger, Jackson 등)
│   │   │   ├── exception/           # 예외 처리 클래스
│   │   │   └── util/                # 공통 유틸리티
│   │   ├── domain/                  # 도메인 관련 클래스
│   │   │   ├── entity/              # JPA Entity 클래스
│   │   │   └── repository/          # 데이터베이스 액세스 (JPA Repository)
│   │   ├── web/                     # Spring Boot 메인 클래스
│   ├── resources/                   # 리소스 파일 (설정, 정적 리소스 등)
│       └── application.yml          # Spring Boot 애플리케이션 설정 파일
├── test/                            # 테스트 코드
├── .github/workflows/               # GitHub Actions 워크플로우 파일
├── pom.xml                          # Maven 의존성 및 빌드 설정 파일
└── README.md                        # 프로젝트 설명 파일

```

## 주요 폴더 설명

### **api/v1**

- **controller**: REST API 엔드포인트를 정의한 클래스.
    - `QuestionController`: 질문 관련 API 처리.
    - `AnswerController`: 답변 관련 API 처리.
    - `HealthController`: 서비스 상태 확인 API.
- **dto**: 데이터 요청(Request) 및 응답(Response) 객체.
    - `request`: API 요청 데이터 클래스.
    - `response`: API 응답 데이터 클래스.

### **application**

- **mapper**: DTO와 Entity 간 데이터 변환을 위한 클래스.
    - `QuestionMapper`, `AnswerMapper`: 데이터 매핑.

### **common**

- **config**: 설정 파일 (Swagger, Jackson, JPA 등).
    - `SwaggerConfig`: API 문서를 자동 생성하는 Swagger 설정.
    - `JacksonConfig`: JSON 직렬화/역직렬화 설정.
    - `WebConfig`: 웹 관련 설정.
- **exception**: 예외 처리 클래스.
    - `GlobalExceptionHandler`: 전역 예외 처리.
    - `DataNotFoundException`: 데이터 조회 실패 시 예외.

### **domain**

- **entity**: JPA Entity 클래스 (데이터 모델).
    - `Question`, `Answer`, `BaseTimeEntity`.
- **repository**: 데이터베이스 접근을 위한 JPA Repository.
    - 관련 인터페이스 정의.

## 참고 OPEN API

https://www.weatherapi.com/