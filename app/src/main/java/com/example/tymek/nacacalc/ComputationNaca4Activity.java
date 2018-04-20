package com.example.tymek.nacacalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tymek.nacacalc.R;

import static com.example.tymek.nacacalc.Naca4Fragment.CHORD;
import static com.example.tymek.nacacalc.Naca4Fragment.N1;
import static com.example.tymek.nacacalc.Naca4Fragment.N2;
import static com.example.tymek.nacacalc.Naca4Fragment.N3;
import static com.example.tymek.nacacalc.Naca4Fragment.XB;
import static java.lang.Math.sqrt;

public class ComputationNaca4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_naca4);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        double naca1Value = extras.getDouble(N1);
        double naca2Value = extras.getDouble(N2);
        double naca3Value = extras.getDouble(N3);

        double xbValue = extras.getDouble(XB);
        double chordValue = extras.getDouble(CHORD);

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

        double maxArrowValue = naca1Value/100;
        double maxArrowPlaceValue = naca2Value/10;

        double profileSupportAccurateValue = (naca3Value / 100) * (1.4845 * sqrt(xbValue)
                - 0.63 * xbValue - 1.758 * xbValue * xbValue + 1.4215 * xbValue * xbValue * xbValue
                - 0.5075 * xbValue * xbValue * xbValue * xbValue);
        double profileSupportApproximateValue = round(profileSupportAccurateValue,4);
//        double profileSupportPercent = profileSupportAccurateValue  * 100.0;
//        double profileSupportDistance = profileSupportApproximateValue * chordValue;

        double skeletonSupportAccurateValue;

        if(xbValue < maxArrowPlaceValue){
            skeletonSupportAccurateValue = maxArrowValue/(maxArrowPlaceValue*maxArrowPlaceValue)
                    * (2*maxArrowPlaceValue*xbValue-(xbValue*xbValue));
        } else {
            skeletonSupportAccurateValue = (maxArrowValue/((1-maxArrowPlaceValue) * (1-maxArrowPlaceValue))) *
                    ((1 - 2 * maxArrowPlaceValue) + 2 * maxArrowPlaceValue * xbValue - (xbValue) * xbValue);
        }

        skeletonSupportAccurateValue = Math.abs(skeletonSupportAccurateValue);

        double skeletonSupportApproximateValue = round(skeletonSupportAccurateValue, 4);
//        double skeletonSupportPercent = skeletonSupportApproximateValue * 100;
//        double skeletonSupportDistance = skeletonSupportApproximateValue * chordValue;

        double arctgVarValue;

        if(xbValue < maxArrowPlaceValue){
            arctgVarValue = ((2 * maxArrowValue)/(maxArrowPlaceValue * maxArrowPlaceValue)*
                    (maxArrowPlaceValue-xbValue));
        } else {
            arctgVarValue = (2 * maxArrowValue)/ ((1-maxArrowPlaceValue) * (1-maxArrowPlaceValue)) *
                    (maxArrowPlaceValue-xbValue);
        }

        double arctgVarRadian = Math.atan(arctgVarValue);

        double upperEdgeSupportXAccurateValue = xbValue - profileSupportApproximateValue * Math.sin(arctgVarValue);
//        double upperEdgeSupportXApproximateValue = round(upperEdgeSupportXAccurateValue, 2);
//        double upperEdgeSupprotXPercent = upperEdgeSupportXApproximateValue * 100;

        double upperEdgeSupportZAccurateValue = skeletonSupportAccurateValue + profileSupportApproximateValue * Math.cos(arctgVarRadian);
//        double upperEdgeSupportZApproximateValue = round(upperEdgeSupportZAccurateValue, 4);
//        double upperEdgeSupprotZPercent = upperEdgeSupportZApproximateValue * 100;

        double downEdgeSupportXAccurateValue = xbValue + profileSupportApproximateValue * Math.sin(arctgVarRadian);
//        double downEdgeSupportXApproximateValue = round(downEdgeSupportXAccurateValue, 4);
//        double downEdgeSupprotXPercent = downEdgeSupportXApproximateValue * 100;

        double downEdgeSupportZAccurateValue = -skeletonSupportAccurateValue-profileSupportApproximateValue * Math.sin(arctgVarRadian);
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
