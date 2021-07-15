import React from "react";
import StudentItem from "./StudentItem";
import {Grid, makeStyles} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    mt2: { marginTop: theme.spacing(2) }
}));

function StudentBar(props) {
    const {klass, reload, selectedStudent, setSelectedStudent, setAddLog} = props;
    const classes = useStyles();
    return(
        <Grid container spacing={2} className={classes.mt2}>
            {klass.roster.map(roster => {return(
                <Grid item xs={12} sm={6} md={4} lg={3} xl={2} key={roster.id} >
                    <StudentItem roster={roster} reload={reload}
                                 selectedStudent={selectedStudent}
                                 setSelectedStudent={setSelectedStudent}
                                 setAddLog={setAddLog}
                    />
                </Grid>
            )})}
        </Grid>
    );
}

export default StudentBar;