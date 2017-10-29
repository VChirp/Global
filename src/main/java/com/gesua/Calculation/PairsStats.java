package com.gesua.Calculation;

import lombok.Data;

import java.util.List;

import static com.gesua.Calculation.StatsParser.PAIRS_FILE_COLUMNS;

@Data
public class PairsStats {
    double t;       // °C
    double p;       // бар
    double ro;      // кг/м3
    double h;       // кДж/кг
    double r;
    double cp;      // кДж/(кг·град)
    double lamda;   // 10^2 Вт/(м·град)
    double a;       // 10^8 м2/сек
    double mu;      // 10^6 Н·сек/м2
    double nu;      // ν·10^6,м 2/сек
    double pr;      // Pr

    PairsStats(double t, double p, double ro, double h, double r, double cp, double lamda, double a, double mu, double nu, double pr) {
        this.t = t;
        this.p = p;
        this.ro = ro;
        this.h = h;
        this.r = r;
        this.cp = cp;
        this.lamda = lamda;
        this.a = a;
        this.mu = mu;
        this.nu = nu;
        this.pr = pr;
    }


    static PairsStats fromList(List<Double> values) {
        if (values.size() != PAIRS_FILE_COLUMNS) {
            throw new IllegalArgumentException("Invalid columns count");
        }
        return new PairsStats(
                values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6),
                values.get(7), values.get(8), values.get(9), values.get(10));

    }
}
