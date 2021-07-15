import React, {useEffect, useState} from "react";
import {
    Box,
    Button,
    FormControl,
    InputLabel,
    makeStyles,
    MenuItem,
    Select,
    TextField,
    Typography
} from "@material-ui/core";
import {API_LOG, API_TAGS} from "../../../config/config";

const useStyles = makeStyles(theme => ({
    mt2: {
        marginTop: theme.spacing(2)
    },
}));

function LogAdd(props) {
    const classes = useStyles();
    const [tags, setTags] = useState([]);
    const [log, setLog] = useState("");
    const [selectedTag, setSelectedTag] = useState();

    useEffect(() => {
        fetch(API_TAGS).then(response => response.json()).then(data => setTags(data));
    }, []);

    const save = () => {
        const logData = {
            tag: selectedTag,
            klassId: props.klass.id,
            studentId: props.student.id,
            body: log
        }

        fetch(API_LOG, {
          method: "POST",
          body: JSON.stringify(logData),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(()=>{setSelectedTag({}); setLog("")});
    }

    return(
        <Box className={classes.mt2}>
            <Typography variant="h5">Log Entry</Typography>
            <FormControl  className={classes.mt2}>
                <InputLabel id='add-log-tag-select-label'>Tag</InputLabel>
                <Select labelId='add-log-tag-select-label'
                        value={selectedTag}
                        onChange={(e)=>{setSelectedTag(e.target.value)}}>
                    {tags.map(t => {return(<MenuItem key={t.id} value={t}>{t.tag}</MenuItem>)})}
                </Select>
            </FormControl>
            <Box>
                <TextField multiline label="Log Entry" maxRows={4} value={log} onChange={(e) => setLog(e.target.value)}/>
            </Box>
            <Box className={classes.mt2}>
                <Button onClick={save} color="primary" variant="contained">Save</Button>
            </Box>
        </Box>
    );
}

export default LogAdd;