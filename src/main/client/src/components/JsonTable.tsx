import React, {Component} from 'react';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import Table from '@material-ui/core/Table';

interface IJsonTableProps {
    data: any[];
}

export class JsonTable extends Component<IJsonTableProps> {
    public render() {
        const {data} = this.props;
        return <Table>
            <TableHead>
                <TableRow>{Object.keys(data[0]).map((key, i) => <TableCell key={i}>{key}</TableCell>)}</TableRow>
            </TableHead>
            <TableBody>
                {data.map((row: any, i) => <TableRow key={i}>{Object.values(row).map((c: any, j) => <TableCell key={j}>{c}</TableCell>)}</TableRow>)}
            </TableBody>
        </Table>
    };
}