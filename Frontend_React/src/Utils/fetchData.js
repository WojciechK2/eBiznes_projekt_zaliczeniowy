export async function fetchData(url) {

    const init = {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }

    try {
        return await fetch(url,init)
            .then((response) => response.json())
    } catch (error) {
        return error
    }
}
