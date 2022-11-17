import React from "react"
import { Bar } from "react-chartjs-2";

function SmellGraph() {
    // TODO: Populate graph with real data collected from the database
    // will likely pass the data down as a prop to this component (map of tests and count totals)
    // and utlize the map in the label/dataset portion below
    return(
        <Bar 
            className="padding-left-right"
            data={
                {
                    labels: [
                        "Assertion Roulette",
                        "Slow Test",
                        "Lazy Test"
                    ],
                    datasets: [{
                        label: 'Smell Count',
                        backgroundColor: '#009688',
                        borderColor: 'rgb(255, 99, 132)',
                        data: [100, 70, 30],
                    }]
                }
            }
            options={
                {
                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            }
        />
    )
}

export default SmellGraph;
