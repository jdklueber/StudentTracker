import React from "react";
import {Link} from "react-router-dom";
import {List, ListItem, ListItemText, makeStyles} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    root: {
        backgroundColor: "#F5F5F5",
        minHeight: "95vh"
    }
}));

function SideBar(props) {
    const classes = useStyles();
    return (
        <div className={classes.root}>
            <List>
                <ListItem button component={Link} to='/classes'>
                    <ListItemText primary='Classes' />
                </ListItem>
                <ListItem button component={Link} to='/students'>
                    <ListItemText primary='Students' />
                </ListItem>
            </List>
        </div>
    );
}

export default SideBar;