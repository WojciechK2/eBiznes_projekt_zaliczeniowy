import {Link} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";

const LayoutHeader = () => {

    return (

        <header className={"layoutHeader container space-around"}>
            <div className={"shopTitle"}>
                <Link to={pathsStruct.MainPage} className={"shopLink"}>ShopTitle</Link>
            </div>
        </header>
    )
}

export default LayoutHeader