import React from "react";
import {Card, CardContent, makeStyles, Typography} from "@material-ui/core";
import PersonIcon from "@material-ui/icons/Person";
import {DateTime} from "luxon";
import {useHistory} from "react-router-dom";

const useStyles = makeStyles(theme => {
    return({
        root: {

        },
        contentHeight: {
           height: 140
        },
        inFlight: {
            color: "green"
        },
        onDeck: {
            color: "dodgerblue"
        }
    });
});

function KlassCard(props) {
    const classes = useStyles();
    const {klass} = props;
    const history = useHistory();

    const now = DateTime.now();
    const startDate = DateTime.fromISO(klass.startDate);
    const endDate = DateTime.fromISO(klass.endDate);

    const people = klass.roster.map( e=>{return(<PersonIcon key={e.id}/>)});
    const current = now > startDate && now < endDate;
    const onDeck = now < startDate && startDate.diff(now, "weeks").weeks < 3;


    return (
        <Card className={classes.root} onClick={() => { history.push(`/class/${klass.id}`)}}>
            <CardContent className={classes.contentHeight}>
                <Typography variant="h5" className={current ? classes.inFlight : onDeck ? classes.onDeck : ""}>{klass.name}</Typography>
                <Typography variant="subtitle1">{klass.startDate} - {klass.endDate}</Typography>
                <div>{people}</div>
            </CardContent>
        </Card>
    )
}

export default KlassCard;