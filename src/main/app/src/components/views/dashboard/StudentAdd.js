import React, {useEffect, useState} from "react";
import {API_KLASS, API_STUDENT} from "../../../config/config";
import {Box, Button, Card, CardContent, makeStyles, TextField, Typography} from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";

const useStyles = makeStyles(theme => ({
    mt2: { marginTop: theme.spacing(2)}
}));

function StudentAdd(props) {
    const {klass, reload} = props;
    const [studentList, setStudentList] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState(null);
    const classes = useStyles();


    useEffect(() => {
        fetch(API_STUDENT).then(response => response.json())
            .then(data => { setStudentList(data)})
    }, [])

    const handleSave = () => {
        klass.roster.push({studentId: selectedStudent.id, active: true});
        setSelectedStudent({})
        fetch(API_KLASS, {
            method: 'PUT',
            body: JSON.stringify(klass),
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(() => reload());
    }

    const existingStudentIds = klass.roster.map(e => e.studentId);
    const filteredList = studentList.filter(e => !existingStudentIds.includes(e.id));

    return(
        <Box className={classes.mt2}>
                <Typography variant="h5">Select a Student</Typography>
                <div>
                    <Autocomplete
                        options={filteredList}
                        getOptionLabel={(option) => option.firstName ? `${option.firstName} ${option.lastName}` : ""}
                        renderInput={(params) => <TextField {...params} label="Student" variant="outlined" />}
                        onChange={(event, newValue) => {setSelectedStudent(newValue)}}
                        value={selectedStudent}
                    />
                </div>
                <div>
                    <Button variant='contained' color='primary' onClick={handleSave}>Save</Button>
                </div>

        </Box>
    );
}

export default StudentAdd;