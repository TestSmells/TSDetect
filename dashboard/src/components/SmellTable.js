import React from "react"
import { Table } from "react-bootstrap";

export default function SmellTable({ data }) {
    return (
        <Table striped bordered>
            <thead>
            <tr>
                <th>Smell Type</th>
                <th>Number of Occurrences</th>
            </tr>
            </thead>
            <tbody>
            {Object.keys(data).map((key) =>
                <tr key={key}>
                    <td key={key}>{key}</td>
                    <td key={data[key]}>{data[key]}</td>
                </tr>
            )}
            </tbody>
        </Table>
    )
}
