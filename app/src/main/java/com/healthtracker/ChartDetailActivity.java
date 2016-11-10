package com.healthtracker;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.BloodPresure;
import com.healthtracker.model.Glucose;
import com.healthtracker.model.Thyroid;
import com.healthtracker.model.Weight;
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

public class ChartDetailActivity extends ActionBarBaseActivitiy {

    int chartId = 0;
    DataBaseHelper dataBaseHelper;
    PreferenceHelper preferenceHelper;
    long maxDaysDiff, minDaysDiff;
    float yMin, yMax;
    String xMin, xMax;
    LinearLayout chartLyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail);
        dataBaseHelper = new DataBaseHelper(this);
        preferenceHelper = new PreferenceHelper(this);
        setTitle(getString(R.string.app_name) + dataBaseHelper.getUser(preferenceHelper.getInteger(AppConstant.USER_ID)).getUserName());

        chartLyt = (LinearLayout) findViewById(R.id.chart);
        if (getIntent().getExtras() != null) {
            chartId = getIntent().getIntExtra(AppConstant.CHART_ID, 1);
        }
        GraphicalView mChart = null;
        switch (chartId) {
            case AppConstant.CHART_WEIGHT:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    mChart = getWeightDataSet();
                    chartLyt.addView(mChart, 0);

                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }

                break;
            case AppConstant.CHART_B_P:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    mChart = getBPDataSet();
                    chartLyt.addView(mChart, 0);

                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }

                break;
            case AppConstant.CHART_CHOLESTROL:
                if (dataBaseHelper.getAllCholestrol(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    mChart = getCholestrolDataSet();
                    chartLyt.addView(mChart, 0);

                } else {

                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_GLUCOSE:
                if (dataBaseHelper.getAllGlucose(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    mChart = getGlucoseDataSet();
                    chartLyt.addView(mChart, 0);

                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_THYROID:
                if (dataBaseHelper.getAllThyroid(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    mChart = getThyroidDataSet();
                    chartLyt.addView(mChart, 0);

                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_WEIGHT_B_P:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    if (dataBaseHelper.getAllBP(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                        mChart = getBPWeightDataSet();
                        chartLyt.addView(mChart, 0);
                    } else {
                        Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_WEIGHT_CHOLESTROL:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    if (dataBaseHelper.getAllCholestrol(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                        mChart = getCholesterolWeightDataSet();
                        chartLyt.addView(mChart, 0);
                    } else {
                        Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_WEIGHT_GLUCOSE:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    if (dataBaseHelper.getAllGlucose(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                        mChart = getGlucoseWeightDataSet();
                        chartLyt.addView(mChart, 0);
                    } else {
                        Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
            case AppConstant.CHART_WEIGHT_THYROID:
                if (dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                    if (dataBaseHelper.getAllThyroid(preferenceHelper.getInteger(AppConstant.USER_ID)).size() > 0) {
                        mChart = getThyroidWeightDataSet();
                        chartLyt.addView(mChart, 0);
                    } else {
                        Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "No Data Exist", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private GraphicalView getThyroidWeightDataSet() {
        ArrayList<Thyroid> weightArrayList = dataBaseHelper.getAllThyroid(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Cholesterol");
        for (int j = 0; j < weightArrayList.size(); j++) {
            Thyroid weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getTshLevel());
        }
        TimeSeries weightSeries = getWeightSeries();

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);
        dataset.addSeries(weightSeries);
        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(2);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");
        return mChart;
    }

    private GraphicalView getGlucoseWeightDataSet() {
        ArrayList<Glucose> weightArrayList = dataBaseHelper.getAllGlucose(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Glucose");

        for (int j = 0; j < weightArrayList.size(); j++) {
            Glucose weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getGlucose());

        }
        TimeSeries weightSeries = getWeightSeries();

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);
        dataset.addSeries(weightSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(2);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");

        return mChart;
    }

    private GraphicalView getCholesterolWeightDataSet() {
        ArrayList<cholesterol> weightArrayList = dataBaseHelper.getAllCholestrol(preferenceHelper.getInteger(AppConstant.USER_ID));
        ArrayList<Weight> weight1ArrayList = dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID));

        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Cholesterol");


        for (int j = 0; j < weightArrayList.size(); j++) {
            cholesterol weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getHdl());
        }
        TimeSeries weightSeries = getWeightSeries();

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);
        dataset.addSeries(weightSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(2);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");
        return mChart;
    }

    private TimeSeries getWeightSeries() {
        ArrayList<Weight> weight1ArrayList = dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID));

        TimeSeries WeightSeries = new TimeSeries("weight");
        for (int j = 0; j < weight1ArrayList.size(); j++) {
            Weight weight = weight1ArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
            WeightSeries.add(Util.getDateFromString(date), weight.getWeight());
        }
        return WeightSeries;
    }

    private GraphicalView getBPWeightDataSet() {
        ArrayList<BloodPresure> weightArrayList = dataBaseHelper.getAllBP(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
//        yMin = weightArrayList.get(0).getSystolic();
//        yMax = weightArrayList.get(0).getSystolic();

        TimeSeries SystolicSeries = new TimeSeries("Systolic");
        TimeSeries DiastolicSeries = new TimeSeries("Diastolic");

        for (int j = 0; j < weightArrayList.size(); j++) {
            BloodPresure weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            SystolicSeries.add(Util.getDateFromString(date), weight.getSystolic());
            DiastolicSeries.add(Util.getDateFromString(date), weight.getDiastolic());

        }
        TimeSeries weightSeries = getWeightSeries();
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(SystolicSeries);
        dataset.addSeries(DiastolicSeries);
        dataset.addSeries(weightSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(3);
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);
        mRenderer.addSeriesRenderer(visitsRenderer[2]);

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
//        mRenderer.setYAxisMax(yMax);
//        mRenderer.setYAxisMin(yMin);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
//        mRenderer.setXAxisMax(Util.getDateFromString(xMax).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");

        return mChart;
    }

    private GraphicalView getThyroidDataSet() {

        ArrayList<Thyroid> weightArrayList = dataBaseHelper.getAllThyroid(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Cholesterol");
        for (int j = 0; j < weightArrayList.size(); j++) {
            Thyroid weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getTshLevel());
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(1);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");
        return mChart;
    }

    private GraphicalView getCholestrolDataSet() {
        ArrayList<cholesterol> weightArrayList = dataBaseHelper.getAllCholestrol(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Cholesterol");
        for (int j = 0; j < weightArrayList.size(); j++) {
            cholesterol weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getHdl());
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(1);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");
        return mChart;
    }

    private GraphicalView getGlucoseDataSet() {
        ArrayList<Glucose> weightArrayList = dataBaseHelper.getAllGlucose(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        TimeSeries GlucoseSeries = new TimeSeries("Glucose");

        for (int j = 0; j < weightArrayList.size(); j++) {
            Glucose weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            GlucoseSeries.add(Util.getDateFromString(date), weight.getGlucose());

        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(GlucoseSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(1);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");

        return mChart;

    }

    private GraphicalView getBPDataSet() {
        ArrayList<BloodPresure> weightArrayList = dataBaseHelper.getAllBP(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
//        yMin = weightArrayList.get(0).getSystolic();
//        yMax = weightArrayList.get(0).getSystolic();

        TimeSeries SystolicSeries = new TimeSeries("Systolic");
        TimeSeries DiastolicSeries = new TimeSeries("Diastolic");
        TimeSeries HeartRateSeries = new TimeSeries("HeartRate");

        for (int j = 0; j < weightArrayList.size(); j++) {
            BloodPresure weight = weightArrayList.get(j);
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
//            if (minDaysDiff > diff) {
//                minDaysDiff = diff;
//                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
//            }
            SystolicSeries.add(Util.getDateFromString(date), weight.getSystolic());
            DiastolicSeries.add(Util.getDateFromString(date), weight.getDiastolic());
            HeartRateSeries.add(Util.getDateFromString(date), weight.getHeartrate());
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(SystolicSeries);
        dataset.addSeries(DiastolicSeries);
        dataset.addSeries(HeartRateSeries);

        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(3);


        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);
        mRenderer.addSeriesRenderer(visitsRenderer[2]);

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
//        mRenderer.setYAxisMax(yMax);
//        mRenderer.setYAxisMin(yMin);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
//        mRenderer.setXAxisMax(Util.getDateFromString(xMax).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");

        return mChart;
    }

    private GraphicalView getWeightDataSet() {
        ArrayList<Weight> weightArrayList = dataBaseHelper.getAllWeight(preferenceHelper.getInteger(AppConstant.USER_ID));
        minDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        maxDaysDiff = Util.getDaysDifference(weightArrayList.get(0).getDate());
        xMax = weightArrayList.get(0).getDate();
        xMin = weightArrayList.get(0).getDate();
        yMin = weightArrayList.get(0).getWeight();
        yMax = weightArrayList.get(0).getWeight();

        TimeSeries WeightSeries = new TimeSeries("Weight");
        TimeSeries averageSeries = new TimeSeries("Average");
        float weightAvg = 0;
        for (int j = 0; j < weightArrayList.size(); j++) {
            Weight weight = weightArrayList.get(j);
            float w = weight.getWeight();
            weightAvg += w;
            String date = weight.getDate();
            long diff = Util.getDaysDifference(date);
            if (maxDaysDiff < diff) {
                maxDaysDiff = diff;
                xMin = date;
//                Toast.makeText(this, "min date" + xMin, Toast.LENGTH_LONG).show();
            }
            if (minDaysDiff > diff) {
                minDaysDiff = diff;
                xMax = date;
//                Toast.makeText(this, "max date" + xMax, Toast.LENGTH_LONG).show();
            }
            if (yMin > w) {
                yMin = w;
            }
            if (yMax < w) {
                yMax = w;
            }
            WeightSeries.add(Util.getDateFromString(date), weight.getWeight());
        }
        weightAvg = weightAvg / weightArrayList.size();
        averageSeries.add(Util.getDateFromString(xMin), yMin);
        averageSeries.add(Util.getDateFromString(xMin), weightAvg);
        averageSeries.add(Util.getDateFromString(xMax), yMax / 2);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(WeightSeries);
        dataset.addSeries(averageSeries);
        XYSeriesRenderer[] visitsRenderer = Util.getSeriesRenderer(2);


        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(visitsRenderer[0]);
        mRenderer.addSeriesRenderer(visitsRenderer[1]);

        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setPanEnabled(true, true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setYAxisMax(yMax);
        mRenderer.setYAxisMin(yMin);
        mRenderer.setXAxisMin(Util.getDateFromString(xMin).getTime());
        mRenderer.setXAxisMax(Util.getDateFromString(xMax).getTime());
        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setInScroll(true);
        final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, mRenderer, "dd-MMM-yyyy");

        return mChart;
    }
}
