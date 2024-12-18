# 1. Base image 설정 (Java 21 사용 예시)
FROM eclipse-temurin:21-jdk-alpine

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. Spring Boot JAR 파일 복사
COPY target/gateway.jar app.jar

# 4. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]