const express = require('express')
const app = express()
const port = 8080
const serviceName = process.env.SERVICE_NAME || 'service-a'

// Initialize the Tracer




// Instrument every incomming request


// Let's capture http error span
app.get('/error', (req, res) => {
  res.status(500).send('some error (ノ ゜Д゜)ノ ︵ ┻━┻')
})

// Using the span inside a route handler
const hello = require('./hello')
app.get('/sayHello/:name', hello)

app.disable('etag')
app.listen(port, () => console.log(`Service ${serviceName} listening on port ${port}!`))

