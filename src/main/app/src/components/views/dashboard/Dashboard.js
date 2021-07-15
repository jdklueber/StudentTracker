import React, {useEffect, useState} from "react";
import KlassSelector from "./KlassSelector";
import StudentBar from "./StudentBar";
import LogView from "./LogView";
import LogAdd from "./LogAdd";
import StudentAdd from "./StudentAdd";
import KlassHeader from "./KlassHeader";
import {API_KLASS} from "../../../config/config";

const CONTENT_MODE = {
    EMPTY: 'empty',
    LOGVIEW: 'logview',
    LOGADD: 'logadd',
    STUDENTADD: 'studentadd'
}

function Dashboard(props) {
    const [contentMode, setContentMode] = useState(CONTENT_MODE.EMPTY);
    const [selectedKlass, setSelectedKlass] = useState({});
    const [klassList, setKlassList] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState({id: -1});

    const loadData = () => {
        fetch("http://localhost:8080/api/klass")
            .then(response => response.json())
            .then(data => {
                setKlassList(data);
                if (selectedKlass) {
                    for (let i = 0; i < data.length; i++) {
                        if (selectedKlass.id === data[i].id) {
                            setSelectedKlass(data[i]);
                        }
                    }
                }
            });
    }

    useEffect(() => {
        loadData();
    },[]);

    const content = () => {
        switch (contentMode) {
            case CONTENT_MODE.EMPTY:
                return "";
            case CONTENT_MODE.LOGADD:
                return (<LogAdd klass={selectedKlass} student={selectedStudent}/>);
            case CONTENT_MODE.LOGVIEW:
                return (<LogView student={selectedStudent}/>);
            case CONTENT_MODE.STUDENTADD:
                return (<StudentAdd klass={selectedKlass} reload={loadData}/>);
            default:
                return "";
        }
    }

    const setViewLogMode = () => {
        setContentMode(CONTENT_MODE.LOGVIEW);
    }

    const setAddLogMode = () => {
        setContentMode(CONTENT_MODE.LOGADD);
    }

    const setStudentAddMode = () => {
        setContentMode(CONTENT_MODE.STUDENTADD);
    }

    const updateSelectedKlass = (klass) => {
        setSelectedKlass(klass);
        setSelectedStudent({id: -1})
        setContentMode(CONTENT_MODE.EMPTY);
    }

    const updateSelectedStudent = (id) => {
        setSelectedStudent(id);
        setViewLogMode();
    }

    const updateLogAdd = (id) => {
        setSelectedStudent(id);
        setAddLogMode();
    }

    return (
        <div>
            <KlassSelector setSelectedKlass={updateSelectedKlass} selectedKlass={selectedKlass} klassList={klassList}/>
            {selectedKlass && selectedKlass.name? (<KlassHeader klass={selectedKlass} setAddStudent={setStudentAddMode}/>) : ""}
            {selectedKlass && selectedKlass.name? (<StudentBar klass={selectedKlass} reload={loadData}
                                                               selectedStudent={selectedStudent}
                                                               setSelectedStudent={updateSelectedStudent}
                                                               setAddLog={updateLogAdd}
                                                    />) : ""}
            {content()}

        </div>
    );
}

export default Dashboard;
