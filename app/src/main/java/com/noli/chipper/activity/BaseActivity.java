package com.noli.chipper.activity;

import android.support.v7.app.AppCompatActivity;

import com.noli.chipper.util.SPEditor;

public class BaseActivity extends AppCompatActivity {

    private final SPEditor mSpEditor = new SPEditor(this);

    public SPEditor getSPEditor() {
        return mSpEditor;
    }

}
