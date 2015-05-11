package com.suntelecom.mobilewaranty;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.suntelecom.mobilewaranty.database.DatabaseHelper;
import com.suntelecom.mobilewaranty.entity.ApplianceEntity;
import com.suntelecom.mobilewaranty.entity.CorrectionEntity;
import com.suntelecom.mobilewaranty.entity.MainModel;
import com.suntelecom.mobilewaranty.entity.Model;
import com.csvreader.CsvReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by thangtb on 07/01/2015.
 */
public class SplashScreenActivity extends Activity{

    private static int SPLASH_TIME_OUT = 2000;
    public DatabaseHelper databaseHelper;
    DoImportData doImportData;
    private boolean isImport = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        ImageView imgLoading = (ImageView)findViewById(R.id.imageViewLoading);
        imgLoading.startAnimation(rotate);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        ArrayList<Model> listModel = (ArrayList<Model>) databaseHelper.getAllModels();
        if (listModel.size()==0) {
            doImportData = new DoImportData();
            doImportData.execute();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StartMainActivity();
                }
            }, SPLASH_TIME_OUT);
        }else{
//            try {
//                BackupDatabase();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StartMainActivity();
                }
            }, SPLASH_TIME_OUT);
        }

    }

    private class DoImportData extends AsyncTask<Object, Object, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            if (isImport) {
                ArrayList<String> constant = new ArrayList<String>();
                try {
                    InputStream is = getAssets().open("htc12022015.csv");
                    char c = '\t';
                    CsvReader products = new CsvReader(is, c, Charset.forName("UTF-8"));

                    HashMap<String, CorrectionEntity> mapCorrection = new HashMap<String, CorrectionEntity>();
                    ArrayList<Model> listModel = new ArrayList<Model>();
                    ArrayList<CorrectionEntity> listCorrection = new ArrayList<CorrectionEntity>();
                    ArrayList<ApplianceEntity> listAppliance = new ArrayList<ApplianceEntity>();
                    ArrayList<MainModel> mainModels = new ArrayList<MainModel>();

                    int id = 0;
                    int flg = 0;
                    while (products.readRecord()) {
                        if (flg == 0) {
                            flg = 1;
                            continue;
                        }
                        String brand = products.get(0);
                        String modelName = products.get(2).trim();

                        String correctionCode = products.get(3).trim();
                        String correctionName = products.get(4).trim();

                        String applianceCode = products.get(5).trim();
                        String applianceName = products.get(6).trim();

                        String appliancePrice = products.get(8).trim();
                        String fee = products.get(9).trim();

                        MainModel mainModel = new MainModel();
                        mainModel.setModelCode(modelName);
                        mainModel.setCorrectionCode(correctionCode);
                        mainModel.setCorrectionName(correctionName);
                        mainModel.setApplianceCode(applianceCode);
                        mainModel.setApplianceName(applianceName);
                        mainModel.setAppliancePrice(0);
                        mainModel.setFee(0);
                        try {
                            mainModel.setAppliancePrice(Integer.parseInt(appliancePrice.trim().equals("") ? "0" : appliancePrice));
                        } catch (Exception ex) {
                            Log.d("ThangTB", "can not parser appliancePrice : " + appliancePrice.trim());
                        }
                        try {
                            mainModel.setFee(Integer.parseInt(fee.trim().equals("") ? "0" : fee));
                        } catch (Exception ex) {
                            Log.d("ThangTB", "can not parser fee : " + fee.trim());
                        }
                        mainModels.add(mainModel);
                        if (!constant.contains(modelName)) {
                            Model model = new Model();
                            model.setBrand(brand);
                            model.setModelName(modelName);
                            constant.add(modelName);
                            listModel.add(model);
                        }
                    }
                    products.close();

                    databaseHelper.insertModelList(listModel);
                    listModel = (ArrayList<Model>) databaseHelper.getAllModels();
                    int modelSize = listModel.size();

                    ArrayList<MainModel> mainModelsTemp = (ArrayList<MainModel>) mainModels.clone();
                    HashMap<String, ArrayList<ApplianceEntity>> applianceMaps = new HashMap<String, ArrayList<ApplianceEntity>>();

                    long time1 = new Date().getTime();
                    for (int i = 0; i < modelSize; i++) {

                        Model model = listModel.get(i);
                        ArrayList<ApplianceEntity> applianceTemp = new ArrayList<ApplianceEntity>();
                        for (MainModel mainModel : mainModelsTemp) {

                            if (mainModel.getModelCode().equals(model.getModelName())) {
                                if (!mapCorrection.containsKey(mainModel.getCorrectionCode())) {
                                    CorrectionEntity mCorrectionEntity = new CorrectionEntity();
                                    mCorrectionEntity.setModelId(model.getId());
                                    mCorrectionEntity.setCode(mainModel.getCorrectionCode());
                                    mCorrectionEntity.setName(mainModel.getCorrectionName());
                                    mCorrectionEntity.setNameShow(mainModel.getCorrectionName());

                                    mapCorrection.put(mainModel.getCorrectionCode(), mCorrectionEntity);
                                    listCorrection.add(mCorrectionEntity);
                                }


                                ApplianceEntity mApplianceEntity = new ApplianceEntity();
                                mApplianceEntity.setCorrectionCode(mainModel.getCorrectionCode());

                                mApplianceEntity.setCode(mainModel.getApplianceCode());
                                mApplianceEntity.setName(mainModel.getApplianceName());
                                mApplianceEntity.setAppliancePrice(mainModel.getAppliancePrice());
                                mApplianceEntity.setFee(mainModel.getFee());

                                applianceTemp.add(mApplianceEntity);

                            }
                        }
                        applianceMaps.put(model.getModelName(), applianceTemp);

                        mapCorrection.clear();

                    }

                    databaseHelper.insertCorrections(listCorrection);

                    for (Model model : listModel) {
                        listCorrection = (ArrayList<CorrectionEntity>) databaseHelper.getAllCorrectionsByModelId(model.getId());
                        ArrayList<ApplianceEntity> applianceTemp = applianceMaps.get(model.getModelName());

                        for (CorrectionEntity correctionEntity : listCorrection) {
                            for (ApplianceEntity appEntity : applianceTemp) {
                                if (correctionEntity.getCode().equals(appEntity.getCorrectionCode())) {
                                    appEntity.setCorrectionId(correctionEntity.getId());
                                    listAppliance.add(appEntity);
                                }
                            }
                        }

                    }
                    databaseHelper.insertAppliances(listAppliance);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    copyDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            StartMainActivity();
        }
    }

    private void StartMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void BackupDatabase() throws Exception{
        final String inFileName = "/data/data/com.suntelecom.mobilewaranty/databases/contactsManager";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory() + "/contactsManager.db";
        Log.d("ThangTB","file name: "+outFileName );

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = getAssets().open("contactsManager.db");
        // Path to the just created empty db
        String outFileName = "/data/data/com.suntelecom.mobilewaranty/databases/contactsManager";
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
