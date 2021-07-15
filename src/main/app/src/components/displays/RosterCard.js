import React from "react";
import {Card, CardContent, makeStyles, Typography} from "@material-ui/core";

const useStyles = makeStyles(theme => {
    return({
        contentHeight: {
            height: 140,
            alignItems: "center",
            justifyItems: "center",
        }
    });
});

function RosterCard(props) {
    const {roster} = props;
    const classes = useStyles();

    return(
        <Card>
            <CardContent className={classes.contentHeight}>
                <Typography variant="h5">{roster.student.firstName} {roster.student.lastName}</Typography>
            </CardContent>
        </Card>
    );
}

export default RosterCard;