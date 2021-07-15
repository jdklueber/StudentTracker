import './App.css';
import KlassList from "./components/views/KlassList";
import NavigationBar from "./components/layout/NavigationBar";
import {BrowserRouter, Route} from "react-router-dom";
import StudentList from "./components/views/StudentList";
import SideBar from "./components/layout/SideBar";
import {Grid, makeStyles} from "@material-ui/core";
import NewKlass from "./components/views/NewKlass";
import SingleKlass from "./components/views/SingleKlass";
import Dashboard from "./components/views/dashboard/Dashboard";

const useStyles = makeStyles(theme => ({
    grid: {
        padding: theme.spacing(2)
    }
}));

function App() {
    const classes = useStyles();
  return (
    <BrowserRouter>
        <NavigationBar/>
        <Grid container>
            <Grid item xs={2}>
                <SideBar/>
            </Grid>
            <Grid item xs={10} className={classes.grid}>
                <Route
                    exact
                    path="/"
                    render={()=>(
                        <Dashboard/>
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
                <Route
                    exact
                    path="/classes/new"
                    render={(data)=>(
                        <NewKlass/>
                    )}
                />
                <Route
                    exact
                    path="/class/:id"
                    render={(data) => (
                        <SingleKlass id={data.match.params.id}/>
                    )}/>
            </Grid>
        </Grid>
    </BrowserRouter>
  );
}

export default App;
