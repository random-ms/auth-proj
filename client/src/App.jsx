import './App.css'
import {ToastContainer} from "react-toastify";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/home.jsx";
import Login from "./pages/login.jsx";
import EmailVerify from "./pages/email-verify.jsx";
import ResetPassword from "./pages/reset-password.jsx";

function App() {

  return (
    <div>
        <ToastContainer />
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/email-verify" element={<EmailVerify />} />
            <Route path="/reset-password" element={<ResetPassword />} />
        </Routes>
    </div>
  )
}

export default App