const opentracing = require("opentracing");
const tracer = opentracing.globalTracer();

function formatGreeting(req, res) {
    // You can re-use the parent span or create a child span
    let span = ctx = tracer.startSpan("format-greeting", { childOf: req.span });
    // Parse the handler input
    let name = req.query.name;
    // check the baggage
    let baggage = span.getBaggageItem("myBaggage");
    span.log({ event: 'baggage', message: `this is baggage ${baggage}` });
    let response = `Hello from service-b ${name}!`;
    span.finish();
    res.send(response);
}
module.exports = formatGreeting;