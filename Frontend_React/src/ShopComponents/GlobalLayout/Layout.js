import {Outlet} from "react-router-dom";
import LayoutHeader from "./LayoutHeader";
import LayoutNavigation from "./LayoutNavigation";
import LayoutFooter from "./LayoutFooter";
import React, {useState} from "react";
import {fetchData} from "../../Utils/fetchData";
import NoPage from "../NoPage";
import {categoryContext} from "../Contexts/categoryContext";
import {productsContext} from "../Contexts/productsContext";
import {loggedInContext} from "../Contexts/loggedInContext";
import {fetchDataWithCredentials} from "../../Utils/fetchDataWithCredentials";

const baseURL = process.env.REACT_APP_SERVER_URL

const Layout = () => {

    const [loggedIn, setLoggedIn] = useState(false)

    const setLoggedInValue = (value) =>{
        setLoggedIn(value)
    }

    const [products, setProducts] = React.useState(null);
    const [categories, setCategories] = React.useState(null);

    React.useEffect(() => {
            fetchData(baseURL + "products").then(response => setProducts(response))
            fetchData(baseURL + "categories").then(response => setCategories(response))
            fetchDataWithCredentials(baseURL+"checklogin").then((response) => response.json()).then(response => {
                console.log(response)
                setLoggedInValue(response)})
    }, []);

    if (!categories) return (<NoPage errorMessage={"Networking issue, categories"}/>)
    if (!products) return (<NoPage errorMessage={"Networking issue, products"}/>)

    return (
        <div className={"mainContainer"}>
            <LayoutHeader/>
            <loggedInContext.Provider value={ {loggedIn , setLoggedInValue }}>
                <categoryContext.Provider value={categories}>
                    <productsContext.Provider value={products}>
                        <div className={"mainContentContainer"}>
                            <LayoutNavigation/>
                            <Outlet/>
                        </div>
                    </productsContext.Provider>
                </categoryContext.Provider>
            </loggedInContext.Provider>
            <LayoutFooter/>
        </div>
)
}

export default Layout