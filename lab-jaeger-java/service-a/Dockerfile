# Build stage - could use maven or our image
FROM maven:3.3-jdk-8 as builder

# Creating Work directory
ENV BUILD_DIR=/usr/src/app/
RUN mkdir $BUILD_DIR
WORKDIR $BUILD_DIR

# Reuse local .m2. if not all the dependencies will be always downloaded
# This can be removed if you want to
VOLUME ${HOME}/.m2:/root/.m2
ADD . /usr/src/app

RUN bash -c " mvn clean install"

FROM ibmjava:8-sfj as runner

# Install Extra Packages
RUN apt-get update \
    && apt-get install -y jq bash bc ca-certificates curl \
    && update-ca-certificates

# Create app directory
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy jar file over from builder stage
ENV JAR_NAME=demo-0.0.1-SNAPSHOT.jar
COPY --from=builder /usr/src/app/target/$JAR_NAME $APP_HOME/app.jar

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ./app.jar" ]