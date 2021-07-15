import React from "react";
import {Card, CardContent, makeStyles, Typography} from "@material-ui/core";
import {AddCircleOutline} from "@material-ui/icons";

const useStyles = makeStyles(theme => ({
    content: {
        height: 140,
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    },
    icon: {
        verticalAlign: "middle",
        fontSize: "xxx-large"
    }
}));

function NewItemCard(props) {
    const classes = useStyles();

    return (
      <Card onClick={props.onClick}>
          <CardContent className={classes.content}>
              <Typography variant="h5"><AddCircleOutline className={classes.icon} fontSize="large"/> {props.text} </Typography>
          </CardContent>
      </Card>
    );
}

export default NewItemCard;