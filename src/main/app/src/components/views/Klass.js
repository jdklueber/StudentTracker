import 'luxon';
import React, {useState} from "react";
import {Button, Card, CardActions, CardContent, CardHeader, makeStyles, TextField} from "@material-ui/core";
import {DateTime} from "luxon";

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(2),
        marginTop: theme.spacing(2)
    }
}));

function Klass(props) {
    const classes = useStyles();
    const {klass, saveHandler, changeHandler} = props;

    const [editMode, setEditMode] = useState(false);

    const handleNameChange = (evt) => {
        changeHandler({...klass, name: evt.target.value});
    }

    const handleStartDateChange = (evt) => {
        const startDate = DateTime.fromISO(evt.target.value).toISODate();
        changeHandler({...klass, startDate});
    }

    const handleEndDateChange = (evt) => {
        const endDate = DateTime.fromISO(evt.target.value).toISODate();
        changeHandler({...klass, endDate});
    }


    const getDisplay = () => {
        return (
            <Card className={classes.root}>
                <CardHeader title={klass.name} subheader={`${klass.startDate} - ${klass.endDate}`}/>
                <CardActions>
                    <Button variant="contained" color="primary" onClick={() => {setEditMode(true)}}>Edit</Button>
                </CardActions>
            </Card>
        )
    }

    const getEdit = () => {
        return (
            <Card className={classes.root}>
                <CardContent>
                    <TextField label="Class Name" value={klass.name} onChange={handleNameChange}/>

                    <TextField label="Start Date" type='date' onChange={handleStartDateChange} value={klass.startDate}/>
                    <TextField label="End Date" type='date' onChange={handleEndDateChange} value={klass.endDate}/>

                </CardContent>
                <CardActions>
                    <Button variant="contained" color="primary" onClick={() => {saveHandler(); setEditMode(false)}}>Save</Button>
                </CardActions>
            </Card>
        )
    }

    return (
      <div>
          {editMode || klass.newRecord? getEdit() : getDisplay()}
      </div>
    );
}

export default Klass;