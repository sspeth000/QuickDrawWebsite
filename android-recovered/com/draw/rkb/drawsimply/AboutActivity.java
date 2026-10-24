package com.draw.rkb.drawsimply;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/* Decompiled from the supplied classes.dex_Decompiler.com(1).zip. */
public class AboutActivity extends AppCompatActivity {
    Button buttonShare;
    Button buttonUpdate;
    ImageView imageView;
    TextView textBack;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
        this.textBack = (TextView) findViewById(R.id.tv_back);
        this.imageView = (ImageView) findViewById(R.id.imgViewBack);
        this.buttonUpdate = (Button) findViewById(R.id.btn_check_update);
        this.buttonShare = (Button) findViewById(R.id.btn_share_app);
        this.imageView.setOnClickListener(view -> onBackPressed());
        this.textBack.setOnClickListener(view -> onBackPressed());
        this.buttonUpdate.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.draw.rkb.drawsimply"));
            startActivity(intent);
        });
        this.buttonShare.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "Quick Draw");
            intent.putExtra("android.intent.extra.TEXT", "'Quick Draw', With this app you can easily create simple & fast drawings/paintings ..,   https://play.google.com/store/apps/details?id=com.draw.rkb.drawsimply");
            startActivity(Intent.createChooser(intent, "Share via"));
        });
    }
}