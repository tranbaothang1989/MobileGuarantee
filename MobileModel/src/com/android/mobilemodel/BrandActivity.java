package com.android.mobilemodel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

        imgHTC = (ImageView)findViewById(R.id.imageViewHTC);
    }

    @Override
    public void onClick(View v) {

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
