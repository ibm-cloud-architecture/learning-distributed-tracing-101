const opentracing = require("opentracing");
const tracer = opentracing.globalTracer();



function greetings(req, res) {
    // You can re-use the parent span or create a child span
    let span = ctx = tracer.startSpan("greetings_handler", { childOf: req.span })

    // Parse the handler input
    let name = req.query.name || "Carlos";

    // show how to do a tag in the span
    span.setTag("nameTag", name);
    // show how to do a log in the span
    span.log({ event: 'greetings_handler', message: `this is a log message for name ${name}` });
    // show how to set a baggage item for context propagation (be careful is expensive)
    span.setBaggageItem("nameBaggage", name);

    // simulate some processing time for 100ms to measure how long this span stook
    setTimeout(() => {
        span.finish()
        res.send(`Greetings ${name}`)
    }, 100);
}

module.exports = greetings;