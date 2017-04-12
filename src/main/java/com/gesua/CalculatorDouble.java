package com.gesua;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Chirp on 12.03.2017.
 */
public class CalculatorDouble {

    public static final double TWO = 2;
    public static final double PI = Math.PI;
    public static final double FOUR = 4;
    public static final double THOUSAND = 1000;
    public static final double G = 0.277777778;

    static class Report {
        final double Dvn = 0.051;
        final double dnar = 0.008;
        final double Ltrub = 2;
        final double lyamdaStali = 16.2;
        final double tolshinaStenki = 0.001;
        final double teploprovodimistStali = 16.2;
        final double teploprovodimostNakipi = 0.22;
        final double po = 977.8;//Table
        final double cp = 4.178;//Table
        final double KoefRiflen = 1;
        final double bNaYjim = 0.0001;
        final double bZagr = 0.000051;

        //        double PrinimayemayaTemperaturaStenki = 89.5;
        //        Par
        final double Pnar = 0.1;
        final double popar = 0.658646;
        final double r = 2250.2;
        final double NuPar = 0.0000187033;
        final double Prpar = 1.08266;
        final double lamdaKond = 0.683532;
        final double poKondensata = 956.4316;
        final double MuPar = 0.0001210034;


        final double MuKond = 0.00276249;
        final double dvn;
        final double dRaschetTeploobTrubki;
        final double fvn;
        final double fnar;
        final double fnarTrubok;
        final double Fvn;
        final double fvnNTrubok;
        final double v1trubka;


        final double vNtrubok;
        final double dTb;
        final double dTm;
        final double dTser;

        final double T2ser;
        final double nyuT2ser;
        final double pr;

        final double lamda;
        final double Re;

        final double reRuflen;
        final double nu;
        final double nuRuflen;
        final double alpha;

        final double alphaRuflen;
        final double alphaGorizontSkorostnaya;
        final double kPoRovnoy;
        final double t2sred;
        final double q;
        final double qPo45min;
        final double k;
        final double F;


        final double Fpo45min;
        //        Fvodu
        final double Fvodu;
        final double Fvn_Fvodu;
        final double Dekv;
        final double dekv;
        final double fvnVodu;


        final double dekvVnTrubok;
        //        Par
        final double dPar;

        final double v;

        Report(final double nTrubok,
               final double t2sht,
               final double T1par,
               final double T2vuh) {
            fnar = fnar(dnar);
            Fvn = Fvn(Dvn);
            Fvn_Fvodu = Fvn_Fvodu(Fvn, fnar, nTrubok);
            Dekv = Dekv(Fvn_Fvodu);
            qPo45min = qPo45min(cp, T2vuh, t2sht);
            alphaGorizontSkorostnaya = alphaGorizontSkorostnaya(T1par, dnar);
            q = q(G, cp, T2vuh, t2sht);
            dvn = dvn(dnar, tolshinaStenki, bZagr, bNaYjim);
            fvn = fvn(dvn);
            dRaschetTeploobTrubki = dRaschetTeploobTrubki(dvn, dnar);
            fvnVodu = fvnVodu(dvn, nTrubok);
            dekvVnTrubok = dekvVnTrubok(fvnVodu);
            Fvodu = Fvodu(fnar, nTrubok);
            dekv = dekv(Fvodu);
            fnarTrubok = fnarTrubok(fnar, nTrubok);
            fvnNTrubok = fvnNTrubok(fvn, nTrubok);
            v1trubka = v1trubka(G, fvn, po);
            vNtrubok = vNtrubok(v1trubka, nTrubok);
            dTb = dTb(T1par, t2sht);
            dTm = dTm(T1par, T2vuh);
            dTser = dTser(dTb, dTm);
            T2ser = T2ser(T1par, dTser);
            nyuT2ser = nyuT2ser(T2ser);
            pr = pr(T2ser);
            lamda = lamda(T2ser);
            Re = Re(vNtrubok, dvn, nyuT2ser);
            reRuflen = reRuflen(Re, KoefRiflen);
            nu = nu(Re, pr);
            nuRuflen = nuRuflen(reRuflen, pr);
            alpha = alpha(nu, lamda, dvn);
            alphaRuflen = alphaRuflen(nuRuflen, lamda, dvn);
            kPoRovnoy = kPoRovnoy(alpha, tolshinaStenki, lyamdaStali, bZagr, alphaGorizontSkorostnaya);
            k = k(alphaRuflen, tolshinaStenki, bZagr, alphaGorizontSkorostnaya);
            Fpo45min = fPo45min(qPo45min, dTser, k);
            t2sred = t2sred(T2vuh, t2sht);
            F = F(q, dTser, k);
            dPar = dPar(q, r);
            v = v(dPar, popar, Fvn_Fvodu);
        }


