package com.multilanguage.notes.notepad.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.multilanguage.notes.notepad.MainActivity;
import com.multilanguage.notes.notepad.MemoDbClass;
import com.multilanguage.notes.notepad.R;
import com.multilanguage.notes.notepad.ReadViewActivity;
import com.multilanguage.notes.notepad.keyboardUpdateActivty;
import com.multilanguage.notes.notepad.model.MemoInfo;

import java.util.List;


/**
 * Created by UDNA on 8/4/2016.
 */
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {


    private static String TAG = MemoAdapter.class.getSimpleName();

    private List<MemoInfo> memoInfoList;
    private Context context;
    int layout;
public static     MemoInfo mMemoInfo;
  public   static String  memoTxt;
    public  static int  posi;
    int po;
    Typeface tf2;

    MemoDbClass memoDbClass;
    MaterialDialog dialog1;
    Typeface urduFont,englishFont,persianFont,arabicFont,sindhiFont,pashtoFont,frenchFont;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,title,memo_text,time;


        View list_item_view;
        LinearLayout cardView;
        ImageView btnDelete,btnEdit;

        public ViewHolder(View view) {
            super(view);
            this.list_item_view = view;

            date = (TextView) view.findViewById(R.id.tvDate);
            memo_text = (TextView) view.findViewById(R.id.tvMemo);
            cardView= (LinearLayout) view.findViewById(R.id.card_view);
            btnDelete=(ImageView)view.findViewById(R.id.delete_btn);
            btnEdit=(ImageView)view.findViewById(R.id.edit_btn);
            time=(TextView) view.findViewById(R.id.tvTime);
        }


    }


    public MemoAdapter(Context context, List<MemoInfo> items, int list_item) {
        memoInfoList = items;
        this.context = context;
        this.layout = list_item;
        memoDbClass = new MemoDbClass(context);
        init();
    }

    private void init() {
        urduFont=Typeface.createFromAsset(context.getAssets(),
                "jameel.ttf");
        englishFont=Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
        persianFont=Typeface.createFromAsset(context.getAssets(),
                "homa farsi.ttf");
        arabicFont=Typeface.createFromAsset(context.getAssets(),
                "arrabic.ttf");
        sindhiFont=Typeface.createFromAsset(context.getAssets(),
                "sindi.ttf");
        pashtoFont=Typeface.createFromAsset(context.getAssets(),
                "pashto.ttf");
        frenchFont=Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mMemoInfo = memoInfoList.get(position);
        holder.memo_text.setText(mMemoInfo.getMemo_text());

        switch (mMemoInfo.getMemo_language()){
            case 0:
                holder.memo_text.setTypeface(arabicFont);
                break;
            case 1:
                holder.memo_text.setTypeface(englishFont);
                break;
            case 2:
                holder.memo_text.setTypeface(frenchFont);
                break;
            case 3:
                holder.memo_text.setTypeface(englishFont);
                break;
            case 4:
                holder.memo_text.setTypeface(pashtoFont);
                break;
            case 5:
                holder.memo_text.setTypeface(persianFont);
                break;
            case 6:
                holder.memo_text.setTypeface(sindhiFont);
                break;
            case 7:
                holder.memo_text.setTypeface(urduFont);
                break;
            case 8:
                holder.memo_text.setTypeface(urduFont);
                break;

        }


        holder.date.setText(mMemoInfo.getDate());
        holder.time.setText(" "+mMemoInfo.getMemo_time());

        holder.btnEdit.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                po=position;

                ProgressImageFolder p = new ProgressImageFolder();
                p.execute();






            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                po=position;
                mMemoInfo = memoInfoList.get(po);
                //Toast.makeText(context,"position "+position,Toast.LENGTH_SHORT).show();

                memoTxt=memoDbClass.getSingleMemo(mMemoInfo.getMemo_id());
                posi=mMemoInfo.getMemo_id();
                //  Toast.makeText(context,"position "+posi+" "+memoTxt,Toast.LENGTH_SHORT).show();
                Intent updateIntent=new Intent(context,ReadViewActivity.class);
                updateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(updateIntent);

            }
        });
holder.btnDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .title(R.string.title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        mMemoInfo = memoInfoList.get(position);
                        memoDbClass.Delete(mMemoInfo.getMemo_id());
                        MainActivity.getAllmemos();

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
    }


    public void addItem(MemoInfo item, int position) {
        memoInfoList.add(position, item);
        this.notifyDataSetChanged();
    }

    public void addItem(MemoInfo item) {
        memoInfoList.add(item);
    }

    public void clearData() {
        int size = memoInfoList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                memoInfoList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void setData(List<MemoInfo> appInfos) {
        this.memoInfoList.addAll(appInfos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return memoInfoList.size();
    }


    class ProgressImageFolder extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... unsued) {

            // setting grid view adapter
            mMemoInfo = memoInfoList.get(po);
            //Toast.makeText(context,"position "+position,Toast.LENGTH_SHORT).show();

            memoTxt=memoDbClass.getSingleMemo(mMemoInfo.getMemo_id());
            posi=mMemoInfo.getMemo_id();
            Log.i(TAG, "doInBackground: id "+posi+memoTxt);
         //   Toast.makeText(context,"position "+posi+" "+memoTxt,Toast.LENGTH_SHORT).show();
            Intent updateIntent=new Intent(context,keyboardUpdateActivty.class);
            updateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(updateIntent);

            return null;

        }

        @Override
        protected void onPreExecute() {


            dialog1 = new MaterialDialog.Builder(context)
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