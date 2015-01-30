package com.android.mobilemodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
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
import android.widget.TextView;

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
		int btnHeight = (screenHeight*20)/100;

		btnMyModel.setHeight(btnHeight);
        sModel        = Build.MODEL;
		btnSearchModel.setHeight(btnHeight);
		
//		String sManufacturer = Build.MANUFACTURER;
//        String sBrand        = Build.BRAND;
//        String sProduct      = Build.PRODUCT;
//        String sModel        = Build.MODEL;
        databaseHelper = new DatabaseHelper(getApplicationContext());
        double memSize = convertbytes(TotalMemory());
        Log.d("ThangTB", "memsize = "+memSize );
        if (sModel.toLowerCase().equals("htc one") || sModel.toLowerCase().equals("htc one x")){
            if (memSize > 16){
                sModel = sModel+" 32G";
            }else{
                sModel = sModel+" 16G";
            }
        }

        btnMyModel.setText(getResources().getString(R.string.btn_my_model)+" "+sModel);
        model = databaseHelper.getModel(sModel.toLowerCase());
        if (model.getId() ==0){
            llMyModel.setVisibility(View.GONE);
        }

        TextView tvTest = (TextView)findViewById(R.id.textViewTest);


        tvTest.setTextColor(getResources().getColor(R.color.red));

        tvTest.setText("  Detect: Model:"+sModel+
                "  total:"+bytesToHuman(TotalMemory())+
                "  free:"+bytesToHuman(FreeMemory())+
                "  use:"+bytesToHuman(BusyMemory()));
        tvTest.setVisibility(View.GONE);
		
	}

    /*
        return num of MB
     */
    public static int getInternalMemorySize() {
       // File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        //StatFs stat = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        long size = totalBlocks * blockSize;//byte
        long sizekb = (size/1024);
        long sizemb = (sizekb/1024);
        int sizeGB = (int)(sizemb/1024);
        Log.d("ThangTB", "memsize 1 is: "+size);
        Log.d("ThangTB", "memsize 2 is: "+sizekb);
        Log.d("ThangTB", "memsize 3 is: "+sizemb);


        //long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        //return Total;

        return sizeGB;
    }

    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        //StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long total = 0;
        if (Build.VERSION.SDK_INT <18) {
            total = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        }else{
            total = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
        }
        return total;
    }

    public long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        //StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long free=0;
        if (Build.VERSION.SDK_INT <18) {
            free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        }else{
            free = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
        }
        return free;
    }

    public long BusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        //StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long total = 0;
        long free = 0;
        if (Build.VERSION.SDK_INT <18) {
            total = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
            free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        }else{
            total = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
            free = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
        }
        long   Busy   = total - free;
        return Busy;
    }

    public static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }


    public static String bytesToHuman (long size)
    {
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " Kb";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " Mb";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " Gb";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " Tb";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " Pb";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " Eb";

        return "???";
    }

    public static double convertbytes (long size)
    {
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return  size;
        if (size >= Kb && size < Mb)    return (double)size / Kb;
        if (size >= Mb && size < Gb)    return ((double)size / Mb);
        if (size >= Gb && size < Tb)    return ((double)size / Gb);
        if (size >= Tb && size < Pb)    return ((double)size / Tb);
        if (size >= Pb && size < Eb)    return ((double)size / Pb);
        if (size >= Eb)                 return ((double)size / Eb);

        return 0;
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
