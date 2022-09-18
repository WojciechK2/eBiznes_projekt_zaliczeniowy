import {postDataWithCredentials} from "../../Utils/PostDataWithCredentials";
import {useNavigate} from "react-router-dom";
import pathsStruct from "../../Utils/PathsStruct";

const baseURL = process.env.REACT_APP_SERVER_URL

const Checkout = () => {

    const navigate = useNavigate()

    const handleSubmit = event => {
        event.preventDefault()
        let data = {
            "address": event.target.address.value,
            "phoneNumber":event.target.phoneNumber.value
        }
        postDataWithCredentials(baseURL + "orders/add", data).then((response) => {
            if(response.ok){
                return response.json()
            } else {
                throw new Error()
            }
        }).then(data=>
            navigate(pathsStruct.Payments, {state: data}))
            .catch(err=>console.log(err))
    }

    return(
        <div className={"ordersComponent"}>
            <h1>Checkout Component</h1>
            <p style={{color:"red"}}>There is no validation for those values!</p>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <label>
                        <p>Address</p>
                        <input name={"address"}/>
                    </label>
                    <label>
                        <p>Phone number</p>
                        <input name={"phoneNumber"}/>
                    </label>
                </fieldset>
                <button type={"submit"}>Submit</button>
            </form>
        </div>
    )
}

export default Checkout