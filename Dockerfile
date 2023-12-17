# Builder Image
# ---------------------------------------------------
FROM maven:3-adoptopenjdk-17 as java-builder

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
FROM adoptopenjdk/openjdk17:alpine-jre

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

# Prepare Any Requirements
RUN mkdir -p /opt/app/static

# Expose Application Port
EXPOSE 8080

# Running Java Application
CMD java -server -jar e-puskesmas.jar
