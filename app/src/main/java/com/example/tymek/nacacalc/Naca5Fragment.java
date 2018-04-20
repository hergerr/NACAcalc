package com.example.tymek.nacacalc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Tymek on 2018-04-19.
 */

public class Naca5Fragment extends Fragment{
    private Button buttonNext, buttonClean;
    private static  final String TAB = "Naca5Fragment";
    public static String NO1 = "1";
    public static String NO2 = "2";
    public static String NO3 = "3";
    public static String NO4 = "4";
    public static String XOB = "5";
    public static String CHOORD = "6";
    public static String FO = "7";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.naca5_fragment,container,false);
        buttonNext = (Button) view.findViewById(R.id.next_button1);
        buttonClean = (Button) view.findViewById(R.id.clean_button1);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    EditText naca1 = (EditText) getView().findViewById(R.id.naca_edit_1);
                    EditText naca2 = (EditText) getView().findViewById(R.id.naca_edit_2);
                    EditText naca3 = (EditText) getView().findViewById(R.id.naca_edit_3);
                    EditText naca4 = (EditText) getView().findViewById(R.id.naca_edit_4);
                    EditText xb = (EditText) getView().findViewById(R.id.xb);
                    EditText chord = (EditText) getView().findViewById(R.id.chord);
                    EditText f = (EditText) getView().findViewById(R.id.f);

                    double naca1Value = Double.parseDouble(naca1.getText().toString());
                    double naca2Value = Double.parseDouble(naca2.getText().toString());
                    double naca3Value = Double.parseDouble(naca3.getText().toString());
                    double naca4Value = Double.parseDouble(naca4.getText().toString());

                    double xbValue = Double.parseDouble(xb.getText().toString());
                    double chordValue = Double.parseDouble(chord.getText().toString());
                    double fValue = Double.parseDouble(f.getText().toString());

                    Intent intent = new Intent(getActivity(), ComputationNaca5Activity.class);
                    Bundle extras = new Bundle();
                    extras.putDouble(NO1, naca1Value);
                    extras.putDouble(NO2, naca2Value);
                    extras.putDouble(NO3, naca3Value);
                    extras.putDouble(NO4, naca4Value);
                    extras.putDouble(XOB, xbValue);
                    extras.putDouble(CHOORD, chordValue);
                    extras.putDouble(FO, fValue);

                    intent.putExtras(extras);
                    startActivity(intent);
                } catch (Exception e){

                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Wystapil blad");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            }
        });

        buttonClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText naca1 = (EditText) getView().findViewById(R.id.naca_edit_1);
                EditText naca2 = (EditText) getView().findViewById(R.id.naca_edit_2);
                EditText naca3 = (EditText) getView().findViewById(R.id.naca_edit_3);
                EditText naca4 = (EditText) getView().findViewById(R.id.naca_edit_4);
                EditText xb = (EditText) getView().findViewById(R.id.xb);
                EditText chord = (EditText) getView().findViewById(R.id.chord);
                EditText f = (EditText) getView().findViewById(R.id.f);

                naca1.setText("");
                naca2.setText("");
                naca3.setText("");
                naca4.setText("");
                xb.setText("");
                chord.setText("");
                f.setText("");
            }
        });

        return  view;
    }

}
