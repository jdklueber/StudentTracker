import React from "react";
import {DateTime} from "luxon";
import {makeStyles} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    tag: {
        fontWeight: "bold"
    }
}));

function LogItem(props) {
    const {log} = props;
    const classes = useStyles();

    return (
        <div>
            <span className={classes.tag}>{log.tag.tag}</span> {log.body}
        </div>
    );
}

export default LogItem;