import React, {useState} from "react";
import {AppBar,







    makeStyles, Tab, Tabs, Toolbar, Typography} from "@material-ui/core";
import {Link} from "react-router-dom";


const useStyles = makeStyles(theme => (
    {
        root: {
            flexGrow: 1
        },
        flex: {
            flex: 1
        },
        menuButton: {
            marginLeft: -12,
            marginRight: 20
        },
        toolbarMargin: theme.mixins.toolbar
    }
));

function NavigationBar(props) {
    const classes = useStyles();
    const [tab, setTab] = useState(0);

    const onChange = (e, value) => {
        setTab(value);
    }

    return (
        <div className={classes.root}>
              <AppBar position="fixed">
                  <Toolbar>
                      <Typography variant="h5" color="inherit" className={classes.flex}>
                          Student Tracker
                      </Typography>
                  </Toolbar>
              </AppBar>
            <div className={classes.toolbarMargin}/>
        </div>
    );
}

export default NavigationBar;