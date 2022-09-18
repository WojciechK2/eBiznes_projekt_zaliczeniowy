import {postDataWithCredentials} from "../../Utils/PostDataWithCredentials";

const AddToCartButton = (props) => {

    const addToBasket = () => {
        let data = {
            "productId": props.productId
        }
        postDataWithCredentials(process.env.REACT_APP_SERVER_URL + "cart/add", data).then((response) => {
            console.log(response)
            props.parentAction()
        })

    }

    return(
    <div className={"addToBasketButton"}>
        <button onClick={addToBasket}>{props.message}</button>
    </div>
    )
}

export default AddToCartButton