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

public class Naca4Fragment extends Fragment{

    private Button buttonNext, buttonClean;
    private static  final String TAB = "Naca4Fragment";
    public static String N1 = "1";
    public static String N2 = "2";
    public static String N3 = "3";
    public static String XB = "4";
    public static String CHORD = "5";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.naca4_fragment,container,false);
        buttonNext = (Button) view.findViewById(R.id.next_button);
        buttonClean = (Button) view.findViewById(R.id.clean_button);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {

                    EditText naca1 = (EditText) getView().findViewById(R.id.naca_edit_1);
                    EditText naca2 = (EditText) getView().findViewById(R.id.naca_edit_2);
                    EditText naca3 = (EditText) getView().findViewById(R.id.naca_edit_3);
                    EditText xb = (EditText) getView().findViewById(R.id.xb);
                    EditText chord = (EditText) getView().findViewById(R.id.chord);

                    double naca1Value = Double.parseDouble(naca1.getText().toString());
                    double naca2Value = Double.parseDouble(naca2.getText().toString());
                    double naca3Value = Double.parseDouble(naca3.getText().toString());

                    double xbValue = Double.parseDouble(xb.getText().toString());
                    double chordValue = Double.parseDouble(chord.getText().toString());

                    Intent intent = new Intent(getActivity(), ComputationNaca4Activity.class);
                    Bundle extras = new Bundle();
                    extras.putDouble(N1, naca1Value);
                    extras.putDouble(N2, naca2Value);
                    extras.putDouble(N3, naca3Value);
                    extras.putDouble(XB, xbValue);
                    extras.putDouble(CHORD, chordValue);

                    intent.putExtras(extras);
                    startActivity(intent);
                } catch (Exception e){

                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("ERROR");
                    alertDialog.setMessage("WYSTAPIL BLAD");
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

        buttonClean.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText naca1 = (EditText) getView().findViewById(R.id.naca_edit_1);
                EditText naca2 = (EditText) getView().findViewById(R.id.naca_edit_2);
                EditText naca3 = (EditText) getView().findViewById(R.id.naca_edit_3);
                EditText xb = (EditText) getView().findViewById(R.id.xb);
                EditText chord = (EditText) getView().findViewById(R.id.chord);

                naca1.setText("");
                naca2.setText("");
                naca3.setText("");
                xb.setText("");
                chord.setText("");

            }
        });


        return view;
    }

}
