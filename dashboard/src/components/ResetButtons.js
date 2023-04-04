import React from "react";
import {Dropdown} from "react-bootstrap";

export function ResetPrimary({ defaultHex, colors, changePrimaryColor}) {
    return (
        <Dropdown.Item
            className='reset-button'
            onClick={() => {
                colors[0] = defaultHex
                changePrimaryColor(colors)
            }}
        >
            Reset
        </Dropdown.Item>
    )
}

export function ResetSecondary({ hex, changeSecondaryColor }) {
    return (
        <Dropdown.Item
            className='reset-button'
            onClick={() => {
                let colors = [hex]
                changeSecondaryColor(colors)
            }}
        >
            Reset
        </Dropdown.Item>
    )
}