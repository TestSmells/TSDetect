import React, {Component} from "react"
import { Bar } from "react-chartjs-2"
import {Col, Dropdown, DropdownButton, Row} from "react-bootstrap"
import PrimaryPicker from "./PrimaryPicker";
import {ResetPrimary, ResetSecondary} from "./ResetButtons";
import SecondaryPicker from "./SecondaryPicker";
import {FontDropdown} from "./FontDropdown";

export default class SmellGraph extends Component {
    constructor(props) {
        super(props)
        this.state = {
            defaultHex: '#009688',
            hex: localStorage.getItem('hex') !== null ? localStorage.getItem('hex') : '#009688',
            fontSize: localStorage.getItem('fontSize') != null ? localStorage.getItem('fontSize') : 16,
            colors: localStorage.getItem('colors') !== null ? localStorage.getItem('colors').split(',') : (localStorage.getItem('hex') !== null ? localStorage.getItem('hex') : ['#009688'])
        }
        this.changePrimaryColor = this.changePrimaryColor.bind(this)
        this.changeSecondaryColor = this.changeSecondaryColor.bind(this)
        this.changeFontSize = this.changeFontSize.bind(this)
    }

    changePrimaryColor(colors) {
        this.setState({
            hex: colors[0],
            colors: colors
        })
        localStorage.setItem('hex', colors[0])
        localStorage.setItem('colors', colors)
    }

    changeSecondaryColor(colors) {
        this.setState({
            colors: colors
        })
        localStorage.setItem('colors', colors)
    }

    changeFontSize(size) {
        this.setState({
            fontSize: size
        })
        localStorage.setItem('fontSize', size)
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
                    display: false
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
                                    <PrimaryPicker hex={hex} colors={colors} changePrimaryColor={this.changePrimaryColor}/>
                                    <ResetPrimary defaultHex={defaultHex} colors={colors} changePrimaryColor={this.changePrimaryColor}/>
                                <Dropdown.Divider />

                                <DropdownButton
                                    id='circle-picker'
                                    title="Add Color"
                                    variant="Secondary"
                                    drop='end'
                                    onSelect
                                >
                                    <SecondaryPicker colors={colors} changeSecondaryColor={this.changeSecondaryColor}/>
                                    <ResetSecondary hex={hex} colors={colors} changeSecondaryColor={this.changeSecondaryColor} />
                                </DropdownButton>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Col>
                    <Col>
                        <FontDropdown changeFontSize={this.changeFontSize} />
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
