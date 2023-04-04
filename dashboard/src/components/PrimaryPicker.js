import Sketch from "@uiw/react-color-sketch";
import React from "react";
import config from "../../config.json";
import {Dropdown} from "react-bootstrap";

export default function PrimaryPicker({ hex, colors, changePrimaryColor }) {
    return (
        <Dropdown.ItemText>
            <Sketch
                color={hex}
                presetColors={config.PRIMARY_COLORS}
                onChange={(color) => {
                    colors[0] = color.hex
                    changePrimaryColor(colors)
                }}
            />
        </Dropdown.ItemText>
    )
}