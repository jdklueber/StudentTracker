import React, {useEffect, useState} from "react";
import Student from "./Student";
import {
    Button,
    makeStyles,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow, TableSortLabel
} from "@material-ui/core";


const comparator = (prop, desc=true) => (a, b) => {
    const SMALLER = -1;
    const BIGGER = 1;
    const EQUAL = 0;

    const order = desc ? -1 : 1;

    if (a[prop] < b[prop]) {
        return SMALLER * order;
    }

    if (a[prop] > b[prop]) {
        return BIGGER * order;
    }

    return EQUAL;
}

const useStyles = makeStyles(theme => ({
    root: {}
}));

function StudentList(props) {
    const classes = useStyles();

    const [students, setStudentList] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState();

    const loadData = () => {
        fetch("http://localhost:8080/api/student")
            .then(response => response.json())
            .then(data => setStudentList(data));
    }

    useEffect(loadData,[]);


    const handleSelectedStudent = (student) => {
        setSelectedStudent(student);
    }

    const handleAddStudent = () => {
        setSelectedStudent({newRecord: true});
    }

    const updateSelectedStudent = (student) => {
        setSelectedStudent(student);
    }

    const saveSelectedStudent = () => {
        if (selectedStudent != null) {
            const HTTP_VERB = selectedStudent.id === null ? 'POST' : 'PUT';

            fetch("http://localhost:8080/api/student", {
                method: HTTP_VERB,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(selectedStudent)
            })
            .then(response => loadData());

        }
        setSelectedStudent(null);
    }

    const [columns, setColumns] = useState([
        { name: 'firstName', active: false},
        { name: 'lastName', active: false}
    ]);

    const onSortClick = index => () => {
        setColumns(
            columns.map((column, i) => ({
                ...column,
                    active: index === i,
                    order:
                            (index === i && (column.order === 'desc' ? 'asc' : 'desc')) || undefined
            }))
        );

        setStudentList(
            students.slice().sort(
                comparator(columns[index].name,
                    columns[index].order === 'desc')
            )
        );
    };

    return (
        <div>
            <Button onClick={handleAddStudent}>Add</Button>

            <TableContainer component={Paper} className={classes.root}>
                <Table>
                    <TableHead>
                        <TableRow>
                            {columns.map((column, index) => (
                                <TableCell key={column.name}>
                                    <TableSortLabel active={column.active} direction={column.order} onClick={onSortClick(index)}>
                                        {column.name}
                                    </TableSortLabel>
                                </TableCell>
                            )
                            )}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {students.map( (row) =>  (
                                <TableRow key={row.id} onClick={()=>{handleSelectedStudent(row)}}>
                                    <TableCell>{row.firstName}</TableCell>
                                    <TableCell>{row.lastName}</TableCell>
                                </TableRow>
                            )
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedStudent? <Student student={selectedStudent} changeHandler={updateSelectedStudent} saveHandler={saveSelectedStudent}/> : "" }
        </div>

    )
}

export default StudentList;