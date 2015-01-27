package com.android.mobilemodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.csvreader.CsvReader;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	public DatabaseHelper databaseHelper;
	Button btnMyModel;
	Button btnSearchModel;

    LinearLayout llMyModel;
    LinearLayout llSearchModel;
    Model model;
    String sModel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int screenHeight = metrics.heightPixels;
		int screenWidth = metrics.widthPixels;

        llMyModel = (LinearLayout)findViewById(R.id.ll_my_model);
        llSearchModel = (LinearLayout)findViewById(R.id.ll_search_model);

		btnMyModel = (Button)findViewById(R.id.btn_my_model);
		btnSearchModel = (Button)findViewById(R.id.btn_search_model);
        llMyModel.setOnClickListener(this);
        llSearchModel.setOnClickListener(this);
		//int btnWidth = (screenWidth*70)/100;
		int btnHeight = (screenHeight*20)/100;

		//btnMyModel.setWidth(btnWidth);
		btnMyModel.setHeight(btnHeight);
        sModel        = Build.MODEL;
        //sModel = "HTC One M8";
        btnMyModel.setText(getResources().getString(R.string.btn_my_model)+" "+sModel);

		//btnSearchModel.setWidth(btnWidth);
		btnSearchModel.setHeight(btnHeight);
		
//		String sManufacturer = Build.MANUFACTURER;
//        String sBrand        = Build.BRAND;
//        String sProduct      = Build.PRODUCT;
//        String sModel        = Build.MODEL;
        databaseHelper = new DatabaseHelper(getApplicationContext());
        int memSize = getInternalMemorySize();
        if (sModel.toLowerCase().equals("htc one") || sModel.toLowerCase().equals("htc one x")){
            if (memSize > 32000){
                sModel = sModel+" 32G";
            }else if(memSize > 16000){
                sModel = sModel+" 16G";
            }
        }

        model = databaseHelper.getModel(sModel.toLowerCase());
        if (model.getId() ==0){
            llMyModel.setVisibility(View.GONE);
        }

        Log.d("ThangTB", "memsize is: "+getTotalInternalMemorySize());
		
	}

    /*
        return num of MB
     */
    public static int getInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        long size = totalBlocks * blockSize;//byte
        long sizekb = (size/1024);
        int sizemb = (int) (sizekb/1024);
        Log.d("ThangTB", "memsize 1 is: "+size);
        Log.d("ThangTB", "memsize 2 is: "+sizekb);
        Log.d("ThangTB", "memsize 3 is: "+sizemb);

        return sizemb;
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.ll_my_model:
			Intent i = new Intent(getApplicationContext(), CorrectionListActivity.class);
			i.putExtra("model_item", model);
			startActivity(i);
			break;
		case R.id.ll_search_model:
			//Intent i2 = new Intent(getApplicationContext(), ModelListActivity.class);
            Intent i2 = new Intent(getApplicationContext(), BrandActivity.class);
			startActivity(i2);
			break;

		default:
			break;
		}
	}
	
}
