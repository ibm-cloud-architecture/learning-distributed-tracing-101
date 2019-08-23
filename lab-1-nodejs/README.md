# Lab 1 - Node.js for Distributing Tracing


## Add tracing library for application

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