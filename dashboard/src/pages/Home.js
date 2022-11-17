import React, { useState } from "react";
import SmellTable from "../components/SmellTable";
import SmellGraph from "../components/SmellGraph";
import { Row, Col, Dropdown, ButtonGroup } from "react-bootstrap";

function updateStats() {
    // Stubbed function
}

function Home() {
    const [timeframe, setTimeframe] = useState("Past Day");
    const [headerData, setHeaderData] = useState([
        {
            text: "Total Smells",
            value: 100
        },
        {
            text: "New Smells",
            value: 100
        },
        {
            text: "Total Users",
            value: 100
        },
        {
            text: "New Users",
            value: 100
        }
    ])

    return(
        <>
            <Row>
                <h1 className="page-title" >TSDetect Dashboard</h1>
            </Row>
            <hr></hr>
            <Row>
                <Col sm={2}>
                <h4>Filtering Options</h4>
                <Dropdown 
                    className="filtering-dropdown"
                    as={ButtonGroup}
                    onSelect={setTimeframe}
                >
                    <Dropdown.Toggle variant="secondary">
                        {timeframe}
                    </Dropdown.Toggle>
                    <Dropdown.Menu className="filtering-dropdown-menu" variant="secondary">
                        <Dropdown.Item eventKey="Past Day" active>Past Day</Dropdown.Item>
                        <Dropdown.Item eventKey="Past Week" >Past Week</Dropdown.Item>
                        <Dropdown.Item eventKey="Past Month" >Past Month</Dropdown.Item>
                        <Dropdown.Item eventKey="Past Year" >Past Year</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
                </Col>
                <Col>
                    <Row className="padding-left-right">
                        {headerData.map((headerDataItem, idx) =>
                            <Col {...idx % 2 == 0 ? {className: "header-data-item-even"} : {className: "header-data-item-odd"}}>
                                <h3><strong>{headerDataItem.text}</strong></h3>
                                <h4>{headerDataItem.value}</h4>
                            </Col>
                        )}
                    </Row>
                    <Row className="padding-left-right">
                        <SmellGraph />
                    </Row>
                    <Row className="padding-left-right">
                        <SmellTable />
                    </Row>
                </Col>
            </Row>
        </>
    )
}

export default Home;
