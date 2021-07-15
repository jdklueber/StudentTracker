import React from "react";
import {Box, Typography} from "@material-ui/core";

function LogHeaderSection(props) {
    const {title, log} = props;

    return (
        <Box>
            <Typography variant={"subtitle1"}>{title}</Typography>
            <ul>
                {log.map(l => {return (<li key={l.id}>{l.body}</li>)})}
            </ul>
        </Box>
    );
}

export default LogHeaderSection;