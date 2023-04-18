
import React from "react"
import { Routes, Route } from 'react-router-dom'
import { Navbar, Nav, Container } from "react-bootstrap"
import Home from './pages/Home'

function Dashboard() {
    return(
        <div className="Dashboard">
            <Navbar className="dashboard-navbar">
                <Container>
                    <Nav className="justify-content-center">
                        <Nav.Link to="/dashboard">Home</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Container>
                <Routes>
                    <Route exact path="/dashboard" element={<Home />} />
                </Routes>
            </Container>
        </div>
    )
}

export default Dashboard
