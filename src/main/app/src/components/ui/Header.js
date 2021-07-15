import React from "react";
import {Divider, makeStyles, Typography} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    margined: {
        marginTop: theme.spacing(2),
        marginBottom: theme.spacing(2)
    }
}));

function Header(props) {
    const classes = useStyles();

    return (
        <div>
            <Typography variant="h3" color="primary">{props.title}</Typography>
            <Divider className={classes.margined}/>
        </div>
    );
}

export default Header;