import React, {useEffect, useState} from "react";
import {API_KLASS} from '../../config/config';
import Header from "../ui/Header";
import {Grid, makeStyles, Typography} from "@material-ui/core";
import RosterCard from "../displays/RosterCard";
import NewStudentCard from "./NewStudentCard";

const useStyles = makeStyles(theme => ({
    grid: {
        marginTop: theme.spacing(2)
    }
}));

function SingleKlass(props) {
    const [klass, setKlass] = useState({});

    const classes = useStyles();

    useEffect(() => {
        fetch(`${API_KLASS}/${props.id}`)
            .then(response => response.json())
            .then(data => setKlass(data));
    },[props]);

    const saveKlass = () => {
        fetch(API_KLASS, {
            method: 'PUT',
            body: JSON.stringify(klass),
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(() => fetch(`${API_KLASS}/${props.id}`)
            .then(response => response.json())
            .then(data => setKlass(data)));
    }

    const handleNewStudent = (student) => {
        klass.roster.push({
            studentId: student.id,
            active: true
        })
        saveKlass();
    }

    const studentCards = () => {
        if (klass.roster) {
            return klass.roster.map(r => (
                <Grid item xs={12} sm={6} md={3} key={r.id}>
                    <RosterCard roster={r}/>
                </Grid>
            ));
        }
    }

    return(
       <div>
           <Header title={klass.name}/>
           <div><Typography variant={"subtitle1"}>Begins:  {klass.startDate}   Ends: {klass.endDate}</Typography></div>
           <Grid container spacing={3} className={classes.grid}>
               <Grid item xs={12} sm={6} md={3}>
                    <NewStudentCard addStudent={handleNewStudent}/>
               </Grid>
               {studentCards()}
               <Grid item>
                   {JSON.stringify(klass)}
               </Grid>
           </Grid>
       </div>
    );
}

export default SingleKlass;