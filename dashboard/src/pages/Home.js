import React, {Component} from "react";
import SmellTable from "../components/SmellTable";
import SmellGraph from "../components/SmellGraph";
import { Row, Col, Dropdown, ButtonGroup } from "react-bootstrap";
import getData from "../util/getData";

//TODO filtering, users, verify real server
export default class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            data: [],
            users: [],
            timeFrame: "All Time"
        }
    }

    render() {
        const {data, users, loaded, timeFrame} = this.state;
        if (!loaded) return <h1>Loading...</h1>

        const filter=(e)=>{
            getData('/data/' + e)
                .then((json) => {
                    this.setState({
                        data: json,
                        loaded: true
                    })
                })
            this.setState({
                timeFrame: e
            })
        }

        return (
            <>
                <Row>
                    <h1 className="page-title" >TSDetect Dashboard</h1>
                </Row>
                <hr />
                <Row>
                    <Col sm={2}>
                        <h4>Filtering Options</h4>
                        <Dropdown
                            className="filtering-dropdown"
                            as={ButtonGroup}
                            onSelect={filter}
                        >
                            <Dropdown.Toggle variant="secondary">
                                {timeFrame}
                            </Dropdown.Toggle>
                            <Dropdown.Menu className="filtering-dropdown-menu" variant="secondary">
                                <Dropdown.Item eventKey="All Time">All Time</Dropdown.Item>
                                <Dropdown.Item eventKey="Past Day" >Past Day</Dropdown.Item>
                                <Dropdown.Item eventKey="Past Week" >Past Week</Dropdown.Item>
                                <Dropdown.Item eventKey="Past Month" >Past Month</Dropdown.Item>
                                <Dropdown.Item eventKey="Past Year" >Past Year</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Col>
                    <Col>
                        <Row className="padding-left-right">
                            <Col className="header-data-item-dark">
                                <h3><strong>Total Smells</strong></h3>
                                <h4>{Object.values(data).reduce((a, b) => a + b, 0)}</h4>
                            </Col>
                            <Col className="header-data-item-light">
                                <h3><strong>New Smells</strong></h3>
                                <h4>{Object.values(data).reduce((a, b) => a + b, 0)}</h4>
                            </Col>
                            <Col className="header-data-item-dark">
                                <h3><strong>Total Users</strong></h3>
                                <h4>{Object.values(users).reduce((a, b) => a + b, 0)}</h4>
                            </Col>
                            <Col className="header-data-item-light">
                                <h3><strong>New Users</strong></h3>
                                <h4>{Object.values(users).reduce((a, b) => a + b, 0)}</h4>
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
        getData('/data/all_time')
            .then((json) => {
                this.setState({
                    data: json,
                    loaded: true
                })
            })
        // getData('/users')
        //     .then((json) => {
        //         this.setState({
        //             users: json,
        //             loaded: true
        //         })
        //     })
    }
}