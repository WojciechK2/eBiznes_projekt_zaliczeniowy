import {postDataWithCredentials} from "../../Utils/PostDataWithCredentials";

const RemoveFromCartButton = (props) => {

    const addToBasket = () => {
        let data = {
            "productId": props.productId
        }
        postDataWithCredentials(process.env.REACT_APP_SERVER_URL + "cart/remove", data).then((response) => {
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

export default RemoveFromCartButton