import React from "react"
import { Bar } from "react-chartjs-2";

export default function SmellGraph({ data }) {
    const options = {
        scales: {
            x: {
                ticks: {
                    font: {
                        size: 16
                    }
                }
            },
            y: {
                ticks: {
                    font: {
                        size: 16
                    }
                }
            }
        },
        plugins: {
            legend: {
                labels: {
                    font: {
                        size: 16
                    }
                }
            },
            tooltip: {
                titleFont: {
                    size: 16
                },
                bodyFont: {
                    size: 16
                }
            }
        }
    }
    return(
        <Bar
            className="padding-left-right"
            data={
                {
                    labels: Object.keys(data),
                    datasets: [{
                        label: 'Smell Count',
                        backgroundColor: '#009688',
                        borderColor: 'rgb(255, 99, 132)',
                        data: Object.values(data),
                    }]
                }
            }
            options={options}
        />
    )
}
