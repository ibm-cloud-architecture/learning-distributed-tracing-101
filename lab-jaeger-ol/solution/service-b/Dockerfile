# our base build image
FROM maven:3.6-jdk-8 as builder

# copy the project files
COPY pom.xml .

# build all dependencies
RUN mvn -ntp dependency:go-offline

# copy your other files
COPY ./src ./src

# build for release
RUN mvn -ntp liberty:install-server liberty:create package

FROM openliberty/open-liberty:20.0.0.3-kernel-java8-openj9-ubi as runner

# Copy jar file over from builder stage
ENV WAR_NAME=service-b.war
COPY --from=builder target/solution-$WAR_NAME /opt/ol/wlp/usr/servers/defaultServer/apps/$WAR_NAME
COPY --from=builder target/liberty/wlp/usr/servers/defaultServer/server.xml /opt/ol/wlp/usr/servers/defaultServer
COPY --from=builder target/liberty/wlp/usr/servers/defaultServer/bootstrap.properties /opt/ol/wlp/usr/servers/defaultServer

RUN find /opt/ol/wlp/bin/server -type f

# Run the server script and start the defaultServer by default.
ENTRYPOINT ["/opt/ol/wlp/bin/server", "run"]
CMD ["defaultServer"]