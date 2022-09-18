import AddToCartButton from "../Buttons/AddToCartButton";
import RemoveFromCartButton from "../Buttons/RemoveFromCartButton";

const BasketItem = (props) => {

    const updateValues = () => {
        props.updateParentFunction()
    }

    return (
        <div className={"BasketItemComponent"}>
            <div className={"Basketitems"}>
                <p>{props.item.productName}</p>
                <p>Quantity: {props.item.quantity}</p>
                <p>Price: {props.item.price * props.item.quantity}</p>
            </div>
            <AddToCartButton message={"Add One"} productId={props.item.productId} parentAction={updateValues}/>
            <RemoveFromCartButton message={"Remove one"} productId={props.item.productId} parentAction={updateValues}/>
        </div>
    )
}

export default BasketItem