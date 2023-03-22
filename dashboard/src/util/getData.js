import config from "../../config.json"
const server = config.SERVER_URL

/*
Get data from the API endpoint
 */
async function getData(endpoint) {
    return await fetch(`${server}${endpoint}`)
        .then((res) => res.json())
}

export default getData
