import React, {useState} from "react"
import { Bar } from "react-chartjs-2";
import Sketch from "@uiw/react-color-sketch";
import {Col, Dropdown, DropdownButton, Row} from "react-bootstrap";

export default function SmellGraph({ data }) {
    const [hex, setHex] = useState(localStorage.getItem('hex') !== null ? localStorage.getItem('hex') : '#009688')
    const [fontSize, setFontSize] = useState(localStorage.getItem('fontSize') != null ? localStorage.getItem('fontSize') : 16)

    const options = {
        scales: {
            x: {
                ticks: {
                    font: {
                        size: fontSize
                    }
                }
            },
            y: {
                ticks: {
                    font: {
                        size: fontSize
                    }
                }
            }
        },
        plugins: {
            legend: {
                labels: {
                    font: {
                        size: fontSize
                    }
                }
            },
            tooltip: {
                titleFont: {
                    size: fontSize
                },
                bodyFont: {
                    size: fontSize
                }
            }
        }
    }
    return(
        <>
            <Row>
                <Col>
                    <Dropdown autoClose="outside">
                        <Dropdown.Toggle variant='secondary'>
                            Color
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            <Dropdown.Item>
                                <Sketch
                                    color={hex}
                                    onChange={(color) => {
                                        setHex(color.hex)
                                        localStorage.setItem('hex', color.hex)
                                    }}
                                />
                            </Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </Col>
                <Col>
                    <DropdownButton
                        title="Font"
                        variant="secondary"
                        id="fontDropdown"
                        onSelect={(size) => {
                            setFontSize(size)
                            localStorage.setItem('fontSize', size)
                        }}
                    >
                        <Dropdown.Item eventKey="12">12</Dropdown.Item>
                        <Dropdown.Item eventKey="16">16</Dropdown.Item>
                        <Dropdown.Item eventKey="20">20</Dropdown.Item>
                        <Dropdown.Item eventKey="24">24</Dropdown.Item>
                        <Dropdown.Item eventKey="28">28</Dropdown.Item>
                        <Dropdown.Item eventKey="32">32</Dropdown.Item>
                    </DropdownButton>
                </Col>
            </Row>
            <Bar
                className="padding-left-right"
                data={
                    {
                        labels: Object.keys(data),
                        datasets: [{
                            label: 'Smell Count',
                            backgroundColor: hex,
                            borderColor: 'rgb(255, 99, 132)',
                            data: Object.values(data),
                        }]
                    }
                }
                options={options}
            />
        </>
    )
}
