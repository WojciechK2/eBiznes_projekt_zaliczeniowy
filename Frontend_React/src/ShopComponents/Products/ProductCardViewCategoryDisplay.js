import {Link, useLocation} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";
import React from "react";

const ProductCardViewCategoryDisplay = ({category}) => {

    const location = useLocation()

    if (location.pathname.includes(pathsStruct.categoryPageBase)) return null

    return (
        <div className={"categoryProductCardView"}>
            <Link to={`${pathsStruct.categoryPageBase}/${category[0].id}`}
                  className={"categoryProductCardViewLink"}>
                {category[0].name}
            </Link>
        </div>
    )
}

export default ProductCardViewCategoryDisplay