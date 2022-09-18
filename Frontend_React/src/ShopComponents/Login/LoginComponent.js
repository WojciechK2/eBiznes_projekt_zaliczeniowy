import LoginLandingPage from "./LoginLandingPage";
import {useContext} from "react";
import {loggedInContext} from "../Contexts/loggedInContext";

const baseURL = process.env.REACT_APP_SERVER_URL

const LoginComponent = () => {

    const {loggedIn} = useContext(loggedInContext)

    let element
    if(!loggedIn){
        element =
            <div className={"loginComponent"}>
                <a href={`${baseURL}login`}>Login with Google</a> <br/>
                <a href={`${baseURL}login_github`}>Login with Github</a>
            </div>

    } else {
        element = <LoginLandingPage />
    }

    return(
        <div className="LoginComponent">
            <h1>Login Component</h1>
            {element}
        </div>
    )
}

export default LoginComponent