package com.frankiewiczsoftware.tymek.nacacalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.CHORD;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.FO;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.NO1;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.NO2;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.NO3;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.NO4;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5Fragment.XOB;
import static java.lang.Math.sqrt;

public class ComputationNaca5Activity extends AppCompatActivity {
    AdView adView;
    double naca1Value, naca2Value, naca3Value, naca4Value, xbValue, chordValue, fValue,
            maxArrowPlaceValue, projectOfSupportForceValue, supportProfileValue,
            mValue, k1Value, zdeValue, skeletonSupportValue, arctgVarRadians, xgeValue,
            zgeValue, xdeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_naca5);

        MobileAds.initialize(this, "ca-app-pub-4960960624269878~4237480021");
        adView = (AdView) findViewById(R.id.adView1);
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
        fValue = extras.getDouble(FO);

        //referencje

        TextView projectOfSupportForce = (TextView) findViewById(R.id.project_of_support_force);
        TextView maxArrowPlace = (TextView) findViewById(R.id.max_arrow_place);

        TextView profileSupport = (TextView) findViewById(R.id.profile_support);
        TextView skeletonSupport = (TextView) findViewById(R.id.skeleton_support);

        TextView arctgValue = (TextView) findViewById(R.id.arcrg_value);
        TextView arctgRadians = (TextView) findViewById(R.id.arctg_radians);

        TextView m = (TextView) findViewById(R.id.m);
        TextView k1 = (TextView) findViewById(R.id.k1);

        TextView upperEdgeSupportX = (TextView) findViewById(R.id.upper_edge_support_x);
        TextView upperEdgeSupportZ = (TextView) findViewById(R.id.upper_edge_support_z);
        TextView downEdgeSupportX = (TextView) findViewById(R.id.down_edge_support_x);
        TextView downEdgeSupportZ = (TextView) findViewById(R.id.down_edge_support_z);

        projectOfSupportForceValue = naca1Value * 3 / 20;
        maxArrowPlaceValue = naca2Value / 20;

        supportProfileValue = (naca4Value / 100) * (1.4845 * sqrt(xbValue) - 0.63 * xbValue
                - 1.758 * xbValue * xbValue + 1.4215 * xbValue * xbValue * xbValue
                - 0.5075 * xbValue * xbValue * xbValue * xbValue);

        mValue = (3 * fValue - 7 * fValue * fValue + 8 * fValue * fValue * fValue
                - 4 * fValue * fValue * fValue * fValue) / (sqrt(fValue * (1 - fValue))) -
                (3 * (1 - 2 * fValue) * ((Math.PI / 2) - Math.asin(1 - 2 * fValue))) / 2;

        k1Value = (6 * projectOfSupportForceValue) / mValue;

        if (xbValue > maxArrowPlaceValue) {
            skeletonSupportValue = (k1Value / 6) * (fValue * fValue * fValue) * (1 - xbValue);
        } else {
            skeletonSupportValue = (k1Value / 6) * (xbValue * xbValue * xbValue - 3 * fValue * xbValue
                    * xbValue + fValue * fValue * (3 - fValue) * xbValue);
        }

        double arctgVarValue;

        if (xbValue > maxArrowPlaceValue) {
            arctgVarValue = -(k1Value / 6) * fValue * fValue * fValue;
        } else {
            arctgVarValue = (k1Value / 6) * (3 * xbValue * xbValue - 6 * fValue * xbValue +
                    fValue * fValue * (3 - fValue));
        }

        arctgVarRadians = Math.atan(arctgVarValue);

        xgeValue = xbValue - supportProfileValue * Math.sin(arctgVarRadians);
        zgeValue = skeletonSupportValue + supportProfileValue * Math.cos(arctgVarRadians);
        xdeValue = xbValue + supportProfileValue * Math.sin(arctgVarRadians);
        zdeValue = skeletonSupportValue - supportProfileValue * Math.cos(arctgVarRadians);


        projectOfSupportForce.setText(String.format("%.9f", projectOfSupportForceValue));
        maxArrowPlace.setText(String.valueOf(String.format("%.9f", maxArrowPlaceValue)));

        profileSupport.setText(String.format("%.9f", supportProfileValue));
        skeletonSupport.setText(String.format("%.9f", skeletonSupportValue));

        m.setText(String.format("%.9f", mValue));
        k1.setText(String.format("%.9f", k1Value));

        arctgValue.setText(String.format("%.9f", arctgVarValue));
        arctgRadians.setText(String.format("%.9f", arctgVarRadians));

        upperEdgeSupportX.setText(String.format("%.9f", xgeValue));
        upperEdgeSupportZ.setText(String.format("%.9f", zgeValue));
        downEdgeSupportX.setText(String.format("%.9f", xdeValue));
        downEdgeSupportZ.setText(String.format("%.9f", zdeValue));
    }

}
