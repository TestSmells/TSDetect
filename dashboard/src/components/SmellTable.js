import React from "react"
import { Table } from "react-bootstrap";

function SmellTable() {
    // TODO: populate table with actual values which will be passed down as prop and map
    // the avalues to <tr> tags in the <tbody>
    return(
        <Table bordered>
            <thead>
                <tr>
                    <th>Smell Type</th>
                    <th>Number of Occurences</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Assertion Roulette</td>
                    <td>100</td>
                </tr>
                <tr>
                    <td>Slow Test</td>
                    <td>100</td>
                </tr>
                <tr>
                    <td>Lazy Test</td>
                    <td>100</td>
                </tr>
            </tbody>
        </Table>
    )
}

export default SmellTable;
