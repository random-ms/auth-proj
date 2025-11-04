import {createContext, useEffect, useState} from "react";
import {AppConstants} from "../util/constants.js";
import axios from "axios";
import {toast} from "react-toastify";

export const AppContext = createContext();

export const AppContextProvider = (props) => {

    axios.defaults.withCredentials = true;

    const backendUrl = AppConstants.BACKEND_URL
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userData, setUserData] = useState(false);

    const getUserData = async () => {
        try {
            const res = await axios.get(backendUrl+"/profile")
            if(res.status === 200){
                setUserData(res.data);
            } else{
                toast.error(res.statusText);
            }
        } catch (e) {
            toast.error(e.message);
        }
    }

    const getAuthState = async () => {
        try{
            const res = await axios.get(backendUrl+"/is-authenticated")
            if(res.status === 200 && res.data === true) {
                setIsLoggedIn(true);
                await getUserData()
            } else {
                setIsLoggedIn(false);
            }
        } catch (e) {
              console.error(e.message);
        }
    }

    useEffect(() => {
        getAuthState();
    }, []);

    const contextValue = {
    backendUrl, isLoggedIn, setIsLoggedIn, userData, setUserData, getUserData
    }

    return(
            <AppContext.Provider value={contextValue}>
                {props.children}
            </AppContext.Provider>
        )
}