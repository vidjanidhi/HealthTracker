package com.healthtracker.util;

import java.text.DecimalFormat;

/**
 * Created by local nidhi on 17-05-2016.
 */
public class UnitHelper {


    public static double format(double value) {
        if (value != 0) {
            DecimalFormat df = new DecimalFormat("###.###");
            return Double.valueOf(df.format(value));
        } else {
            return -1;
        }
    }

    public static double mgToMmol(float mg) {
        return format(mg * 0.0555);

    }

    public static double mmolToMg(float mmol) {
        return format(mmol * 18.0182);
    }

    public static double lbsToKgConverter(double lb) {
        return format(lb * 0.45359237);
    }


    public static double kgToLbsConverter(double kg) {
        return format(kg * 2.20462262);
    }


    public static double cmToInchConverter(double cm) {
        return format(cm * 0.393701);
    }


    public static double inchToCmConverter(double inch) {
        return format(inch * 2.54);
    }


    public static double getBMIKg(double height, double weight) {
        double meters = height / 100;
        return format(weight / Math.pow(meters, 2));
    }


    public static double getBMILb(double height, double weight) {
        int inch = (int) (height * 12);
        return format((weight * 703) / Math.pow(inch, 2));
    }


    public static String getBMIClassification(double bmi) {

        if (bmi <= 0) return "unknown";
        String classification;

        if (bmi < 18.5) {
            classification = "underweight";
        } else if (bmi < 25) {
            classification = "normal";
        } else if (bmi < 30) {
            classification = "overweight";
        } else {
            classification = "obese";
        }
        return classification;
    }

}
