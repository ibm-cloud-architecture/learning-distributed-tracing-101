# Lab - Distributing Tracing for Java


## Test the service without tracing enable

```
docker-compose build
docker-compose up
```

Try the service
```
curl http://localhost:8080/sayHello/Carlos
Hello Carlos
```

## Add the tracing client library

For the java service `service-a` edit the file `pom.xml`and add the dependecies for `jaeger` and `opentracing`.

The file should look like the following:
```xml
<dependency>
    <groupId>io.opentracing</groupId>
    <artifactId>opentracing-api</artifactId>
    <version>0.31.0</version>
</dependency>

<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-cloud-starter</artifactId>
    <version>0.1.13</version>
</dependency>

<dependency>
    <groupId>io.jaegertracing</groupId>
    <artifactId>jaeger-client</artifactId>
    <version>0.31.0</version>
</dependency>
```