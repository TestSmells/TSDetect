import React, {useState} from "react"
import { Bar } from "react-chartjs-2";
import Sketch from "@uiw/react-color-sketch";
import {Dropdown} from "react-bootstrap";

export default function SmellGraph({ data }) {
    const [hex, setHex] = useState('#009688')

    const fontSize = 16
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
                            }}
                        />
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
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
