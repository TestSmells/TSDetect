import React from "react"
import { Bar } from "react-chartjs-2";

export default function SmellGraph({ data }) {
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
        />
    )
}
