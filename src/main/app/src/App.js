import './App.css';
import KlassList from "./components/views/KlassList";
import NavigationBar from "./components/layout/NavigationBar";
import {BrowserRouter, Route} from "react-router-dom";
import StudentList from "./components/views/StudentList";
import SideBar from "./components/layout/SideBar";
import {Grid} from "@material-ui/core";
function App() {
  return (
    <BrowserRouter>
        <NavigationBar/>
        <Grid container>
            <Grid item xs={2}>
                <SideBar/>
            </Grid>
            <Grid item xs={10}>
                <Route
                    exact
                    path="/"
                    render={()=>(
                        <KlassList/>
                    )}
                />
                <Route
                    exact
                    path="/classes"
                    render={()=>(
                        <KlassList/>
                    )}
                />
                <Route
                    exact
                    path="/students"
                    render={()=>(
                        <StudentList/>
                    )}
                />
            </Grid>
        </Grid>
    </BrowserRouter>
  );
}

export default App;
