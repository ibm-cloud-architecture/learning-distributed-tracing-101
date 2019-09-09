# Distributing Tracing Lab

## Course Objectives

Learn how to:

- Instantiate a Tracer
- Create a simple trace
- Annotate the trace
- Trace individual functions
- Combine multiple spans into a single trace
- Propagate the in-process context
- Trace a transaction across more than one microservice
- Pass the context between processes using Inject and Extract
- Apply OpenTracing-recommended tags
- Understand distributed context propagation
- Use baggage to pass data through the call graph

## Prerequisites
- [Docker for Desktop](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Code Editor - I recommend [VSCode](https://code.visualstudio.com/)

## Node.js Lab
Change directory to the lab:
```
cd lab-jaeger-nodejs
```
Follow the instructions for the [Lab - Distributing Tracing for Node.js](./modules/ROOT/pages/lab-jaeger-java.adoc)


## Java Lab
Change directory to the lab:
```
cd lab-jaeger-java
```
Follow the instructions for the [Lab - Distributing Tracing for Java](./modules/ROOT/pages/lab-jaeger-nodejs.adoc)
