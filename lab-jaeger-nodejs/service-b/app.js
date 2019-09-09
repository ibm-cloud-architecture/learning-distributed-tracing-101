const express = require('express')
const app = express()
const port = 8081
const serviceName = process.env.SERVICE_NAME || 'service-b'

// Using the span inside a route handler
const formatter = require('./formatter')
app.get('/formatGreeting', formatter)

app.disable('etag')
app.listen(port, () => console.log(`Service ${serviceName} listening on port ${port}!`))
