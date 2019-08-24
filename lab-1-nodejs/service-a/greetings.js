const opentracing = require("opentracing");
const tracer = opentracing.globalTracer();


let counter = 1;
function sayHello(req, res) {
    // You can re-use the parent span or create a child span
    let span = ctx = tracer.startSpan("say-hello", { childOf: req.span });

    // Parse the handler input
    let name = req.params.name;

    // show how to do a log in the span
    span.log({ event: 'name', message: `this is a log message for name ${name}` });
    // show how to set a baggage item for context propagation (be careful is expensive)
    span.setBaggageItem("myBaggage", name);

    // simulate a slow request every 3 requests
    setTimeout(() => {
        let response = formatGreeting(name, span);
        span.setTag("response", response);
        span.finish();
        res.send(response);
    }, counter++ % 3 == 0 ? 100 : 0);
}

function formatGreeting(name, parent) {
    let span = tracer.startSpan("format-greeting", { childOf: parent })
    let response = `Hello ${name}!`;
    span.finish();
    return response;
}

function formatGreetingRemote() {

}


module.exports = sayHello;