package com.android.mobilemodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by thangtb on 08/01/2015.
 */
public class BrandActivity extends ActionBarActivity implements View.OnClickListener {

    ImageView imgHTC;
    ImageView imgAlcatel;
    ImageView imgHuawei;
    ImageView imgLenovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);


        getSupportActionBar().setTitle(getResources().getString(R.string.title_model_list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgHTC = (ImageView)findViewById(R.id.imageViewHTC);
        imgLenovo = (ImageView)findViewById(R.id.imageViewLenovo);
        imgAlcatel = (ImageView)findViewById(R.id.imageViewALCATEL);
        imgHuawei = (ImageView)findViewById(R.id.imageViewHuawei);

        imgHTC.setOnClickListener(this);
        imgLenovo.setOnClickListener(this);
        imgAlcatel.setOnClickListener(this);
        imgHuawei.setOnClickListener(this);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        int imgWidth = (screenWidth*50)/100;
        int imgHeight = (screenHeight*20)/100;

        imgHTC.getLayoutParams().width = imgWidth;
        imgLenovo.getLayoutParams().width = imgWidth;
        imgAlcatel.getLayoutParams().width = imgWidth;
        imgHuawei.getLayoutParams().width = imgWidth;

        imgHTC.getLayoutParams().height = imgHeight;
        imgLenovo.getLayoutParams().height = imgHeight;
        imgAlcatel.getLayoutParams().height = imgHeight;
        imgHuawei.getLayoutParams().height = imgHeight;

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        String branch = "";

        switch (id){
            case R.id.imageViewHTC:
                branch = "HTC";
                break;
            case R.id.imageViewALCATEL:
                branch = "Alcatel";
                break;
            case R.id.imageViewHuawei:
                branch = "Huawei";
                break;
            case R.id.imageViewLenovo:
                branch = "Lenovo";
                break;
        }


        Intent i = new Intent(getApplicationContext(), ModelListActivity.class);
        i.putExtra("branch",branch);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
