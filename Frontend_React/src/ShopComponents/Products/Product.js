import {useParams} from "react-router-dom";
import React from "react";
import {fetchData} from "../../Utils/fetchData";

const baseURL = process.env.REACT_APP_SERVER_URL

const Product = () => {
    const { id } = useParams()

    const [product, setProduct] = React.useState(null);

    React.useEffect(() => {
        fetchData(baseURL + "products/" + id).then((response) => {
            setProduct(response)
        })
    }, [id]);

    if (!product) return null

    return(
        <div className={"specificProduct"}>
            <h1>Specific Product Page</h1>
            <p>Name: {product.name}</p>
            <p>Description: {product.description}</p>
            <p>Price: {product.price}</p>
        </div>
    )
}

export default Product