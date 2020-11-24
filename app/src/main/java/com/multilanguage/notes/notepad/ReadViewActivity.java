package com.multilanguage.notes.notepad;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.multilanguage.notes.notepad.adapter.MemoAdapter;
import com.multilanguage.notes.notepad.model.MemoInfo;

import java.io.Serializable;

public class ReadViewActivity extends AppCompatActivity {
    Typeface tf2;
    TextView memo,dateTime;
ImageView buttonEdit,buttonDelete;
    MemoDbClass memoDbClass;
MaterialDialog dialog1;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        memoDbClass=new MemoDbClass(ReadViewActivity.this);
        memo= (TextView) findViewById(R.id.memo);
        dateTime=(TextView) findViewById(R.id.txt_date);
        buttonEdit=(ImageView) findViewById(R.id.edit_btn);
        buttonDelete=(ImageView) findViewById(R.id.delete_btn);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDialog.Builder builder = new MaterialDialog.Builder(ReadViewActivity.this)
                        .theme(Theme.LIGHT)
                        .title(R.string.title)
                        .content(R.string.content)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {

                                memoDbClass.Delete(MemoAdapter.mMemoInfo.getMemo_id());
                                MainActivity.getAllmemos();
                                finish();


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
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressImageFolder p = new ProgressImageFolder();
                p.execute();
            }
        });

        tf2= Typeface.createFromAsset(getAssets(),
                "jameel.ttf");

        memo.setText(MemoAdapter.memoTxt);
        memo.setTypeface(tf2);
        dateTime.setText(" "+MemoAdapter.mMemoInfo.getDate()+" "+MemoAdapter.mMemoInfo.getMemo_time());
    }



    class ProgressImageFolder extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... unsued) {

            // setting grid view adapter
           // mMemoInfo = memoInfoList.get(po);
            //Toast.makeText(context,"position "+position,Toast.LENGTH_SHORT).show();

        //    memoTxt=memoDbClass.getSingleMemo(mMemoInfo.getMemo_id());
         //   posi=mMemoInfo.getMemo_id();
            //  Toast.makeText(context,"position "+posi+" "+memoTxt,Toast.LENGTH_SHORT).show();


            Intent updateIntent=new Intent(ReadViewActivity.this,KeyboardActivity.class);
            updateIntent.putExtra("id",MemoAdapter.mMemoInfo.getMemo_id());
            updateIntent.putExtra("memo",MemoAdapter.memoTxt);
            startActivity(updateIntent);

            return null;

        }

        @Override
        protected void onPreExecute() {


            dialog1 = new MaterialDialog.Builder(ReadViewActivity.this)
                    .title(R.string.progress_dialog)
                    .theme(Theme.LIGHT)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .show();


        }

        @Override
        protected void onProgressUpdate(Void... unsued) {

        }

        @Override
        protected void onPostExecute(String sResponse) {


            dialog1.dismiss();

        }
    }
}
