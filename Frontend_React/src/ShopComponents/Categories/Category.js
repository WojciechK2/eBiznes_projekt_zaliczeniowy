import React, {useContext} from 'react'
import {useParams} from "react-router-dom";
import ProductCardView from "../Products/ProductCardView";
import {categoryContext} from "../Contexts/categoryContext";
import {productsContext} from "../Contexts/productsContext";

const Category = () => {

    const {id} = useParams()

    const categories = useContext(categoryContext);
    const products = useContext(productsContext);

    const categoryName = categories.filter(d => {
        return JSON.stringify(d.id) === id
    });

    const productsInCategory = products.filter(d => {
        return JSON.stringify(d.categoryReference) === id
    });

    return (
        <div className={"productsComponent categoryComponent"}>
            <h1>Category {categoryName[0].name}</h1>
            <ul>
                {productsInCategory.map(d => (
                    <li key={d.id}>
                        <ProductCardView key={d.id} productID={d.id}/>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default Category;