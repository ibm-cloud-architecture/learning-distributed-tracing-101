

function formatGreeting(req, res) {
  // Parse the handler input
  const name = req.query.name

  const response = `Hello from service-b ${name}!`

  res.send(response)
}
module.exports = formatGreeting
