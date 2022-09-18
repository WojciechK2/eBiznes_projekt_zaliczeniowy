import pathsStruct from "../../Utils/PathsStruct";
import {Link} from "react-router-dom";

const LoginLandingPage = () => {
    return (
        <div>
            <h1>You are already logged in</h1>
            <Link to={pathsStruct.MainPage}>Go back to main: </Link>
        </div>
    )
}

export default LoginLandingPage