const opentracing = require('opentracing')
const tracer = opentracing.globalTracer()

function formatGreeting (req, res) {
  // You can re-use the parent span or create a child span
  const span = tracer.startSpan('format-greeting', { childOf: req.span })
  // Parse the handler input
  const name = req.query.name
  // check the baggage
  const baggage = span.getBaggageItem('myBaggage')
  span.log({ event: 'baggage', message: `this is baggage ${baggage}` })
  const response = `Hello from service-b ${name}!`
  span.finish()
  res.send(response)
}
module.exports = formatGreeting
