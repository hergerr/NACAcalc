package com.frankiewiczsoftware.tymek.nacacalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.CHORD;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.FO;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.NO1;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.NO2;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.NO3;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.NO4;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.XOB;
import static com.frankiewiczsoftware.tymek.nacacalc.Naca5v2Fragment.K1;
import static java.lang.Math.sqrt;

public class ComputationNaca5v2Activity extends Activity {
    AdView adView;
    double naca1Value, naca2Value, naca3Value, naca4Value, xbValue, chordValue, fValue,
            maxArrowPlaceValue, projectOfSupportForceValue, supportProfileValue, k1Value, zdeValue,
            skeletonSupportValue, arctgVarRadians, xgeValue,
            zgeValue, xdeValue, k2Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_naca5v2);

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
        k1Value = extras.getDouble(K1);

        //referencje

        TextView projectOfSupportForce = (TextView) findViewById(R.id.project_of_support_force);
        TextView maxArrowPlace = (TextView) findViewById(R.id.max_arrow_place);

        TextView profileSupport = (TextView) findViewById(R.id.profile_support);
        TextView skeletonSupport = (TextView) findViewById(R.id.skeleton_support);

        TextView arctgValue = (TextView) findViewById(R.id.arcrg_value);
        TextView arctgRadians = (TextView) findViewById(R.id.arctg_radians);

        TextView k2 = (TextView) findViewById(R.id.k2);

        TextView upperEdgeSupportX = (TextView) findViewById(R.id.upper_edge_support_x);
        TextView upperEdgeSupportZ = (TextView) findViewById(R.id.upper_edge_support_z);
        TextView downEdgeSupportX = (TextView) findViewById(R.id.down_edge_support_x);
        TextView downEdgeSupportZ = (TextView) findViewById(R.id.down_edge_support_z);

        projectOfSupportForceValue = naca1Value * 3 / 20;
        maxArrowPlaceValue = naca2Value / 20;

        supportProfileValue = (naca4Value / 100) * (1.4845 * sqrt(xbValue) - 0.63 * xbValue
                - 1.758 * xbValue * xbValue + 1.4215 * xbValue * xbValue * xbValue
                - 0.5075 * xbValue * xbValue * xbValue * xbValue);

        k2Value = (k1Value*(3*(fValue - projectOfSupportForceValue)*
                (fValue - projectOfSupportForceValue) - fValue * fValue * fValue))/
                ((1 - fValue)*(1 - fValue)*(1 - fValue));

        if (xbValue > maxArrowPlaceValue) {
            skeletonSupportValue = (k2Value / 6) * ((k2Value/k1Value) * (xbValue - fValue) *
                    (xbValue - fValue)  * (xbValue - fValue) - k2Value/k1Value*(1-fValue)*
                    (1-fValue) * (1-fValue) * xbValue - fValue * fValue * fValue * xbValue +
                    fValue * fValue * fValue);
        } else {
            skeletonSupportValue = (k1Value / 6) * ((xbValue - fValue)*(xbValue - fValue)
            *(xbValue - fValue) - k2Value/k1Value*(1 - fValue)*(1 - fValue)*(1 - fValue) *
            xbValue - fValue * fValue * fValue * xbValue + fValue * fValue * fValue);
        }

        double arctgVarValue;

        if (xbValue > maxArrowPlaceValue) {
            arctgVarValue = (k1Value / 6) *(3*(k2Value/k1Value)*(xbValue-fValue)*(xbValue-fValue) -
                    (k2Value/k1Value)*(1-fValue)*(1-fValue)*(1-fValue)-fValue*fValue*fValue);
        } else {
            arctgVarValue = (k1Value / 6) * (3 * (xbValue - fValue)*(xbValue - fValue) - k2Value/
                    k1Value*(1-fValue)*(1-fValue)*(1-fValue) - fValue*fValue*fValue);
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

        k2.setText(String.format("%.9f", k2Value));

        arctgValue.setText(String.format("%.9f", arctgVarValue));
        arctgRadians.setText(String.format("%.9f", arctgVarRadians));

        upperEdgeSupportX.setText(String.format("%.9f", xgeValue));
        upperEdgeSupportZ.setText(String.format("%.9f", zgeValue));
        downEdgeSupportX.setText(String.format("%.9f", xdeValue));
        downEdgeSupportZ.setText(String.format("%.9f", zdeValue));
    }

}
