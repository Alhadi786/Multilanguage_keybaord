package com.multilanguage.notes.notepad;;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.multilanguage.notes.notepad.adapter.MemoAdapter;
import com.multilanguage.notes.notepad.model.MemoInfo;
import com.multilanguage.notes.notepad.utils.RecyclerViewUtils.ItemOffsetDecoration;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



     final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {


        MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                .theme(Theme.LIGHT)
                .title(R.string.title_exit)
                .content(R.string.content_exit)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        finish();
                        System.exit(0);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();


    }

    Context context;

    static List<MemoInfo> memoInfoList;
 MemoInfo memoInfo;
    static MemoAdapter memoAdapter;
    static MemoDbClass memoDbClass;
    MaterialDialog dialog;
    //UI
    RecyclerView recyclerView;
AppPreferences appPreferences;
     final  String DATABASE_NAME="MemoDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.appPreferences = MainApplication.getAppPreferences();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        if(!appPreferences.getIsFirstRun()){
            appPreferences.setIsFirstRun(true);
            SelectLanguage();
        }
        memoDbClass=new MemoDbClass(getApplicationContext());
      //   checkDB();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());

        memoInfoList = new ArrayList<MemoInfo>();
        memoInfoList.clear();
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemOffsetDecoration itemDecorationSC = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecorationSC);

        memoAdapter = new MemoAdapter(MainActivity.this, memoInfoList, R.layout.list_item);
        recyclerView.setAdapter(memoAdapter);
       getAllmemos();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressImageFolder p = new ProgressImageFolder();
                p.execute();
            }
        });


    }

    private void SelectLanguage() {
        MaterialDialog.Builder builder= new MaterialDialog.Builder(this)
                .title(R.string.title_language)
                .items(R.array.array_languages)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        appPreferences.setLanguage(text.toString());
                        appPreferences.setIndex(which);


                        Log.i(TAG, "onSelection: "+which);
                        return true;
                    }
                })
                .positiveText(R.string.choose);
        MaterialDialog dialog = builder.build();
        dialog.setSelectedIndex(appPreferences.getIndex());
        dialog.show();
    }

    class ProgressImageFolder extends AsyncTask<Void, Void, String> {


        protected String doInBackground(Void... unsued) {


            Intent keyboardIntent=new Intent(MainActivity.this,KeyboardActivity.class);
            startActivity(keyboardIntent);
            // setting grid view adapter

            return null;

        }


        protected void onPreExecute() {



             dialog = new MaterialDialog.Builder(MainActivity.this)
                    .title(R.string.progress_dialog)
                     .theme(Theme.LIGHT)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .show();

        }


        protected void onProgressUpdate(Void... unsued) {

        }
    protected void onPostExecute(String sResponse) {


            dialog.dismiss();

        }
    }

    public static  void getAllmemos() {
        memoInfoList.clear();
        memoAdapter.clearData();
        memoInfoList = memoDbClass.getAllDBMemo();

        memoAdapter.setData(memoInfoList);
    }
    public void checkDB() {
        try {

            // android default database location is :
            // /data/data/youapppackagename/databases/
            String packageName = this.getPackageName();
            String destPath = "/data/data/" + packageName + "/databases";
            String fullPath = "/data/data/" + packageName + "/databases/"
                    + DATABASE_NAME;

            // this database folder location
            File f = new File(destPath);

            // this database file location
            File obj = new File(fullPath);

            // check if databases folder exists or not. if not create it
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
            }

            // check database file exists or not, if not copy database from
            // assets
            if (!obj.exists()) {
                this.CopyDB(fullPath);
                Log.e("data base does not ", "No");
                Toast.makeText(MainActivity.this," No data base exits ",Toast.LENGTH_SHORT).show();


            } else {
                Log.e("data base exists", "yes");
                Toast.makeText(MainActivity.this,"data base exits here",Toast.LENGTH_SHORT).show();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    public void CopyDB(String path) throws IOException {

        InputStream databaseInput = null;
        String outFileName = path;
        OutputStream databaseOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        // open database file from asset folder
        databaseInput = this.getAssets().open(
                "database" + File.separator + DATABASE_NAME);
        while ((length = databaseInput.read(buffer)) > 0) {
            databaseOutput.write(buffer, 0, length);
            databaseOutput.flush();
        }

        databaseInput.close();
        databaseOutput.flush();
        databaseOutput.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.action_settings){
           SelectLanguage();
        }

        if (id == R.id.action_delete) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                    .theme(Theme.LIGHT)
                    .title(R.string.title_delete)
                    .content(R.string.content_delete)
                    .positiveText(R.string.yes)
                    .negativeText(R.string.no)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            memoDbClass.DeleteAll();
                            getAllmemos();


                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            dialog.dismiss();
                        }
                    });

            MaterialDialog dialog = builder.build();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
