import React from "react";
import config from "../../config.json";
import {Dropdown} from "react-bootstrap";
import {CirclePicker} from "react-color";

export default function SecondaryPicker({ colors, changeSecondaryColor }) {
    return (
        <Dropdown.ItemText>
            <CirclePicker
                width={170}
                colors={config.SECONDARY_COLORS}
                onChangeComplete={(color) => {
                    colors.push(color.hex)
                    changeSecondaryColor(colors)
                }}
            />
        </Dropdown.ItemText>
    )
}