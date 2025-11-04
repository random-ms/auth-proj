import {Link, useNavigate} from "react-router-dom";
import {assets} from "../assets/assets.js";
import {useContext, useState} from "react";
import axios from "axios";
import {AppContext} from "../context/AppContext.jsx";
import {toast} from "react-toastify";

const Login = () => {
    const [isAccountCreated, setIsAccountCreated] = useState(false);
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const {backendUrl, setIsLoggedIn, getUserData} = useContext(AppContext);
    const navigate = useNavigate();

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        axios.defaults.withCredentials = true;
        setIsLoading(true);
        try{
            if(isAccountCreated){
                // Register API
               const res = await axios.post(`${backendUrl}/register`, {name, email, password})
                if(res.status === 201){
                    navigate("/");
                    toast.success("Account created successfully.");
                } else {
                    toast.error(res.statusText);
                }
            } else {
                const res = await axios.post(`${backendUrl}/login`, {email, password})
                if(res.status === 200){
                    setIsLoggedIn(true);
                    getUserData();
                    navigate("/");
                } else {
                    toast.error(res.statusText);
                }
            }
        }catch (e) {
            toast.error(e.response.data.message);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <div className="position-relative min-vh-100 d-flex justify-content-center align-items-center"
        style={{background: "linear-gradient(90deg, #6a5af9, #8268f9)", border: "none"}}>
            <div style={{position: "absolute", top: "20px", left: "30px", display: "flex", alignItems: "center",
                    justifyContent: "center"}}>
                <Link to="/" style={{
                    display: "flex", gap: 5, alignItems: "center",
                    fontWeight: "bold", fontSize: "24px",
                    textDecoration: "none",
                }}>
                    <img src={assets.logo} alt="logo" style={{width: 32, height: 32}} />
                    <span className="fw-bold fs-24 text-light">Authify</span>
                </Link>
            </div>
            <div className="card p-4" style={{maxWidth: "400px", width: "100%"}}>
                <h2 className="text-center mb-4">
                    {isAccountCreated ? "Create Account" : "Login"}
                </h2>
                <form onSubmit={onSubmitHandler}>
                    {isAccountCreated && (
                        <div className="mb-3">
                            <label htmlFor="fullName" className="form-label">Full Name</label>
                            <input type="text" id="fullName" className="form-control"
                                   placeholder="Enter your full name" required
                                   value={name}
                                   onChange={(e) => setName(e.target.value)}
                            />
                        </div>
                    )}
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email</label>
                        <input type="text" id="email" className="form-control"
                               placeholder="Enter your email" required
                               value={email}
                               onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" id="password" className="form-control"
                               placeholder="************" required
                               value={password}
                               onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="d-flex justify-content-between mb-3">
                        <Link to="/reset-password" className="text-decoration-none">
                            Forgot Password?
                        </Link>
                    </div>
                        <button type="submit" className="btn btn-primary w-100" disabled={isLoading}>
                            {isLoading ? "Loading..." : isAccountCreated ? "Sign up" : "Login"}
                        </button>
                </form>
                <div className="text-center mt-3">
                    <p className="mb-0">
                        {
                            isAccountCreated ?
                                (<>
                                    Already have an account?
                                    <span
                                        onClick={() => setIsAccountCreated(!isAccountCreated)}
                                        className="text-decoration-underline"
                                        style={{cursor: "pointer", marginLeft: "5px"}}>
                                        Login
                                    </span>
                                </>) :
                                (
                                    <>
                                        Don't have an account?
                                        <span
                                            onClick={() => setIsAccountCreated(!isAccountCreated)}
                                            className="text-decoration-underline"
                                            style={{cursor: "pointer", marginLeft: "5px"}}>
                                            Sign up
                                        </span>
                                    </>
                                )
                        }
                    </p>
                </div>
            </div>
        </div>
    )
}

export default Login;