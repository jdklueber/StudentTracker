import React from "react";
import {Box, Button, makeStyles, Typography} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    mt2: { marginTop: theme.spacing(2) }
}));

function KlassHeader(props) {
    const {klass} = props;
    const classes = useStyles();

    return (
      <Box className={classes.mt2}>
          <Typography variant={"h3"}>{klass.name}</Typography>
          <Typography variant="subtitle1">{klass.startDate} to {klass.endDate}</Typography>
          <Button onClick={props.setAddStudent}>Add Student</Button>
      </Box>
    );
}

export default KlassHeader;