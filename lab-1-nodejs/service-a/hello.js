const opentracing = require("opentracing");
const tracer = opentracing.globalTracer();

let counter = 1;
const sayHello = async (req, res) => {
    // You can re-use the parent span or create a child span
    let span = ctx = tracer.startSpan("say-hello", { childOf: req.span });

    // Parse the handler input
    let name = req.params.name;

    // show how to do a log in the span
    span.log({ event: 'name', message: `this is a log message for name ${name}` });
    // show how to set a baggage item for context propagation (be careful is expensive)
    span.setBaggageItem("myBaggage", name);

    // simulate a slow request every 3 requests
    setTimeout(async () => {
        //let response = await formatGreeting(name, span);
        let response = await formatGreetingRemote(name, span);
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

const bent = require('bent');
const formatGreetingRemote = async (name, span) => {
    let service = process.env.SERVICE_FORMATTER || 'localhost';
    let servicePort = process.env.SERVICE_FORMATTER_PORT || '8081';
    let url = `http://${service}:${servicePort}/formatGreeting?name=${name}`
    let headers = {};
    tracer.inject(span, opentracing.FORMAT_HTTP_HEADERS, headers);
    let request = bent('string', headers);
    let response = await request(url);
    return response;
}

module.exports = sayHello;