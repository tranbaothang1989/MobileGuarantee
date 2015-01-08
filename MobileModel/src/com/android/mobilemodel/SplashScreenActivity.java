package com.android.mobilemodel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.android.mobilemodel.database.DatabaseHelper;
import com.android.mobilemodel.entity.ApplianceEntity;
import com.android.mobilemodel.entity.CorrectionEntity;
import com.android.mobilemodel.entity.MainModel;
import com.android.mobilemodel.entity.Model;
import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        }else{
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            ArrayList<String> appliance = new ArrayList<String>();
            ArrayList<String> constant = new ArrayList<String>();
            try {
                InputStream is = getAssets().open("datasuachuadt.csv");
                char c = '\t';
                CsvReader products = new CsvReader(is, c, Charset.forName("UTF-8"));

                //products.readHeaders();

                HashMap<String, Model> mapModel = new HashMap<String, Model>();
                HashMap<String, CorrectionEntity> mapCorrection = new HashMap<String, CorrectionEntity>();
                //HashMap<String, ApplianceEntity> mapComponent = new HashMap<String, ApplianceEntity>();
                ArrayList<Model> listModel = new ArrayList<Model>();
                ArrayList<CorrectionEntity> listCorrection = new ArrayList<CorrectionEntity>();
                ArrayList<ApplianceEntity> listAppliance = new ArrayList<ApplianceEntity>();

                ArrayList<MainModel> mainModels = new ArrayList<MainModel>();

                int id=0;
                int flg =0;
                while (products.readRecord())
                {
                    if (flg == 0) {
                        flg =1;
                        continue;
                    }
                    String brand = products.get(0);
                    String modelName = products.get(2).trim();

                    String correctionCode = products.get(3).trim();
                    String correctionName = products.get(4).trim();

                    String applianceCode = products.get(6).trim();
                    String applianceName = products.get(7).trim();

                    //String guarantee = products.get(8).trim();

                    String appliancePrice = products.get(9).trim();
                    String fee = products.get(10).trim();

                    MainModel mainModel = new MainModel();
                    mainModel.setModelCode(modelName);
                    mainModel.setCorrectionCode(correctionCode);
                    mainModel.setCorrectionName(correctionName);
                    mainModel.setApplianceCode(applianceCode);
                    mainModel.setApplianceName(applianceName);
                    mainModel.setAppliancePrice(Integer.parseInt(appliancePrice.trim().equals("") ? "0": appliancePrice));
                    mainModel.setFee(Integer.parseInt(fee.trim().equals("") ? "0": fee));
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

                Log.d("ThangTB", " list model before: " + listModel.size());
                databaseHelper.insertModelList(listModel);
                listModel = (ArrayList<Model>) databaseHelper.getAllModels();
                Log.d("ThangTB", " list model after: "+ listModel.size());

                int modelSize = listModel.size();

                ArrayList<MainModel> mainModelsTemp = (ArrayList<MainModel>) mainModels.clone();
                HashMap<String, ArrayList<ApplianceEntity>> applianceMaps = new HashMap<String, ArrayList<ApplianceEntity>>();

                Log.d("ThangTB", " list correction after: "+ listCorrection.size()+ "  time "+ new Date().getTime());
                long time1 =new Date().getTime();
                for (int i = 0; i < modelSize; i++) {

                    Model model = listModel.get(i);
                    ArrayList<ApplianceEntity> applianceTemp = new ArrayList<ApplianceEntity>();
                    for (MainModel mainModel : mainModelsTemp){

                        if (mainModel.getModelCode().equals(model.getModelName())){
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

                Log.d("ThangTB", " list correction after: "+ listCorrection.size()+ "  time "+ new Date().getTime());

                databaseHelper.insertCorrections(listCorrection);

                for (Model model : listModel){
                    listCorrection = (ArrayList<CorrectionEntity>) databaseHelper.getAllCorrectionsByModelId(model.getId());
                    ArrayList<ApplianceEntity> applianceTemp = applianceMaps.get(model.getModelName());

                    for (CorrectionEntity correctionEntity : listCorrection){
                        for (ApplianceEntity appEntity : applianceTemp){
                            if (correctionEntity.getCode().equals(appEntity.getCorrectionCode())){
                                appEntity.setCorrectionId(correctionEntity.getId());
                                listAppliance.add(appEntity);
                            }
                        }
                    }

                }
                Log.d("ThangTB", " list listAppliance after: "+ listAppliance.size()+ "  time "+ new Date().getTime());
                databaseHelper.insertAppliances(listAppliance);
                long time2 =new Date().getTime();
                Log.d("ThangTB", " total time : "+ (time2-time1));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            Log.d("ThangTB", "import done");
            super.onPostExecute(result);

            StartMainActivity();
        }
    }

    private void StartMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}