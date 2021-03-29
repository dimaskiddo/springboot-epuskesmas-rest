# Builder Image
# ---------------------------------------------------
FROM maven:3-adoptopenjdk-11 as java-builder

# Set Arguments On Build
ARG ARGS_MVN_PACKAGE

# Set Working Directory
WORKDIR /usr/src/app

# Copy Java Source Code File
COPY . ./

# Build Java Source Code File
RUN mvn dependency:resolve \
    && mvn package ${ARGS_MVN_PACKAGE}

# Final Image
# ---------------------------------------------------
FROM adoptopenjdk/openjdk11:alpine-jre

# Set Environment Variable
ENV DB_HOST=127.0.0.1 \
    DB_PORT=3306 \
    DB_USERNAME=root \
    DB_PASSWORD=password \
    DB_NAME=

# Set Working Directory
WORKDIR /opt/app

# Copy Anything The Application Needs
COPY --from=java-builder /usr/src/app/target/*.jar ./e-puskesmas.jar

EXPOSE 8080

# Running Java application
CMD java -server -jar e-puskesmas.jar
