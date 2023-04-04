import React from "react";
import {Dropdown, DropdownButton} from "react-bootstrap";

export function FontDropdown({ changeFontSize }) {
    return (
        <DropdownButton
            title="Font"
            variant="secondary"
            id="fontDropdown"
            onSelect={(size) => {
                changeFontSize(size)
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
    )
}