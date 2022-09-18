export async function fetchData(url) {
    try {
        return await fetch(url)
            .then((response) => response.json())
    } catch (error) {
        return error
    }
}