import MenuBar from "../components/menu-bar.jsx";
import Header from "../components/header.jsx";

const Home = () => {
    return (
        <div className="flex flex-col items-center justify-content-center min-vh-100">
            <MenuBar />
            <Header />
        </div>
    )
}

export default Home;