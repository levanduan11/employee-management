FROM gradle:8.4-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN mkdir -p src/main/resources/keys && \
    openssl genrsa -out src/main/resources/keys/keypair.pem 2048 && \
    openssl rsa -in src/main/resources/keys/keypair.pem -pubout -out src/main/resources/keys/public.pem && \
    openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in src/main/resources/keys/keypair.pem -out src/main/resources/keys/private.pem && \
    rm src/main/resources/keys/keypair.pem

RUN gradle build --no-daemon

FROM openjdk:21-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/api.jar
COPY --from=build /home/gradle/src/src/main/resources/keys /app/src/main/resources/keys
ENTRYPOINT ["java","-jar","/app/api.jar"]
