const server = 'http://localhost:8080'

/*
Get data from the API endpoint
 */
async function getData(endpoint) {
    return await fetch(`${server}${endpoint}`)
        .then((res) => res.json())
}

export default getData
