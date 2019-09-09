
let counter = 1
const sayHello = async (req, res) => {
  // Parse the handler input
  const name = req.params.name

  // simulate a slow request every 3 requests
  setTimeout(async () => {
    const response = await formatGreeting(name);
    // const response = await formatGreetingRemote(name, span)
    res.send(response)
  }, counter++ % 3 === 0 ? 100 : 0)
}

function formatGreeting(name, parent) {
  const response = `Hello ${name}!`
  return response
}

const bent = require('bent')

const formatGreetingRemote = async (name, span) => {
  const service = process.env.SERVICE_FORMATTER || 'localhost'
  const servicePort = process.env.SERVICE_FORMATTER_PORT || '8081'
  const url = `http://${service}:${servicePort}/formatGreeting?name=${name}`
  const headers = {}

  const request = bent('string', headers)
  const response = await request(url)
  return response
}

module.exports = sayHello
