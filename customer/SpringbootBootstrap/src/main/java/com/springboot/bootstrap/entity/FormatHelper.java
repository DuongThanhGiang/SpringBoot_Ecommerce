package com.springboot.bootstrap.entity;

import java.text.DecimalFormat;

public class FormatHelper {
    public String formatCurrency(double value) {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(value);
    }
}
