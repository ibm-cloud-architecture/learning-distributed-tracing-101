# Lab 1 - Distributing Tracing for Node.js


## Test the service without tracing enable

```
docker-compose build
docker-compose up
```

Try the service
```
curl http://localhost:8080/sayHello/Carlos
Hello Carlo
```

## Add the tracing client library

For the node.js service edit the file `package.json`and add the dependecy for `jaeger-client`

The json section should look like the following:
```json
{
"dependencies": {
    "express": "^4.17.1",
    "jaeger-client": "^3.15.0"
  }
}
```
