package com.tricky_tweaks.library.model;

import com.tricky_tweaks.library.interfaces.ListHeader;

public class DateHeader implements ListHeader {

    private String dateText;

    public DateHeader() {}

    public DateHeader(String dateText) {
        this.dateText = dateText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    @Override
    public boolean isHeader() {
        return true;
    }
}
