import React, {Component} from "react"
import { Bar } from "react-chartjs-2"
import {Col, Dropdown, DropdownButton, Row} from "react-bootstrap"
import Sketch from "@uiw/react-color-sketch"
import {CirclePicker} from "react-color"

export default class SmellGraph extends Component {
    constructor(props) {
        super(props)
        this.state = {
            defaultHex: '#009688',
            hex: localStorage.getItem('hex') !== null ? localStorage.getItem('hex') : '#009688',
            fontSize: localStorage.getItem('fontSize') != null ? localStorage.getItem('fontSize') : 16,
            colors: localStorage.getItem('colors') !== null ? localStorage.getItem('colors').split(',') : (localStorage.getItem('hex') !== null ? localStorage.getItem('hex') : ['#009688'])
        }
    }

    render() {
        const {defaultHex, hex, fontSize, colors} = this.state
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

        return (
            <>
                <Row>
                    <Col>
                        <Dropdown autoClose="outside">
                            <Dropdown.Toggle variant='secondary'>
                                Color
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.ItemText>Primary Color</Dropdown.ItemText>
                                <Dropdown.ItemText>
                                    <Sketch
                                        color={hex}
                                        presetColors={['#ff0000', '#ffa500', '#ffff00', '#00ff00',
                                                       '#009688', '#0000ff', '#9300ff', '#e01fe0']}
                                        onChange={(color) => {
                                            colors[0] = color.hex
                                            this.setState({
                                                hex: color.hex,
                                                colors: colors
                                            })
                                            localStorage.setItem('hex', color.hex)
                                            localStorage.setItem('colors', colors)
                                        }}
                                    />
                                </Dropdown.ItemText>
                                <Dropdown.Item
                                    className='reset-button'
                                    onClick={() => {
                                        colors[0] = defaultHex
                                        this.setState({
                                            hex: defaultHex,
                                            colors: colors
                                        })
                                        localStorage.setItem('hex', defaultHex)
                                        localStorage.setItem('colors', defaultHex)
                                    }}
                                >
                                    Reset
                                </Dropdown.Item>
                                <Dropdown.Divider />
                                <DropdownButton
                                    id='circle-picker'
                                    title="Add Color"
                                    variant="Secondary"
                                    drop='end'
                                    onSelect
                                >
                                    <Dropdown.ItemText>
                                        <CirclePicker
                                            width={170}
                                            colors={['#990000', '#ff0000', '#ff6666', '#ffcccc',
                                                     '#996300', '#ffa500', '#ffc966', '#ffedcc',
                                                     '#999900', '#ffff00', '#ffff66', '#ffffcc',
                                                     '#009900', '#00ff00', '#66ff66', '#ccffcc',
                                                     '#00998b', '#00ffe7', '#80FFF3', '#ccfffa',
                                                     '#000099', '#0000ff', '#6666ff', '#ccccff',
                                                     '#580099', '#9300ff', '#be66ff', '#e9ccff',
                                                     '#871287', '#e01fe0', '#ed78ed', '#f9d2f9',]}
                                            onChangeComplete={(color) => {
                                                colors.push(color.hex)
                                                this.setState({
                                                    colors: colors
                                                })
                                                localStorage.setItem('colors', colors)
                                            }}
                                        />
                                    </Dropdown.ItemText>
                                    <Dropdown.Item
                                        className='reset-button'
                                        onClick={() => {
                                            let colors = [hex]
                                            this.setState({
                                                colors: colors
                                            })
                                            localStorage.setItem('colors', colors.toString())
                                        }}
                                    >
                                        Reset
                                    </Dropdown.Item>
                                </DropdownButton>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Col>
                    <Col>
                        <DropdownButton
                            title="Font"
                            variant="secondary"
                            id="fontDropdown"
                            onSelect={(size) => {
                                this.setState({
                                    fontSize: size
                                })
                                localStorage.setItem('fontSize', size)
                            }}
                        >
                            <Dropdown.Item eventKey="12">12</Dropdown.Item>
                            <Dropdown.Item eventKey="16">16</Dropdown.Item>
                            <Dropdown.Item eventKey="20">20</Dropdown.Item>
                            <Dropdown.Item eventKey="24">24</Dropdown.Item>
                            <Dropdown.Item eventKey="28">28</Dropdown.Item>
                            <Dropdown.Item eventKey="32">32</Dropdown.Item>
                            <Dropdown.Item eventKey="32">36</Dropdown.Item>
                        </DropdownButton>
                    </Col>
                </Row>
                <Bar
                    className="padding-left-right"
                    data={
                        {
                            labels: Object.keys(this.props.data),
                            datasets: [{
                                label: 'Smell Count',
                                backgroundColor: colors,
                                data: Object.values(this.props.data),
                            }]
                        }
                    }
                    options={options}
                />
            </>
        )
    }
}
