import React, {useState} from "react";
import BasketItem from "./BasketItem";
import {fetchDataWithCredentials} from "../../Utils/fetchDataWithCredentials";
import ToOrderButton from "../Buttons/toOrderButton";

const Basket = () => {

    const [totalPrice, setTotalPrice] = useState(null)
    const [productsInCart, setProductsInCart] = useState([])

    const updateValues = () => {
        fetchDataWithCredentials(`${process.env.REACT_APP_SERVER_URL}cart`).then((response) => {
            if (response.ok) {
                response.json().then((response) => {
                    setProductsInCart(response)
                    calculateTotalPrice(response)
                })
            } else {
                console.log(response.status)
                if (response.status === 403) {
                    response.text().then((response) => {
                        alert(response)
                    })
                } else {
                    alert("Error occured")
                }
            }
        })
    }

    const calculateTotalPrice = (response) => {
        let tot = 0
        response.forEach(function (item) {
            tot += (item.quantity * item.price)
        })
        setTotalPrice(tot)
    }

    React.useEffect(() => {
        updateValues()
    }, [])

    if (productsInCart.length === 0) {
        return (
            <div className={"basketComponent"}>
                <h1>Basket is empty</h1>
            </div>
        )
    }

    return (
        <div className="basketComponent">
            <div className={"basketItemsList"}>
                <ul>
                    {productsInCart.map((d, index) => (
                        <li key={index}>
                            <BasketItem item={d} updateParentFunction={updateValues}/>
                        </li>
                    ))
                    }
                </ul>
            </div>
            <div className={"totalPrice"}>
                <p>Total price: {totalPrice}</p>
            </div>
            <ToOrderButton />
        </div>
    )
}

export default Basket;
