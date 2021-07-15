import React from "react";
import {Box, Typography} from "@material-ui/core";
import LogHeaderSection from "./LogHeaderSection";

function LogHeader(props) {
    const {student, bio, interests, strengths, weaknesses, currentStatus} = props;

    return (
        <Box>
            <Typography variant="h5">{student.firstName} {student.lastName}</Typography>
            <LogHeaderSection title="Bio" log={bio}/>
            <LogHeaderSection title="Interests" log={interests}/>
            <LogHeaderSection title="Strengths" log={strengths}/>
            <LogHeaderSection title="Weaknesses" log={weaknesses}/>
            { currentStatus ? (
                <Box>
                    <Typography variant={"subtitle1"}>Current Status:</Typography> {currentStatus.body}
                </Box>
            ) : ""}
        </Box>
    );
}

export default LogHeader;