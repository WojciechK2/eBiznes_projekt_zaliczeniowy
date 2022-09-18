import {Outlet} from "react-router-dom";
import LayoutHeader from "./LayoutHeader";
import LayoutNavigation from "./LayoutNavigation";
import LayoutFooter from "./LayoutFooter";
import React from "react";
import {fetchData} from "../../Utils/fetchData";
import NoPage from "../NoPage";
import {categoryContext} from "../Contexts/categoryContext";
import {productsContext} from "../Contexts/productsContext";

const baseURL = process.env.REACT_APP_SERVER_URL

const Layout = () => {

    const [products, setProducts] = React.useState(null);
    const [categories, setCategories] = React.useState(null);

    React.useEffect(() => {
            fetchData(baseURL + "products").then(response => setProducts(response))
            fetchData(baseURL + "categories").then(response => setCategories(response))
    }, []);

    if (!categories) return (<NoPage errorMessage={"Networking issue, categories"}/>)
    if (!products) return (<NoPage errorMessage={"Networking issue, products"}/>)

    return (
        <div className={"mainContainer"}>
            <LayoutHeader/>
                <categoryContext.Provider value={categories}>
                    <productsContext.Provider value={products}>
                        <div className={"mainContentContainer"}>
                            <LayoutNavigation/>
                            <Outlet/>
                        </div>
                    </productsContext.Provider>
                </categoryContext.Provider>
            <LayoutFooter/>
        </div>
)
}

export default Layout