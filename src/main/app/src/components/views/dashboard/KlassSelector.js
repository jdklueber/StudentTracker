import React, {useState} from "react";
import {Box, Button, makeStyles, TextField} from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";
import {DateTime} from "luxon";

const FILTERS = {
    IN_FLIGHT: 'inflight',
    UPCOMING: 'upcoming',
    HISTORICAL: 'historical'
}

const useStyles = makeStyles(theme => ({
    mt2: { marginTop: theme.spacing(2) }
}));

function KlassSelector(props) {
    const [filter, setFilter] = useState(FILTERS.IN_FLIGHT);
    const {klassList} = props;
    const classes = useStyles();

    const filterKlasses = (e) => {
        const now = DateTime.now();
        const startDate = DateTime.fromISO(e.startDate);
        const endDate = DateTime.fromISO(e.endDate);

        switch (filter) {
            case FILTERS.IN_FLIGHT:
                return now >= startDate && now <= endDate;
            case FILTERS.UPCOMING:
                return now < startDate;
            case FILTERS.HISTORICAL:
                return now > endDate;
            default:
                return false;
        }
    }

    return (
        <div>
            <Button variant={filter === FILTERS.IN_FLIGHT ? "contained" : "text"} onClick={() => {setFilter(FILTERS.IN_FLIGHT); props.setSelectedKlass({});}}>In Flight</Button>
            <Button variant={filter === FILTERS.UPCOMING ? "contained" : "text"} onClick={() => {setFilter(FILTERS.UPCOMING); props.setSelectedKlass({});}}>Upcoming</Button>
            <Button variant={filter === FILTERS.HISTORICAL ? "contained" : "text"} onClick={() => {setFilter(FILTERS.HISTORICAL); props.setSelectedKlass({});}}>Historical</Button>
            <Box className={classes.mt2}>
                <Autocomplete
                    options={klassList.filter(filterKlasses)}
                    getOptionLabel={(option) => option.name ? option.name : ""}
                    renderInput={(params) => <TextField {...params} label="Class" variant="outlined" />}
                    onChange={(event, newValue) => {props.setSelectedKlass(newValue)}}
                    value={props.selectedKlass}
                />
            </Box>
        </div>
    );
}

export default KlassSelector;