        //Расчеты нормализации
        //Для nTrubok
        private double normNtrub(double F, double Ltrub, double dRaschetTeploobTrubki) {
            return F / PI / dRaschetTeploobTrubki / Ltrub;
        }

        private double ploshadEkvivalentnoyPoverhnosti(double dRaschetTeploobTrubki, double Ltrub, double nTrubok) {
            return PI * dRaschetTeploobTrubki * Ltrub * nTrubok * 0.96;
        }

        private double tStenki(double T1par, double q, double alphaGorizontSkorostnaya) {
            return (T1par - 5) - q / alphaGorizontSkorostnaya;
        }

        private double qNorm(double k, double dTser) {
            return k * dTser;
        }

        //Общие+вода
        private double dvn(double dnar, double tolshinaStenki, double bZagr, double bNaYjim) {
            return dnar - (tolshinaStenki * TWO) - (bZagr * TWO) - (bNaYjim * TWO);
        }

        private double dRaschetTeploobTrubki(double dvn, double dnar) {
            return (dvn + dnar) / 2;
        }

        private double fvn(double dvn) {
            return PI * dvn * dvn / FOUR;
        }

        private double fnar(double dnar) {
            return PI * dnar * dnar / FOUR;
        }

        private double fnarTrubok(double fnar, double nTrubok) {
            return fnar * nTrubok;
        }

        private double Fvn(double Dvn) {
            return Dvn * Dvn * PI / FOUR;
        }

        private double fvnNTrubok(double fvn, double nTrubok) {
            return fvn * nTrubok;
        }


        private double nyuT2ser(double T2ser) {
            return 0.000000556 + (0.000000478 - 0.000000556) / (60 - 50) * (T2ser - 50);
        }

        private double pr(double T2ser) {
            return 3.54 + (2.93 - 3.54) / (60 - 50) * (T2ser - 50);
        }

        private double lamda(double T2ser) {
            return Math.pow(10, -2) * (64.8 + (65.9 - 64.8) / (60 - 50) * (T2ser - 50));
        }

        private double v1trubka(double G, double fvn, double po) {
            return G / fvn / po;
        }

        private double vNtrubok(double v1trubka, double nTrubok) {
            return v1trubka / nTrubok;
        }

        private double Re(double vNtrubok, double dvn, double Nyu) {
            return vNtrubok * dvn / Nyu;
        }

        private double reRuflen(double Re, double koefRuglen) {
            return Re * koefRuglen;
        }

        private double nu(double Re, double pr) {
            return 0.021 * Math.pow(Re, 0.8) * Math.pow(pr, 0.43);
        }

        private double nuRuflen(double reRuflen, double pr) {
            return 0.021 * Math.pow(reRuflen, 0.8) * Math.pow(pr, 0.43);
        }

        private double alpha(double nu, double lamda, double dvn) {
            return nu * lamda / dvn;
        }

        private double alphaRuflen(double nuRuflen, double lamda, double dvn) {
            return nuRuflen * lamda / dvn;
        }

        private double kPoRovnoy(double alpha, double tolshinaStenki, double lyamdaStali, double bZagr, double alphaGorizontSkorostnaya) {
            return 1 / (1 / alpha + tolshinaStenki / lyamdaStali + bZagr / 1.16 + 1 / alphaGorizontSkorostnaya);
        }

        private double alphaGorizontSkorostnaya(double T1par, double dnar) {
            return (4950 + 58.5 * 97.66 - (0.18 * Math.pow(97.66, 2))) / Math.pow(((T1par - 89.5) * 3 * dnar), 0.25);
        }

        private double t2sred(double T2vuh, double t2sht) {
            return (T2vuh + t2sht) / 2;
        }

        private double dTb(double T1par, double t2sht) {
            return T1par - t2sht;
        }

