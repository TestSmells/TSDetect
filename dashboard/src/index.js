import React from 'react'
import {createRoot} from "react-dom/client"
import './index.css'
import Dashboard from './Dashboard'
import { HashRouter } from 'react-router-dom'

import { Chart, registerables } from 'chart.js'
Chart.register(...registerables)

const root = createRoot(document.getElementById('root'))
root.render(
    <HashRouter basename="/">
      <Dashboard />
    </HashRouter>
)
