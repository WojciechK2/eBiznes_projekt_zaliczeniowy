import React, {useContext} from "react";
import {Link} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";
import ProductCardViewCategoryDisplay from "./ProductCardViewCategoryDisplay";
import NoPage from "../NoPage";
import {categoryContext} from "../Contexts/categoryContext";
import {productsContext} from "../Contexts/productsContext";
import {loggedInContext} from "../Contexts/loggedInContext";
import AddToCartButton from "../Buttons/AddToCartButton";

const ProductCardView = ({productID}) => {

    const products = useContext(productsContext)
    const categories = useContext(categoryContext)
    const {loggedIn} = useContext(loggedInContext)

    const updateValues = () => {
        //console.log("Updated")
    }

    const product = products.filter(
        d => {
            return d.id === productID
        }
    )

    const category = categories.filter(
        d => {
            return d.id === product[0].categoryReference
        }
    )

    if (!categories) return (<NoPage errorMessage={"Networking issue, categories"}/>)
    if (!products) return (<NoPage errorMessage={"Networking issue, products"}/>)

    //add links inside
    return (
        <div className={"productCardView"}>
            <div className={"productCardView_middle"}>
                <Link to={`${pathsStruct.ProductsPage}/${productID}`} className={"productCardViewProductLink"}>
                    <p className={"nameProductCardView"}>{product[0].name}</p>
                </Link>
                <ProductCardViewCategoryDisplay category={category}/>
                <p className={"priceProductCardView"}>Price: {product[0].price}</p>
            </div>
            <div className={"productCardView_bottom"}>
                <p className={"descriptionProductCardView"}>{product[0].description}</p>
            </div>
            {(loggedIn) && <AddToCartButton productId={product[0].id} message={"Add To Cart"} parentAction={updateValues}/>}
            {(!loggedIn) && <p>To add to cart, Please log in to the service</p>}
        </div>
    )
}

export default ProductCardView