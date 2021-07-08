import React, {useEffect, useState} from "react";
import {
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TableSortLabel
} from "@material-ui/core";
import Klass from "./Klass";
import {DateTime} from "luxon";

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

function KlassList(props) {
    const [klassList, setKlassList] = useState([]);
    const [selectedKlass, setSelectedKlass] = useState();

    function loadData() {
        fetch("http://localhost:8080/api/klass")
            .then(response => response.json())
            .then(data => setKlassList(data));
    }

    useEffect(() => {
        loadData();
    },[]);

    const [columns, setColumns] = useState([
        {name: 'name', active: false },
        {name: 'startDate', active: false },
        {name: 'endDate', active: false },
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

        setKlassList(
            klassList.slice().sort(
                comparator(columns[index].name,
                    columns[index].order === 'desc')
            )
        );
    };

    const updateKlass = function(updatedKlass) {
        setSelectedKlass(updatedKlass);
    }

    const saveKlass = function() {
        const HTTP_VERB = selectedKlass.id === null ? 'POST' : 'PUT';

        fetch("http://localhost:8080/api/klass", {
            method: HTTP_VERB,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(selectedKlass)
        })
            .then(response => loadData());

    }

    return (
        <div>
            <Button onClick={() => {setSelectedKlass({newRecord: true, name: "", startDate: DateTime.now().toISODate(), endDate: DateTime.now().plus({months: 1}).toISODate()})}}>Add</Button>
            <TableContainer component={Paper}>
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
                        {klassList.map( (row) =>  (
                                <TableRow key={row.id} onClick={()=>{setSelectedKlass(row)}}>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell>{row.startDate}</TableCell>
                                    <TableCell>{row.endDate}</TableCell>
                                </TableRow>
                            )
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            { selectedKlass ? <Klass klass={selectedKlass} saveHandler={saveKlass} changeHandler={updateKlass}/> : "" }
        </div>
    )
}

export default KlassList;