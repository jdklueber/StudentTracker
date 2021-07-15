import React, {useEffect, useState} from "react";
import NewItemCard from "../ui/NewItemCard";
import {Button, Card, CardContent, makeStyles, TextField, Typography} from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";
import {API_STUDENT} from "../../config/config";

const useStyles = makeStyles(theme => ({
    content: {
        height: 140,
        //display: "flex",
        alignItems: "center",
        justifyContent: "center"
    }

}));

function NewStudentCard(props) {
    const [enterMode, setEnterMode] = useState(false);
    const [studentList, setStudentList] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState(null);
    const classes = useStyles();


    useEffect(() => {
        fetch(API_STUDENT).then(response => response.json())
            .then(data => { setStudentList(data)})
    }, [])

    const handleSave = () => {
        props.addStudent(selectedStudent);
        setEnterMode(false);
        setSelectedStudent(null);
    }

    const getDisplay = () => {
        if (enterMode) {
            return (
                <Card>
                    <CardContent className={classes.content}>
                        <div>
                            <Typography variant="h5">Select a Student</Typography>
                        </div>
                        <div>
                            <Autocomplete
                                options={studentList}
                                getOptionLabel={(option) => option.firstName ? `${option.firstName} ${option.lastName}` : ""}
                                renderInput={(params) => <TextField {...params} label="Student" variant="outlined" />}
                                onChange={(event, newValue) => {setSelectedStudent(newValue)}}
                                value={selectedStudent}
                            />
                        </div>
                        <div>
                            <Button variant='contained' color='primary' onClick={handleSave}>Save</Button>
                        </div>
                    </CardContent>
                </Card>);
        } else {
            return (<NewItemCard text="Add A Student" onClick={()=>{setEnterMode(true)}}/>)
        }
    }

    return(
        <div>
            { getDisplay() }
        </div>
    );
}

export default NewStudentCard;