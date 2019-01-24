package org.openokr.manage.vo;

import java.io.Serializable;

public class CheckinsExtVO extends CheckinsVO implements Serializable {

    private String resultName;

    public String getResultName() {
        return resultName;
    }

    public CheckinsExtVO setResultName(String resultName) {
        this.resultName = resultName;
        return this;
    }
}