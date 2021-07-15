import React from "react";
import {Box, Button, Card, CardActions, CardContent, CardHeader, makeStyles, Typography} from "@material-ui/core";
import {API_ROSTER} from "../../../config/config";

const useStyles = makeStyles(theme => ({
    inactive: { color: "silver" },
    selected: { backgroundColor: "honeydew"},
    unselected: { backgroundColor: "inherit"}
}));

function StudentItem(props) {
    const {roster, selectedStudent, setAddLog, setSelectedStudent, reload} = props;
    const student = roster.student;
    const classes = useStyles();

    const toggleEnroll = () => {
        roster.active = !roster.active;
        fetch(API_ROSTER, {
            method: "PUT",
            body: JSON.stringify(roster),
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(() => {reload()})
    }

    const getHighlightedClass = () => {
        if (selectedStudent && selectedStudent.id && student && student.id && selectedStudent.id === student.id) {
            return classes.selected;
        }

        return classes.unselected;
    }

    const getStudentName = () => {
        if (student && student.firstName && student.lastName) {
            return `${student.firstName} ${student.lastName}`;
        }

        return "";
    }

    return(
        <Box>
            <Card className={getHighlightedClass()}>
                <CardContent>
                    <Typography variant="body1" onClick={() => {setSelectedStudent(student)}} className={roster.active === false ? classes.inactive : ""}>{getStudentName()}</Typography>

                </CardContent>
                <CardActions>
                    <Button onClick={toggleEnroll}>{roster.active? "Drop" : "Re-enroll"}</Button>
                    <Button onClick={() => {setSelectedStudent(student)}}>View Logs</Button>
                    <Button onClick={() => {setAddLog(student)}}>Add Log</Button>
                </CardActions>
            </Card>
        </Box>
    );
}

export default StudentItem;