import React, {Component} from "react";
import Select from "react-select";
import SmellTable from "../components/SmellTable";
import SmellGraph from "../components/SmellGraph";
import { Row, Col } from "react-bootstrap";
import getData from "../util/getData";

//TODO: update endpoints when API is implemented
export default class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            total: [],
            timeOptions: [
                {value: 0, label: "All Time"},
                {value: 1, label: "Past Day"},
                {value: 7, label: "Past Week"},
                {value: 30, label: "Past Month"},
                {value: 365, label: "Past Year"}
            ],
            data: [],
        }
    }

    render() {
        const {data, total, timeOptions, loaded} = this.state;
        if (!loaded) return <h1>Loading...</h1>

        const timeFilter=(e)=>{
            getData('/data/' + e.value)
                .then((json) => {
                    this.setState({
                        data: json,
                    })
                })
            this.setState({
                timeFrame: e
            })
        }

        const smellFilter=(e)=>{
            console.log(e)
            let smells = ""
            e.map((option) => (smells += option.value + "+"))
            // getData('/data/smells/' + smells)
            //     .then((json) => {
            //         this.setState({
            //             data: json,
            //         })
            //     })
        }

        return (
            <>
                <Row>
                    <h1 className="page-title" >TSDetect Dashboard</h1>
                </Row>
                <hr />
                <Row>
                    <Col sm={3}>
                        <h4>Filter by Time</h4>
                        <Select
                            defaultValue={timeOptions[0]}
                            options={timeOptions}
                            onChange={timeFilter}
                        />
                    </Col>
                    <Col>
                        <h4>Filter by Smell</h4>
                        <Select
                            defaultValue={Object.keys(total).map((key) => ( {value: key, label: key} ))}
                            isMulti
                            options={Object.keys(total).map((key) => ( {value: key, label: key} ))}
                            onChange={smellFilter}
                        />
                    </Col>
                </Row>
                <br />
                <Row>
                    <Col>
                        <Row className="padding-left-right">
                            <Col className="header-data-item-dark">
                                <h3><strong>Total Smells</strong></h3>
                                <h4>{Object.values(total).reduce((a, b) => a + b, 0)}</h4>
                            </Col>
                            <Col className="header-data-item-light">
                                <h3><strong>New Smells</strong></h3>
                                <h4>{Object.values(data).reduce((a, b) => a + b, 0)}</h4>
                            </Col>
                        </Row>
                    </Col>
                </Row>
                <br />
                <Row className="padding-left-right">
                    <SmellGraph data={data} />
                </Row>
                <br />
                <Row className="padding-left-right">
                    <SmellTable data={data} />
                </Row>
            </>
        );
    }

    componentDidMount() {
        getData('/data/0')
            .then((json) => {
                this.setState({
                    total: json,
                    data: json,
                    loaded: true
                })
            })
    }
}