package com.draw.rkb.drawsimply;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/* Decompiled from the supplied classes.dex_Decompiler.com(1).zip. */
public class HelpActivity extends AppCompatActivity {
    Button btnSettings;
    ImageView imageViewBack;
    TextView textViewBack;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_help);
        this.imageViewBack = (ImageView) findViewById(R.id.imgViewBackHelp);
        this.textViewBack = (TextView) findViewById(R.id.tv_back_help);
        this.btnSettings = (Button) findViewById(R.id.btn_settings);
        this.imageViewBack.setOnClickListener(view -> onBackPressed());
        this.textViewBack.setOnClickListener(view -> onBackPressed());
        this.btnSettings.setOnClickListener(view -> { });
    }
}