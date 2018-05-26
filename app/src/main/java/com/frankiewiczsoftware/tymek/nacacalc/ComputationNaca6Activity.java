package com.frankiewiczsoftware.tymek.nacacalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.CHORD;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.NO1;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.NO2;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.NO3;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.NO4;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca6Fragment.XOB;
import static java.lang.Math.sqrt;

public class ComputationNaca6Activity extends Activity {
    AdView adView;
    double naca1Value, naca2Value, naca3Value, naca4Value, xbValue, chordValue, maxArrowValue,
            maxArrowPlaceValue, profileSupportAccurateValue, profileSupportApproximateValue,
            skeletonSupportAccurateValue, arctgVarValue,
            arctgVarRadian, upperEdgeSupportXAccurateValue, upperEdgeSupportZAccurateValue,
            downEdgeSupportXAccurateValue, downEdgeSupportZAccurateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_naca6);

        MobileAds.initialize(this,"ca-app-pub-4960960624269878~4237480021");
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        naca1Value = extras.getDouble(NO1);
        naca2Value = extras.getDouble(NO2);
        naca3Value = extras.getDouble(NO3);
        naca4Value = extras.getDouble(NO4);

        xbValue = extras.getDouble(XOB);
        chordValue = extras.getDouble(CHORD);

        //referencje

        TextView maxArrow = (TextView) findViewById(R.id.max_arrow);
        TextView maxArrowPlace = (TextView) findViewById(R.id.max_arrow_place);

        TextView profileSupport = (TextView) findViewById(R.id.profile_support);
        TextView skeletonSupport = (TextView) findViewById(R.id.skeleton_support);

        TextView arctgValue = (TextView) findViewById(R.id.arcrg_value);
        TextView arctgRadians= (TextView) findViewById(R.id.arctg_radians);

        TextView upperEdgeSupportX = (TextView) findViewById(R.id.upper_edge_support_x);
        TextView upperEdgeSupportZ = (TextView) findViewById(R.id.upper_edge_support_z);
        TextView downEdgeSupportX = (TextView) findViewById(R.id.down_edge_support_x);
        TextView downEdgeSupportZ = (TextView) findViewById(R.id.down_edge_support_z);

        maxArrowValue = naca1Value/100;
        maxArrowPlaceValue = naca2Value/10;

        profileSupportAccurateValue = (naca3Value / 100) * (1.4845 * sqrt(xbValue)
                - 0.63 * xbValue - 1.758 * xbValue * xbValue + 1.4215 * xbValue * xbValue * xbValue
                - 0.5075 * xbValue * xbValue * xbValue * xbValue);
//        double profileSupportApproximateValue = round(profileSupportAccurateValue,4);
//        double profileSupportPercent = profileSupportAccurateValue  * 100.0;
//        double profileSupportDistance = profileSupportApproximateValue * chordValue;


        if(xbValue < maxArrowPlaceValue){
            skeletonSupportAccurateValue = maxArrowValue/(maxArrowPlaceValue*maxArrowPlaceValue)
                    * (2*maxArrowPlaceValue*xbValue-(xbValue*xbValue));
        } else {
            skeletonSupportAccurateValue = (maxArrowValue/((1-maxArrowPlaceValue) * (1-maxArrowPlaceValue))) *
                    ((1 - 2 * maxArrowPlaceValue) + 2 * maxArrowPlaceValue * xbValue - (xbValue) * xbValue);
        }

        skeletonSupportAccurateValue = Math.abs(skeletonSupportAccurateValue);

//        double skeletonSupportApproximateValue = round(skeletonSupportAccurateValue, 4);
//        double skeletonSupportPercent = skeletonSupportApproximateValue * 100;
//        double skeletonSupportDistance = skeletonSupportApproximateValue * chordValue;


        if(xbValue < maxArrowPlaceValue){
            arctgVarValue = ((2 * maxArrowValue)/(maxArrowPlaceValue * maxArrowPlaceValue)*
                    (maxArrowPlaceValue-xbValue));
        } else {
            arctgVarValue = (2 * maxArrowValue)/ ((1-maxArrowPlaceValue) * (1-maxArrowPlaceValue)) *
                    (maxArrowPlaceValue-xbValue);
        }

        arctgVarRadian = Math.atan(arctgVarValue);

        upperEdgeSupportXAccurateValue = xbValue - profileSupportApproximateValue * Math.sin(arctgVarValue);
//        double upperEdgeSupportXApproximateValue = round(upperEdgeSupportXAccurateValue, 2);
//        double upperEdgeSupprotXPercent = upperEdgeSupportXApproximateValue * 100;

        upperEdgeSupportZAccurateValue = skeletonSupportAccurateValue + profileSupportApproximateValue * Math.cos(arctgVarRadian);
//        double upperEdgeSupportZApproximateValue = round(upperEdgeSupportZAccurateValue, 4);
//        double upperEdgeSupprotZPercent = upperEdgeSupportZApproximateValue * 100;

        downEdgeSupportXAccurateValue = xbValue + profileSupportApproximateValue * Math.sin(arctgVarRadian);
//        double downEdgeSupportXApproximateValue = round(downEdgeSupportXAccurateValue, 4);
//        double downEdgeSupprotXPercent = downEdgeSupportXApproximateValue * 100;

        downEdgeSupportZAccurateValue = -skeletonSupportAccurateValue-profileSupportApproximateValue * Math.sin(arctgVarRadian);
//        double downEdgeSupportZApproximateValue = round(downEdgeSupportZAccurateValue, 4);
//        double downEdgeSupprotZPercent = downEdgeSupportZApproximateValue * 100;

        maxArrow.setText(String.valueOf(maxArrowValue));
        maxArrowPlace.setText(String.valueOf(maxArrowPlaceValue));

        profileSupport.setText(String.format("%.6f",profileSupportAccurateValue));
        skeletonSupport.setText(String.format("%.6f",skeletonSupportAccurateValue));

        arctgValue.setText(String.format("%.6f", arctgVarValue));
        arctgRadians.setText(String.format("%.6f", arctgVarRadian));

        upperEdgeSupportX.setText(String.format("%.6f",upperEdgeSupportXAccurateValue));
        upperEdgeSupportZ.setText(String.format("%.6f",upperEdgeSupportZAccurateValue));
        downEdgeSupportX.setText(String.format("%.6f",downEdgeSupportXAccurateValue));
        downEdgeSupportZ.setText(String.format("%.6f",downEdgeSupportZAccurateValue));


    }

//    public static double round(double value, int places) {
//        if (places < 0) throw new IllegalArgumentException();
//
//        long factor = (long) Math.pow(10, places);
//        value = value * factor;
//        long tmp = Math.round(value);
//        return (double) tmp / factor;
//    }
}
