package com.example.tymek.nacacalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.tymek.nacacalc.Naca5Fragment.CHOORD;
import static com.example.tymek.nacacalc.Naca5Fragment.FO;
import static com.example.tymek.nacacalc.Naca5Fragment.NO1;
import static com.example.tymek.nacacalc.Naca5Fragment.NO2;
import static com.example.tymek.nacacalc.Naca5Fragment.NO3;
import static com.example.tymek.nacacalc.Naca5Fragment.NO4;
import static com.example.tymek.nacacalc.Naca5Fragment.XOB;
import static java.lang.Math.sqrt;

public class ComputationNaca5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_naca5);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        double naca1Value = extras.getDouble(NO1);
        double naca2Value = extras.getDouble(NO2);
        double naca3Value = extras.getDouble(NO3);
        double naca4Value = extras.getDouble(NO4);

        double xbValue = extras.getDouble(XOB);
        double chordValue = extras.getDouble(CHOORD);
        double fValue = extras.getDouble(FO);

        //referencje

        TextView projectOfSupportForce = (TextView) findViewById(R.id.project_of_support_force);
        TextView maxArrowPlace = (TextView) findViewById(R.id.max_arrow_place);

        TextView profileSupport = (TextView) findViewById(R.id.profile_support);
        TextView skeletonSupport = (TextView) findViewById(R.id.skeleton_support);

        TextView arctgValue = (TextView) findViewById(R.id.arcrg_value);
        TextView arctgRadians= (TextView) findViewById(R.id.arctg_radians);

        TextView m = (TextView) findViewById(R.id.m);
        TextView k1 = (TextView) findViewById(R.id.k1);

        TextView upperEdgeSupportX = (TextView) findViewById(R.id.upper_edge_support_x);
        TextView upperEdgeSupportZ = (TextView) findViewById(R.id.upper_edge_support_z);
        TextView downEdgeSupportX = (TextView) findViewById(R.id.down_edge_support_x);
        TextView downEdgeSupportZ = (TextView) findViewById(R.id.down_edge_support_z);

        double projectOfSupportForceValue = naca1Value * 3 / 20;
        double maxArrowPlaceValue = naca2Value / 20;

        double supportProfileValue = (naca4Value/100) *(1.4845 * sqrt(xbValue) - 0.63 * xbValue
                -1.758 * xbValue * xbValue + 1.4215 * xbValue * xbValue * xbValue
                -0.5075 * xbValue * xbValue * xbValue * xbValue);

        double mValue = (3 * fValue - 7 * fValue * fValue + 8 * fValue * fValue *fValue
                - 4 * fValue * fValue *fValue * fValue)/(sqrt(fValue * (1 - fValue))) -
                (3*(1 - 2 * fValue) * ((Math.PI / 2) - Math.asin(1 - 2 * fValue)))/2;

        double k1Value = (6 * projectOfSupportForceValue) / mValue;


        double skeletonSupportValue;

        if(xbValue > maxArrowPlaceValue){
            skeletonSupportValue = (k1Value / 6)*(fValue * fValue * fValue) * (1 - xbValue);
        } else {
            skeletonSupportValue = (k1Value / 6) * (xbValue * xbValue * xbValue - 3 * fValue * xbValue
                    * xbValue + fValue * fValue*(3-fValue)*xbValue);
        }

        double arctgVarValue;

        if(xbValue > maxArrowPlaceValue){
            arctgVarValue = -(k1Value/6) * fValue * fValue *fValue;
        } else {
            arctgVarValue = (k1Value/6)*(3 * xbValue * xbValue - 6 * fValue * xbValue +
                    fValue * fValue * (3 - fValue));
        }

        double arctgVarRadians = Math.atan(arctgVarValue);

        double xgeValue = xbValue - supportProfileValue * Math.sin(arctgVarRadians);
        double zgeValue = skeletonSupportValue + supportProfileValue * Math.cos(arctgVarRadians);
        double xdeValue = xbValue + supportProfileValue * Math.sin(arctgVarRadians);
        double zdeValue = skeletonSupportValue - supportProfileValue * Math.cos(arctgVarRadians);



        projectOfSupportForce.setText(String.format("%.9f",projectOfSupportForceValue));
        maxArrowPlace.setText(String.valueOf(String.format("%.9f",maxArrowPlaceValue)));

        profileSupport.setText(String.format("%.9f",supportProfileValue));
        skeletonSupport.setText(String.format("%.9f",skeletonSupportValue));

        m.setText(String.format("%.9f", mValue));
        k1.setText(String.format("%.9f", k1Value));

        arctgValue.setText(String.format("%.9f", arctgVarValue));
        arctgRadians.setText(String.format("%.9f", arctgVarRadians));

        upperEdgeSupportX.setText(String.format("%.9f",xgeValue));
        upperEdgeSupportZ.setText(String.format("%.9f",zgeValue));
        downEdgeSupportX.setText(String.format("%.9f",xdeValue));
        downEdgeSupportZ.setText(String.format("%.9f",zdeValue));
    }


}
