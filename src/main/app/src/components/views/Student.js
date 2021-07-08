import React, {useState} from "react";
import {
    Button,
    Card,
    CardActions, CardContent,
    CardHeader,
    makeStyles, Paper, TextField
} from "@material-ui/core";

const useStyles = makeStyles(theme=> ({
    paper: {
        margin: theme.spacing(6),
        padding: theme.spacing(3)
    },
    card: {
        minWidth: 300,
    }
}));

function Student(props) {
    const [editMode, setEditMode] = useState(false);
    const student = props.student;
    const classes = useStyles();

    const handleEditButton = function() {
        setEditMode(true);
    }

    const handleFirstNameChange = function(e) {
        props.changeHandler({...student, firstName: e.target.value});
    }

    const handleLastNameChange = function(e) {
        props.changeHandler({...student, lastName: e.target.value})
    }

    const handleSaveButton = function() {
        props.saveHandler();
        setEditMode(false);
    }

    const getDisplay = function() {
        return(
            <Card className={classes.card}>
                <CardHeader title={`${student.firstName} ${student.lastName}`}/>
                <CardActions>
                    <Button variant="contained" color="primary" onClick={handleEditButton}>Edit</Button>
                </CardActions>
            </Card>
        )
    }

    const getEditForm = function() {
        return (
            <Card className={classes.card}>
                <CardContent>
                    <TextField label="First name" value={student.firstName} onChange={handleFirstNameChange}/>
                    <TextField label="Last name" value={student.lastName} onChange={handleLastNameChange}/>
                </CardContent>
                <CardActions>
                    <Button onClick={handleSaveButton} variant="contained" color="primary">Save</Button>
                </CardActions>
            </Card>
        )
    }


    return (
        <Paper className={classes.paper}>

            {editMode || student.newRecord? getEditForm() : getDisplay()}

        <p>No Classes found.</p>
        <p>No log entries found.</p>

        </Paper>
    );

}

export default Student;