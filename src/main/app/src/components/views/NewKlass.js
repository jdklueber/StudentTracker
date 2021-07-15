import React, {useState} from "react";
import Header from "../ui/Header";
import {Button, makeStyles, TextField} from "@material-ui/core";
import {DateTime} from "luxon";
import {useHistory} from "react-router-dom";

const useStyles = makeStyles(theme => ({
    button: {
        marginTop: theme.spacing(2)
    }
}));

function NewKlass(props) {
    const [name, setName] = useState("");
    const [startDate, setStartDate] = useState(DateTime.now().toISODate());
    const [endDate, setEndDate] = useState(DateTime.now().plus({months: 1}).toISODate());
    const handleNameChange = e => { setName(e.target.value) };

    const classes = useStyles();
    const history = useHistory();

    const handleDateChange = (setter) => e => {
        setter(e.target.value)
    };

    const handleSave = () => {
        const newClass = {
            name,
            startDate,
            endDate
        };

        fetch("http://localhost:8080/api/klass", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newClass)
        })
            .then((response) => {
                return response.json();
            }).then( data => {
                history.push(`/class/${data.id}`);
            }
        );
    }

    return(
        <div>
            <Header title="New Class"/>
            <TextField label="Class Name" value={name} onChange={handleNameChange}/>
            <TextField type='date' label="Start Date" value={startDate} onChange={handleDateChange(setStartDate)}/>
            <TextField type='date' label="Start Date" value={endDate} onChange={handleDateChange(setEndDate)}/>
            <div>
                <Button className={classes.button} variant='contained' color='primary' onClick={handleSave}>Save</Button>
            </div>
        </div>
    );
}

export default NewKlass;