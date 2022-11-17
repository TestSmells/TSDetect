import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Dashboard from './Dashboard';
import { HashRouter } from 'react-router-dom';

import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <HashRouter basename="/">
      <Dashboard />
    </HashRouter>
);
