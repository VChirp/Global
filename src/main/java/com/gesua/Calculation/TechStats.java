package com.gesua.Calculation;

import lombok.Data;

import static com.gesua.Calculation.StatsParser.TECH_FILE_COLUMNS;

@Data
public class TechStats {
    double vnPlTrub;
    double narPlTrub;
    String name;

    TechStats(double vnPlTrub, double narPlTrub, String name) {
        this.vnPlTrub = vnPlTrub;
        this.narPlTrub = narPlTrub;
        this.name = name;
    }

    static TechStats fromArray(String[] values) {
        if (values.length != TECH_FILE_COLUMNS) {
            throw new IllegalArgumentException("Invalid columns count");
        }
        return new TechStats(Double.parseDouble(values[0]),Double.parseDouble(values[1]), values[2]);
    }

}
