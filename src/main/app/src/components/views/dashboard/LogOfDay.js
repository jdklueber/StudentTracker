import React from "react";
import {DateTime} from "luxon";
import {Box, Typography} from "@material-ui/core";
import LogItem from "./LogItem";

function LogOfDay(props) {
    const {day, log} = props;

    return (
        <Box>
            <Typography variant='h6'>{DateTime.fromISO(day).toLocaleString(DateTime.DATETIME_SHORT)}</Typography>
            {log.map(l => {return (<LogItem log={l}/>)})}
        </Box>

    );
}


export default LogOfDay;