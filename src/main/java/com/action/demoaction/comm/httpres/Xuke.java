package com.action.demoaction.comm.httpres;

import java.util.ArrayList;
import java.util.List;

public class Xuke {

    private String total;

    private List<XukeBody> rows = new ArrayList<>();

    public Xuke(String total, List<XukeBody> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Xuke() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<XukeBody> getRows() {
        return rows;
    }

    public void setRows(List<XukeBody> rows) {
        this.rows = rows;
    }
}
