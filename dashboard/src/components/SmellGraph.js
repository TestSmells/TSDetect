import React, {Component} from "react"
import { Table, Spinner } from "react-bootstrap";
import getData from "../util/getData";
import { Bar } from "react-chartjs-2";

export default class SmellGraph extends Component {
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

        console.log(data)
        return(
            <Bar
                className="padding-left-right"
                data={
                    {
                        labels: data.map(data => data.name),
                        datasets: [{
                            label: 'Smell Count',
                            backgroundColor: '#009688',
                            borderColor: 'rgb(255, 99, 132)',
                            data: data.map(data => data.testSmellId),
                        }]
                    }
                }

            />
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
