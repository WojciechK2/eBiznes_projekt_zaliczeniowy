export async function postDataWithCredentials(url, data) {
    return fetch(url, {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        credentials: "include",
        body: JSON.stringify(data)
    })
}

//create separate one with credentials included