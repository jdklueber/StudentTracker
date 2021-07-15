import React, {useEffect, useState} from "react";
import {API_STUDENT} from "../../../config/config";
import {DateTime} from "luxon";
import LogOfDay  from './LogOfDay';
import LogHeader from "./LogHeader";

function LogView(props) {
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        fetch(API_STUDENT + "/" + props.student.id + "/logs")
            .then(response => response.json())
            .then(data => setLogs(data));
    }, [props])

    const bio = logs.filter(l => {return l.tag.tag === "Bio"});
    const interests = logs.filter(l => {return l.tag.tag === "Interests"});
    const strengths = logs.filter(l => {return l.tag.tag === "Strengths"});
    const weaknesses = logs.filter(l => {return l.tag.tag === "Weaknesses"});
    const chronlog = logs.filter(l => {return (l.tag.tag !== "Bio"
                                           && l.tag.tag !== "Interests"
                                           && l.tag.tag !== "Strengths"
                                           && l.tag.tag !== "Weaknesses"
    )}).sort((a, b) => {
        const a_ts = DateTime.fromISO(a.timestamp);
        const b_ts = DateTime.fromISO(b.timestamp);

        if (b_ts < a_ts) {
            return -1;
        } else if (b_ts > a_ts) {
            return 1;
        } else {
            return 0;
        }
    });
    const logsByDay = {};
    const days = [];

    chronlog.forEach(l => {
       const day = DateTime.fromISO(l.timestamp).startOf('Day').toISO();
       if (!logsByDay[day]) {
           logsByDay[day] = [];
           days.push(day);
       }

       logsByDay[day].push(l);
    });

    const current_status = chronlog.filter(l => {return l.tag.tag === "Status"}).shift();
    return(
      <div>
          <LogHeader student={props.student} bio={bio} interests={interests} strengths={strengths} weaknesses={weaknesses} currentStatus={current_status} />
          {days.map(day => {return (<LogOfDay day={day} log={logsByDay[day]}/>)})}
      </div>
    );
}

export default LogView;