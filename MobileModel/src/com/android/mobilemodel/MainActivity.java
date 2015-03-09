package com.android.mobilemodel;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.Model;

import java.io.File;
import java.text.DecimalFormat;

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
        double memSize = 0;

        if (sModel.toLowerCase().equals("htc one")){
            memSize = convertbytes(TotalMemory());
            if (memSize > 16){
                sModel = sModel+" 32G";
            }else{
                sModel = sModel+" 16G";
            }
        }
        if(sModel.toLowerCase().equals("htc one x")){
            memSize = convertbytes(getTotalExternalMemorySizeToInt());
            if (memSize > 16){
                sModel = sModel+" 32G";
            }else{
                sModel = sModel+" 16G";
            }
        }
//        Log.d("ThangTB", "memsize = "+memSize );

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
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        long size = totalBlocks * blockSize;//byte
        long sizekb = (size/1024);
        long sizemb = (sizekb/1024);
        int sizeGB = (int)(sizemb/1024);
        return sizeGB;
    }

    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
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
            Intent i2 = new Intent(getApplicationContext(), BrandActivity.class);
			startActivity(i2);
			break;

		default:
			break;
		}
	}


    //-----------------------------
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize1() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static long getTotalExternalMemorySizeToInt() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return (totalBlocks * blockSize);
        } else {
            return 0;
        }
    }

    public static String formatSize1(long size) {
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
	
}
