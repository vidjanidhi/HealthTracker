package com.healthtracker.util;

/**
 * Created by local nidhi on 27-04-2016.
 */
public class AppConstant {

    public static final String FRAGMENT_ID = "fragment_id";
    public static final String POSITION = "position";
    public static final String IS_EDIT = "is_edit";
    public static final String TAB_ID = "tab_id";

    //fragments for add detail activity
    public static final int WEIGHT_FRAGMENT = 1;
    public static final int GLUCOSE_FRAGMENT = 2;
    public static final int B_P_FRAGMENT = 3;
    public static final int CHOLESTROL_FRAGMENT = 4;
    public static final int THYROID_FRAGMENT = 5;

    //fragments for add log activity
    public static final int MED_FRAGMENT = 1;
    public static final int ACTIVITY_FRAGMENT = 2;
    public static final int FOOD_FRAGMENT = 3;
    public static final int MIS_FRAGMENT = 4;


    public static final String USER_ID = "user_id";

    //units

    //glucose
    public static final String GLUCOSE_MMOL_PER_L = "mmol/L";
    public static final String GLUCOSE_MG_PER_DL = "mg/dL";
    public static final int GLUCOSE_SELECTED_UNIT_MMOL_PER_L = 0;
    public static final int GLUCOSE_SELECTED_UNIT_MG_PER_DL = 1;
    public static final String GLUCOSE_SELECTED = "glucose_selected_value";

    public static final String GLUCOSE_TEXT_MMOL_PER_L = "Glucose Units(mmol/L)";
    public static final String GLUCOSE_TEXT_MG_PER_DL = "Glucose Units(mg/dL)";

    //hba1c
    public static final String HBA1C_IFFC = "IFFC(mmol/mol)";
    public static final String HBA1C_DCCT = "DCCT(%)";
    public static final String HBA1C_SELECTED = "hba1c_selected_value";
    public static final int HBA1C_SELECTED_UNIT_DCCT = 0;
    public static final int HBA1C_SELECTED_UNIT_IFFC = 1;

    public static final String HBA1C_TEXT_IFFC = "HbA1c Units(mmol/mol)";
    public static final String HBA1C_TEXT_DCCT = "HbA1c Units(DCCT(%))";

    //weight
    public static final String WEIGHT_KG = "kilograms";
    public static final String WEIGHT_POUNDS = "Pounds(lbs)";
    public static final String WEIGHT_SELECTED = "weight_selected_value";
    public static final int WEIGHT_SELECTED_UNIT_KG = 0;
    public static final int WEIGHT_SELECTED_UNIT_POUNDS = 1;

    public static final String WEIGHT_TEXT_KG = "Weight Units(kg)";
    public static final String WEIGHT_TEXT_POUNDS = "Weight Units(lbs)";

    //length
    public static final String LENGTH_CM = "cm";
    public static final String LENGTH_INCH = "inch";
    public static final String LENGTH_TEXT_INCH = "Length Units(inch)";
    public static final String LENGTH_SELECTED = "length_selected_value";
    public static final int LENGTH_SELECTED_UNIT_CM = 0;
    public static final int LENGTH_SELECTED_UNIT_INCH = 1;

    public static final String LENGTH_TEXT_CM = "Length Units(cm)";


    //date format
    public static final String DATE_DMY = "day/month/year";
    public static final String DATE_MDY = "month/day/year";
    public static final String DATE_SELECTED = "date_selected_value";

    public static final int DATE_SELECTED_UNIT_DMY = 0;
    public static final int DATE_SELECTED_UNIT_MDY = 1;


}
