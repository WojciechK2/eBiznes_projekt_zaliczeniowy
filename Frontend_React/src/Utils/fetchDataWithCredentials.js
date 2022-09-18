export async function fetchDataWithCredentials(url) {

    const init = {
        credentials: "include",
        mode: "cors",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }

    try {
        return await fetch(url,init)
    } catch (error) {
        return error
    }
}