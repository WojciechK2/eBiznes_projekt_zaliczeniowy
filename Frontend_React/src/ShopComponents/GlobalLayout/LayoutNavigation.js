import {Link} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";
import {useContext} from "react";
import {categoryContext} from "../Contexts/categoryContext";
import {loggedInContext} from "../Contexts/loggedInContext";

const baseURL = process.env.REACT_APP_SERVER_URL

const LayoutNavigation = () => {

    const categories = useContext(categoryContext)
    const { loggedIn } = useContext(loggedInContext)
    let LoginButton;

    if (!loggedIn) {
        LoginButton =
            <li>
                <Link to={pathsStruct.LoginPage}>Login</Link>
            </li>
    } else {
        LoginButton =
            <li>
                < a href={`${baseURL}logout`}>Logout</a>
            </li>
    }

    return (
        <div className={"sideNavigation"}>
            <nav>
                <ul>
                    <li>
                        <Link to={pathsStruct.MainPage}>Home</Link>
                    </li>
                    <li>
                        <Link to={pathsStruct.BasketPage}>Basket</Link>
                    </li>
                    {LoginButton}
                    <li>
                        <p>Categories</p>
                        <ul>
                            {categories.map(d => (
                                <li key={d.id}>
                                    <Link to={"/category/" + d.id}>{d.name}</Link>
                                </li>
                            ))}
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    )
};

export default LayoutNavigation;
