package com.flatradiogroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.library.FlatRadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FlatRadioButton flatRadioButton = (FlatRadioButton) findViewById(R.id.flat_radio_button_first);
//        flatRadioButton.setFlatText("Test1");
//        final Button button = (Button) flatRadioButton.findViewById(R.id.flat_compound_button_image);
//        button.setFlatText("Hello");
    }
}
