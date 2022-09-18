import {Link} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";
import React, {useContext} from "react";
import {loggedInContext} from "../Contexts/loggedInContext";

const ToOrderButton = () => {

    const {loggedIn} = useContext(loggedInContext)

    if(loggedIn) {
        return (
            <div className={"CheckoutButtonHandler"}>
                <button className={"checkoutButton"}>
                    <Link to={pathsStruct.CheckoutPage}>
                    Checkout
                    </Link>
                </button>
            </div>
        )
    } else {
        return (
            <div className={"CheckoutButtonHandler"}>
                <p>Not so fast!</p>
                <p>Please login first</p>
                <button className={"LoginButton"}>
                    <Link to={pathsStruct.LoginPage}>
                        Login
                    </Link>
                </button>
            </div>
        )
    }
}

export default ToOrderButton