        private double dTm(double T1par, double T2vuh) {
            return T1par - T2vuh;
        }

        private double dTser(double dTb, double dTm) {
            return (dTb - dTm) / Math.log(dTb / dTm);
        }

        private double T2ser(double T1par, double dTser) {

            return T1par - dTser;
        }

        private double q(double G, double cp, double T2vuh, double t2sht) {
            return G * cp * (T2vuh - t2sht) * THOUSAND;
        }

        private double qPo45min(double cp, double T2vuh, double t2sht) {
            return 1000 * 60 / 45 * cp * (T2vuh - t2sht) / 3600 * THOUSAND;
        }

        private double k(double alphaRuflen, double tolshinaStenki, double bzagr, double alphaGorizontSkorostnaya) {
            return 1 / (1 / alphaRuflen + tolshinaStenki / 16.2 + bzagr / 1.16 + 1 / alphaGorizontSkorostnaya);
        }

        private double F(double q, double dTser, double k) {
            return q / dTser / k;
        }

        private double fPo45min(double qPo45min, double dTser, double k) {
            return qPo45min / dTser / k;
        }

        //need
        private double Fvodu(double fnar, double nTrubok) {

            return fnar * nTrubok;
        }

        private double Fvn_Fvodu(double Fvn, double fnar, double nTrubok) {
            return Fvn - fnar * nTrubok;
        }

        private double Dekv(double Fvn_Fvodu) {
            return Math.pow((4 * Fvn_Fvodu / PI), 0.5);
        }

        private double dekv(double Fvodu) {
            return Math.pow((4 * Fvodu / PI), 0.5);
        }

        private double fvnVodu(double dvn, double nTrubok) {
            return Math.pow(dvn, 2) * PI / 4 * nTrubok;
        }

        private double dekvVnTrubok(double fvn) {
            return Math.pow((fvn * 4 / PI), 0.5);
        }

//    Пар

        private double dPar(double Q, double r) {
            return Q / r / 1000;
        }

        private double v(double Dpar, double poPar, double Fvn_Fvodu) {
            return Dpar / poPar / Fvn_Fvodu;
        }

        private double RePar(double v, double Dekv, double NuPar) {
            return v * Dekv / NuPar;
        }

        private double nuPar(double Re, double PrPar) {
            return 0.021 * Math.pow(Re, 0.8) * Math.pow(PrPar, 0.43);
        }

        private double AlphaPar(double nuPar, double lamdaKond, double Dekv) {
            return nuPar * lamdaKond / Dekv;
        }

        private double A(double lamdaKond, double r, double poKond, double poPar, double NuPar) {
            return Math.pow((Math.pow(lamdaKond, 3) * 9.81 * r * 1000 * (poKond - poPar) / NuPar), 0.25);
        }

        private double AlphaGorKondNepodPar(double A, double dnar, double T1par, double Tstenki1) {
            return 0.728 * A / Math.pow(dnar * (T1par - Tstenki1), 0.25);
        }

        //    Сопротивление по воде
        private double reEkv(double vNtrubok, double dekvVnTrubok, double nu) {
            return vNtrubok * dekvVnTrubok / nu;
        }

        private double soprLamda(double dEkv, double reEkv) {
            return 0.11 * Math.pow((1 / dEkv + 68 / reEkv), 0.25);
        }

        private double dPot(double soprLamda, double Ltrub, double dekvVnTrubok, double vNtrubok, double po) {
            return (soprLamda * Ltrub / dekvVnTrubok * Math.pow(vNtrubok, 2) / 2 * po) / 1000;
        }

        private double ksiSujeniya(double fvnVodu, double sorpF) {
            return 0.5 * (1 - fvnVodu / sorpF);
        }

        private double dpSujeniya(double ksiSujeniya, double vNtrubok, double po) {
            return (ksiSujeniya * Math.pow(vNtrubok, 2) * po / 2) / 1000;
        }

        private double dpTotal(double dPot, double dpSujeniya) {
            return dPot + dpSujeniya;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }

    }


    public static void main(String[] args) {

        final double nTrubok = 9;
        final double t2sht = 10;// Т2вх
        final double T1par = 102.66;
        final double T2vuh = 60;

        Report report = new Report(nTrubok, t2sht, T1par, T2vuh);

        System.out.println(report);

    }
}