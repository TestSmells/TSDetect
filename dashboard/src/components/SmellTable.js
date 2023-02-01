import React, {Component} from "react"
import { Table, Spinner } from "react-bootstrap";
import getData from "../util/getData";

export default class SmellTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            data: [],
        }
    }

    render() {
        const {loaded, data} = this.state;
        if (!loaded) return <Spinner animation="border" variant="success" />

        return (
            <Table bordered>
                <thead>
                <tr>
                    <th>Smell Type</th>
                    <th>Number of Occurrences</th>
                </tr>
                </thead>
                <tbody>
                {data.map((smell) =>
                    <tr key={smell.testSmellId}>
                        <td key={smell.name}>{smell.name}</td>
                        <td key={smell.testSmellId}>{smell.testSmellId}</td>
                    </tr>
                )}
                </tbody>
            </Table>
        )
    }

    componentDidMount() {
        getData('/test-smells')
            .then((json) => {
                this.setState({
                    data: json,
                    loaded: true
                })
            })
    }
}
