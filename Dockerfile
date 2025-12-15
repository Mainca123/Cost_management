# =========================
# BUILD STAGE
# =========================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Maven Wrapper & pom trước để cache dependency
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build jar (skip test cho nhanh)
RUN ./mvnw clean package -DskipTests


# =========================
# RUN STAGE
# =========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy jar từ stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
