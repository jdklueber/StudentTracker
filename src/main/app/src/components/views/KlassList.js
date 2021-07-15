import React, {useEffect, useState} from "react";
import {
    Grid, makeStyles, Typography,
} from "@material-ui/core";
import KlassCard from "../displays/KlassCard";
import {DateTime} from "luxon";
import NewItemCard from "../ui/NewItemCard";
import Header from "../ui/Header";
import {useHistory} from "react-router-dom";

const dateSortAsc = (klass1, klass2) => {
    const d1 = DateTime.fromISO(klass1.startDate);
    const d2 = DateTime.fromISO(klass2.startDate);
    if (d1 < d2) {
        return -1;
    } else if (d2 < d1) {
        return 1;
    } else {
        return 0;
    }
}

const useStyles = makeStyles(theme => ({
    margined: {
        marginTop: theme.spacing(2),
        marginBottom: theme.spacing(2)
    }
}));

function KlassList(props) {
    const [klassList, setKlassList] = useState([]);
    const classes = useStyles();
    const history = useHistory();

    function loadData() {
        fetch("http://localhost:8080/api/klass")
            .then(response => response.json())
            .then(data => setKlassList(data));
    }

    useEffect(() => {
        loadData();
    },[]);

    const isInFlight = (klass) => {
        const now = DateTime.now();
        const startDate = DateTime.fromISO(klass.startDate);
        const endDate = DateTime.fromISO(klass.endDate);

        return now >= startDate && now <= endDate;
    }

    const isOld = (klass) => {
        const now = DateTime.now();
        const endDate = DateTime.fromISO(klass.endDate);

        return now > endDate;

    }

    const isUpcoming = (klass) => {
        const now = DateTime.now();
        const startDate = DateTime.fromISO(klass.startDate);

        return now < startDate;

    }

    const inFlightklasses = klassList.sort(dateSortAsc).filter(e => {return isInFlight(e)}).map(klass => {
       return (
            <Grid item xs={12} sm={6} md={3} key={klass.id}>
                <KlassCard klass={klass}/>
            </Grid>
       );
    });

    const upcomingklasses = klassList.sort(dateSortAsc).filter(e => {return isUpcoming(e)}).map(klass => {
        return (
            <Grid item xs={12} sm={6} md={3} key={klass.id} >
                <KlassCard klass={klass}/>
            </Grid>
        );
    });

    const oldklasses = klassList.sort(dateSortAsc).filter(e => {return isOld(e)}).map(klass => {
        return (
            <Grid item xs={12} sm={6} md={3} key={klass.id}>
                <KlassCard klass={klass}/>
            </Grid>
        );
    });

    return (
        <div>
            <Header title="Classes"/>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6} md={3}>
                    <NewItemCard text="New Class" onClick={()=>{history.push('/classes/new')}}/>
                </Grid>
            </Grid>
            <Typography variant="h5" color="primary" className={classes.margined}>In Flight</Typography>
            <Grid container spacing={3}>
                { inFlightklasses }
            </Grid>
            <Typography variant="h5" color="primary" className={classes.margined}>Upcoming</Typography>
            <Grid container spacing={3}>
                { upcomingklasses.length ? upcomingklasses : <Grid item xs={12} sm={6} md={3}>None Found</Grid> }
            </Grid>
            <Typography variant="h5" color="primary" className={classes.margined}>Historical</Typography>
            <Grid container spacing={3}>
                { oldklasses.length ? oldklasses : <Grid item xs={12} sm={6} md={3}>None Found</Grid> }
            </Grid>
        </div>
    );
}

export default KlassList;