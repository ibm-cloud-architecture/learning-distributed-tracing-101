const opentracing = require('opentracing')
const tracer = opentracing.globalTracer()

function formatGreeting(req, res) {
  // You can re-use the parent span or create a child span
  const span = tracer.startSpan('format-greeting', { childOf: req.span })
  // Parse the handler input
  const name = req.query.name
  span.log({ event: 'format', message: `formatting message remotely for name ${name}` })
  // check the baggage
  const baggage = span.getBaggageItem('my-baggage')
  span.log({ event: 'propagation', message: `this is baggage ${baggage}` })
  const response = `Hello from service-b ${name}!`
  span.finish()
  res.send(response)
}
module.exports = formatGreeting
