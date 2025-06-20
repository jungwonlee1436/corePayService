송금 서비스

1. 기술 스택
- Java 17
- Spring Boot 3
- JPA (Hibernate)
- H2 / MySQL
- Docker, Docker Compose
- Lombok, JUnit, Mockito

2. 실행 방법 (Docker)
-Docker만 사용하는 경우 (H2 내장 DB사용)
```bash
docker build -t remittance-app .
docker run -p 8080:8080 remittance-app

-Docker Compose 사용 (MySQL 포함)
docker-compose up --build

-만약 docker-compose로 DB 띄워서 통합 테스트하고 싶으면 application.yml의 DB 설정만 MySQL로 변경
./gradlew test --tests *RemittanceServiceIntegrationTest

3. 테스트 방법
./gradlew test
- 단위 테스트는 Mockito 기반이며, DB 없이도 테스트됩니다.
