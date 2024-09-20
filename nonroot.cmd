
Breadcrumbsdevops-backend
/nonroot.Dockerfile
Latest commit
andreasrous
andreasrous
first commit
0edfe0d
 · 
3 months ago
History
File metadata and controls

Code

Blame
18 lines (16 loc) · 447 Bytes
FROM openjdk:21-rc-oracle as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw  package -Dmaven.test.skip

FROM openjdk:19-jdk-alpine3.16
RUN apk update && apk add curl
WORKDIR /app
# Set non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 9090
COPY --from=builder /app/target/*.jar /app/*.jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
