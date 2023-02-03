package org.testsmells.server.controller.requests;

import javax.validation.constraints.NotNull;

public class ExampleRequest {

    @NotNull
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
