package com.multilanguage.notes.notepad;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.multilanguage.notes.notepad.adapter.MemoAdapter;

@SuppressLint("InflateParams")
public class keyboardUpdateActivty extends Activity implements
        OnFocusChangeListener, OnClickListener, OnLongClickListener,
        OnCheckedChangeListener, OnItemSelectedListener, OnTouchListener {

    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;
    boolean checkoption = true;
    boolean defaultSound = true;

    private PopupWindow popupWindow;
    View keyboardPopup;
    int lan =11;
    Rect location;
    int[] keyLocation;
    String memoText;
    MemoDbClass memoDbClass;
    Button btnClosePopup;
    Spinner spinner;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Dialog dialog;
    AudioManager am;
    Vibrator vibe;
    CheckBox _sound, _vib, _alerthide;
    public static boolean check = true, check1 = true;
    EditText mEt, message;
    int cursorPosition;
    int textLength;
    int kbLanguage, kbSound;
    float volume;
    TextView keyBoardPhUr, keyBoardPs, keyBoardSndh, keyBoardAlpUr;
    ToggleButton sound, vibration;
    ImageView setting, keyboradvisibilty;
    Handler handler;
    Runnable runnable;
    private CountDownTimer mTimer;

    Dialog edittextalert;
    AlertDialog.Builder builder;
    AppPreferences appPreferences;

    private Button mBSpace, mBdone, mBack, mBChange, mNum, mBSpace2, mBdone2,
            mBack2, mBChange2, done, cancel, mBackUrSC, engcTOengl,
            spctourdchng, urduSpch1, urduSpch2, urduSpch3, urTOeng, urTOeng2,
            urTOeng3, engTour1, engToenSpChar, engSPCHARtoEng, engCAPTOsp,
            spCharEng, mSpaceEng, changeEngCTOL, enTour2, engTour3, mBSpace3,
            xDoneursp, xDoneEng, xDoneEngc, xDoneEsp, xbackengc,
            xChangeEngtoCap, xBackEng, xBackEngSc, xSpaceEngC, xSpaceEngsp,
            psSpace, psSpace2, psSpaceSc, psDone, psDone2, psDoneSc, psBack,
            psBack2, psBackSc, psChange, psChange2, psToEng, psToEng2,
            psToEngSc, psToPsSc, psPsToSc, psPs2ToSc,

    sndhSpace, sndhSpace2, sndhSpaceSc, sndhDone, sndhDone2,
            sndhDoneSc, sndhBack, sndhBack2, sndhBackSc, sndhChange,
            sndhChange2, sndhToEng, sndhToEng2, sndhToEngSc, sndhToSndhSc,
            sndhPsToSc, sndhPs2ToSc,

    urAlpSpace, urAlpSpace2, urAlpSpaceSc, urAlpDone, urAlpDone2,
            urAlpDoneSc, urAlpBack, urAlpBack2, urAlpBackSc, urAlpChange,
            urAlpChange2, urAlpToEng, urAlpToEng2, urAlpToEngSc,
            urAlpScToUrAlp, urAlpToUrAlpSc, urAlp2ToUrAlpSc,

    hndSpace, hndSpace2, hndSpaceSc, hndDone, hndDone2, hndDoneSc,
            hndBack, hndBack2, hndBackSc, hndChange, hndChange2, hndToLng,
            hndToLng2, hndToLngSc, hndScToHnd, hndToHndSc, hnd2ToHndSc,

    prsSpace, prsSpace2, prsSpaceSc, prsDone, prsDone2, prsDoneSc,
            prsBack, prsBack2, prsBackSc, prsChange, prsChange2, prsToLng,
            prsToLng2, prsToLngSc, prsScToPrs, prsToPrsSc, prs2ToPrsSc,

    arbSpace, arbSpace2, arbSpaceSc, arbDone, arbDone2, arbDoneSc,
            arbBack, arbBack2, arbBackSc, arbChange, arbChange2, arbToLng,
            arbToLng2, arbToLngSc, arbScToArb, arbToArbSc, arb2ToArbSc,

    frenchSpace, frenchSpaceC, frenchSpaceSc, frenchDone, frenchDoneC,
            frenchDoneSc, frenchBack, frenchBackC, frenchBackSc, frenchChange,
            frenchChangeC, frenchToLng, frenchToLngC, frenchToLngSc,
            frenchScTofrench, frenchTofrenchSc, frenchCTofrenchSc;

    static keyboardUpdateActivty keyboardactObject;
    private LinearLayout mKLayout, mKLayout2, mKLayoutSch, mELayout, mELayoutC,
            mPsLayout, mPsLayout2, mPsLayoutSc, mSndhLayout, mSndhLayout2,
            mSndhLayoutSc, mUrAlpLayout, mUrAlpLayout2, mUrAlpLayoutSc,
            mHndLayout, mHndLayout2, mHndLayoutSc, mPrsLayout, mPrsLayout2,
            mPrsLayoutSc, mArbLayout, mArbLayout2, mArbLayoutSc, mFrenchLayout,
            mFrenchLayoutC, mFrenchLayoutSc;
    LinearLayout mELayoutSC;
    private LinearLayout mLayout;
    @SuppressWarnings("unused")
    private boolean isEdit = false;
    boolean soundState, vibState, alertstate;
    Typeface tf2;
    Typeface urduFont,englishFont,persianFont,arabicFont,sindhiFont,pashtoFont,frenchFont;
    boolean keybordflag = false;

    @SuppressWarnings("unused")
    private int mWindowWidth;
    private Button mB[] = new Button[756];
    private int sounds[] = { 0, R.raw.sound001, R.raw.sound002, R.raw.sound003,
            R.raw.sound004, R.raw.sound005 };

    ProgressDialog progressDialog;
    @SuppressWarnings("unused")
    private EditText edText;

    public keyboardUpdateActivty() {
        this.textLength = 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        memoDbClass=new MemoDbClass(keyboardUpdateActivty.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.keyboard_main);
        this.appPreferences = MainApplication.getAppPreferences();
        preferences = PreferenceManager
                .getDefaultSharedPreferences(keyboardUpdateActivty.this);
        tf2=Typeface.createFromAsset(getAssets(),
                "jameel.ttf");

        urduFont=Typeface.createFromAsset(getAssets(),
                "jameel.ttf");
        englishFont=Typeface.createFromAsset(getAssets(),
                "Roboto-Regular.ttf");
        persianFont=Typeface.createFromAsset(getAssets(),
                "homa farsi.ttf");
        arabicFont=Typeface.createFromAsset(getAssets(),
                "arrabic.ttf");
        sindhiFont=Typeface.createFromAsset(getAssets(),
                "sindi.ttf");
        pashtoFont=Typeface.createFromAsset(getAssets(),
                "pashto.ttf");
        frenchFont=Typeface.createFromAsset(getAssets(),
                "Roboto-Regular.ttf");
        soundState = getFromSP("key");
        vibState = getFromSP1("key1");
        alertstate = getFromSP2("alert");
        kbLanguage =appPreferences.getIndex();
        kbSound = preferences.getInt("spinnerSelection", 0);

        keyboardactObject = this;
        new LongOperation().execute("Loading Content");

        mLayout = (LinearLayout) findViewById(R.id.xK1);
        mKLayout = (LinearLayout) findViewById(R.id.xKeyBoard);
        mKLayout2 = (LinearLayout) findViewById(R.id.xKeyBoard2);
        mKLayoutSch = (LinearLayout) findViewById(R.id.xKeyBoardSC);
        mELayout = (LinearLayout) findViewById(R.id.xKeyBoardEn);
        mELayoutC = (LinearLayout) findViewById(R.id.xKeyBoardEnCAP);
        mELayoutSC = (LinearLayout) findViewById(R.id.xKeyBoardEnSch);
        mPsLayout = (LinearLayout) findViewById(R.id.xKeyBoardPs);
        mPsLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardPs2);
        mPsLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardPsSc);
        mSndhLayout = (LinearLayout) findViewById(R.id.xKeyBoardSndh);
        mSndhLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardSndh2);
        mSndhLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardSndhSc);
        mUrAlpLayout = (LinearLayout) findViewById(R.id.xKeyBoardUrAlp);
        mUrAlpLayout2 = (LinearLayout) findViewById(R.id.xKeyBoardUrAlp2);
        mUrAlpLayoutSc = (LinearLayout) findViewById(R.id.xKeyBoardUrAlpSc);
        mHndLayout = (LinearLayout) findViewById(R.id.mHndKeyBoard);
        mHndLayout2 = (LinearLayout) findViewById(R.id.mHndKeyBoard2);
        mHndLayoutSc = (LinearLayout) findViewById(R.id.mHndKeyBoardSC);
        mPrsLayout = (LinearLayout) findViewById(R.id.mPrsKeyBoard);
        mPrsLayout2 = (LinearLayout) findViewById(R.id.mPrsKeyBoard2);
        mPrsLayoutSc = (LinearLayout) findViewById(R.id.mPrsKeyBoardSC);
        mArbLayout = (LinearLayout) findViewById(R.id.mArbKeyBoard);
        mArbLayout2 = (LinearLayout) findViewById(R.id.mArbKeyBoard2);
        mArbLayoutSc = (LinearLayout) findViewById(R.id.mArbKeyBoardSC);
        mFrenchLayout = (LinearLayout) findViewById(R.id.mFrenchKeyBoard);
        mFrenchLayoutC = (LinearLayout) findViewById(R.id.mFrenchKeyBoardC);
        mFrenchLayoutSc = (LinearLayout) findViewById(R.id.mFrenchKeyBoardSC);
        CheckKeyBoard();

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        loadSound();

    }

    private void CheckKeyBoard() {
        if (kbLanguage == 0) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            arabicKeyboard();
        } else if (kbLanguage == 1) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            englishKeyboard();
        } else if (kbLanguage == 2) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            frenchKeyboard();
        } else if (kbLanguage == 3) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            hindiKeyboard();
        } else if (kbLanguage == 4) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            pashtoKeyboard();
        } else if (kbLanguage == 5) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            persianKeyboard();
        } else if (kbLanguage == 6) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            sindhiKeyboard();
        } else if (kbLanguage == 7) {
            setting.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            urduPhoneticKeyboard();
        } else if (kbLanguage == 8) {
            urduAlphabeticKeyboard();
        }

    }

    private void loadSound() {
        if (sounds[kbSound] == 0) {
            defaultSound = true;

        } else {
            defaultSound = false;
            soundID = soundPool.load(getApplication(), sounds[kbSound], 1);
        }
    }

    public class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(keyboardUpdateActivty.this,
                    "please Wait", "Loading Contents...");
            keyboardUpdateActivty.this.init();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @SuppressLint("CutPasteId")
    private void init() {
        this.edText = (EditText) findViewById(R.id.xEt);
        done = (Button) findViewById(R.id.done);
        cancel = (Button) findViewById(R.id.cancel);
        setting = (ImageView) findViewById(R.id.setting);
        setting.setSoundEffectsEnabled(true);
        mEt = (EditText) findViewById(R.id.xEt);
        mEt.setTypeface(tf2);
        mEt.setTextColor(Color.BLACK);
        mEt.setTextSize(30);
        mEt.setText(MemoAdapter.memoTxt);
        if (Build.VERSION.SDK_INT >= 11) {
            mEt.setRawInputType(InputType.TYPE_CLASS_TEXT);
            mEt.setTextIsSelectable(true);
        } else {
            mEt.setRawInputType(InputType.TYPE_NULL);
            mEt.setFocusable(true);
        }
        hideDefaultKeyboard();
        setKeys();
        done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                memoText =mEt.getText().toString();

                memoDbClass.updateMemo(MemoAdapter.posi-1,memoText);

                Log.i("keyboardUpdateActivty", "done: id "+(MemoAdapter.posi-1)+"txt "+memoText);
                MainActivity.getAllmemos();

                finish();

            }
        });

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keyBoardSetting();
            }
        });

    }

    public static keyboardUpdateActivty getInstance() {
        return keyboardactObject;
    }

    private void isdeleteChar(View v) {
        mTimer = new CountDownTimer(60000, 120) {
            @Override
            public void onTick(long millisUntilFinished) {

                int selectionStart = mEt.getSelectionStart();
                int selectionEnd = mEt.getSelectionEnd();
                String checking = mEt.getText().toString();
                String selectedText = checking.substring(selectionStart,
                        selectionEnd);

                if (!selectedText.isEmpty()) {
                    String selectionDeletedString = checking.replace(
                            selectedText, "");
                    mEt.setText(selectionDeletedString);
                    mEt.setSelection(selectionStart);

                }

                else if ((mEt.getText().toString().length() > 0)) {
                    int temp = mEt.getSelectionEnd() - 1;
                    if (temp >= 0) {
                        mEt.setText((mEt.getText().toString()
                                .substring(0, mEt.getSelectionEnd() - 1)
                                .concat(mEt
                                        .getText()
                                        .toString()
                                        .substring(mEt.getSelectionEnd(),
                                                mEt.getText().length()))));
                        mEt.setSelection(temp);

                    }

                }

            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();
    }

    public static void setTextInTextView(TextView tv, Context context,
                                         String text) {
        tv.setGravity(80);
        tv.setText(text);
    }

    /* enabling customized keyboard */
    private void enableKeyboard() {
        keybordflag = true;
        mLayout.setVisibility(LinearLayout.VISIBLE);
        mKLayout.setVisibility(RelativeLayout.VISIBLE);

    }

    private void hideDefaultKeyboard() {
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @SuppressLint("CutPasteId")
    @SuppressWarnings("deprecation")
    private void setKeys() {
        mWindowWidth = getWindowManager().getDefaultDisplay().getWidth();
		/* urdu first keyboard keys */
        mB[0] = (Button) findViewById(R.id.xP);
        mB[1] = (Button) findViewById(R.id.xH);
        mB[2] = (Button) findViewById(R.id.xY);
        mB[3] = (Button) findViewById(R.id.Xha);
        mB[4] = (Button) findViewById(R.id.xYY);
        mB[5] = (Button) findViewById(R.id.xT);
        mB[6] = (Button) findViewById(R.id.xR);
        mB[7] = (Button) findViewById(R.id.xE);
        mB[8] = (Button) findViewById(R.id.xW);
        mB[9] = (Button) findViewById(R.id.xQ);
        mB[10] = (Button) findViewById(R.id.x1L);
        mB[11] = (Button) findViewById(R.id.x1K);
        mB[12] = (Button) findViewById(R.id.x1J);
        mB[13] = (Button) findViewById(R.id.x1H);
        mB[14] = (Button) findViewById(R.id.x1G);
        mB[15] = (Button) findViewById(R.id.x1F);
        mB[16] = (Button) findViewById(R.id.x1D);
        mB[17] = (Button) findViewById(R.id.x1S);
        mB[18] = (Button) findViewById(R.id.x1A);
        mB[19] = (Button) findViewById(R.id.X2lm);
        mB[20] = (Button) findViewById(R.id.x2n);
        mB[21] = (Button) findViewById(R.id.x2B);
        mB[22] = (Button) findViewById(R.id.x2T);
        mB[23] = (Button) findViewById(R.id.x2CH);
        mB[24] = (Button) findViewById(R.id.x2SH);
        mB[25] = (Button) findViewById(R.id.x2Z);
		/* urdu second keyboard keys */
        mB[26] = (Button) findViewById(R.id.cLB);
        mB[27] = (Button) findViewById(R.id.cRB);
        mB[28] = (Button) findViewById(R.id.cP);
        mB[29] = (Button) findViewById(R.id.cUP);
        mB[30] = (Button) findViewById(R.id.cSN);
        mB[31] = (Button) findViewById(R.id.cTE);
        mB[32] = (Button) findViewById(R.id.cRE);
        mB[33] = (Button) findViewById(R.id.cAS);
        mB[34] = (Button) findViewById(R.id.cDD);
        mB[35] = (Button) findViewById(R.id.cATT);
        mB[36] = (Button) findViewById(R.id.cSD);
        mB[37] = (Button) findViewById(R.id.cDDT);
        mB[38] = (Button) findViewById(R.id.cRH);
        mB[39] = (Button) findViewById(R.id.cKH);
        mB[40] = (Button) findViewById(R.id.cZD);
        mB[41] = (Button) findViewById(R.id.cHH);
        mB[42] = (Button) findViewById(R.id.cGH);
        mB[43] = (Button) findViewById(R.id.cCM);
        mB[44] = (Button) findViewById(R.id.cDL);
        mB[45] = (Button) findViewById(R.id.cSNS);
        mB[46] = (Button) findViewById(R.id.cNGN);
        mB[47] = (Button) findViewById(R.id.cZZ);
        mB[48] = (Button) findViewById(R.id.cSA);
        mB[49] = (Button) findViewById(R.id.cXAH);
        mB[50] = (Button) findViewById(R.id.cZAH);
        mB[51] = (Button) findViewById(R.id.cST);
        mB[52] = (Button) findViewById(R.id.cRZ);
        mB[53] = (Button) findViewById(R.id.cST2);
		/* urdu digits */
        mB[54] = (Button) findViewById(R.id.ur1);
        mB[55] = (Button) findViewById(R.id.ur2);
        mB[56] = (Button) findViewById(R.id.ur3);
        mB[57] = (Button) findViewById(R.id.ur4);
        mB[58] = (Button) findViewById(R.id.ur5);
        mB[59] = (Button) findViewById(R.id.ur6);
        mB[60] = (Button) findViewById(R.id.ur7);
        mB[61] = (Button) findViewById(R.id.ur8);
        mB[62] = (Button) findViewById(R.id.ur9);
        mB[63] = (Button) findViewById(R.id.urzr);
		/* urdu special characters */
        mB[64] = (Button) findViewById(R.id.urdusp1);
        mB[65] = (Button) findViewById(R.id.urdusp2);
        mB[66] = (Button) findViewById(R.id.urdusp3);
        mB[67] = (Button) findViewById(R.id.urdusp4);
        mB[68] = (Button) findViewById(R.id.urdusp5);
        mB[69] = (Button) findViewById(R.id.urdusp6);
        mB[70] = (Button) findViewById(R.id.urdusp7);
        mB[71] = (Button) findViewById(R.id.urdusp8);
        mB[72] = (Button) findViewById(R.id.urdusp9);
        mB[73] = (Button) findViewById(R.id.ursp12);
        mB[74] = (Button) findViewById(R.id.ursp13);
        mB[75] = (Button) findViewById(R.id.ursp15);
        mB[76] = (Button) findViewById(R.id.ursp16);
        mB[77] = (Button) findViewById(R.id.ursp17);
        mB[78] = (Button) findViewById(R.id.ursp18);
        mB[79] = (Button) findViewById(R.id.ursp19);
        mB[80] = (Button) findViewById(R.id.cSTur);
        for(int i=0;i<=80;i++){
            mB[i].setTypeface(urduFont);
        }
		/* english keybord */
        mB[81] = (Button) findViewById(R.id.engA1);
        mB[82] = (Button) findViewById(R.id.engA2);
        mB[83] = (Button) findViewById(R.id.engA3);
        mB[84] = (Button) findViewById(R.id.engA4);
        mB[85] = (Button) findViewById(R.id.engA5);
        mB[86] = (Button) findViewById(R.id.engA6);
        mB[87] = (Button) findViewById(R.id.engA7);
        mB[88] = (Button) findViewById(R.id.engA8);
        mB[89] = (Button) findViewById(R.id.engA9);
        mB[90] = (Button) findViewById(R.id.engA10);
        mB[91] = (Button) findViewById(R.id.engA11);
        mB[92] = (Button) findViewById(R.id.engA12);
        mB[93] = (Button) findViewById(R.id.engA13);
        mB[94] = (Button) findViewById(R.id.engA14);
        mB[95] = (Button) findViewById(R.id.engA15);
        mB[96] = (Button) findViewById(R.id.engA16);
        mB[97] = (Button) findViewById(R.id.engA17);
        mB[98] = (Button) findViewById(R.id.engA18);
        mB[99] = (Button) findViewById(R.id.eng19);
        mB[100] = (Button) findViewById(R.id.eng20);
        mB[101] = (Button) findViewById(R.id.eng21);
        mB[102] = (Button) findViewById(R.id.eng22);
        mB[103] = (Button) findViewById(R.id.eng23);
        mB[104] = (Button) findViewById(R.id.eng24);
        mB[105] = (Button) findViewById(R.id.eng25);
        mB[106] = (Button) findViewById(R.id.eng26);
        mB[107] = (Button) findViewById(R.id.engStop);
		/* english keybord capital */
        mB[108] = (Button) findViewById(R.id.engAC1);
        mB[109] = (Button) findViewById(R.id.engAC2);
        mB[110] = (Button) findViewById(R.id.engAC3);
        mB[111] = (Button) findViewById(R.id.engAC4);
        mB[112] = (Button) findViewById(R.id.engAC5);
        mB[113] = (Button) findViewById(R.id.engAC6);
        mB[114] = (Button) findViewById(R.id.engAC7);
        mB[115] = (Button) findViewById(R.id.engAC8);
        mB[116] = (Button) findViewById(R.id.engAC9);
        mB[117] = (Button) findViewById(R.id.engAC10);
        mB[118] = (Button) findViewById(R.id.engAC11);
        mB[119] = (Button) findViewById(R.id.engAC12);
        mB[120] = (Button) findViewById(R.id.engAC13);
        mB[121] = (Button) findViewById(R.id.engAC14);
        mB[122] = (Button) findViewById(R.id.engAC15);
        mB[123] = (Button) findViewById(R.id.engAC16);
        mB[124] = (Button) findViewById(R.id.engAC17);
        mB[125] = (Button) findViewById(R.id.engAC18);
        mB[126] = (Button) findViewById(R.id.engAC19);
        mB[127] = (Button) findViewById(R.id.engAC20);
        mB[128] = (Button) findViewById(R.id.engAC21);
        mB[129] = (Button) findViewById(R.id.engAC22);
        mB[130] = (Button) findViewById(R.id.engAC23);
        mB[131] = (Button) findViewById(R.id.engAC24);
        mB[132] = (Button) findViewById(R.id.engAC25);
        mB[133] = (Button) findViewById(R.id.engAC26);
        mB[134] = (Button) findViewById(R.id.engStopcS);
		/* english keybord digits */
        mB[135] = (Button) findViewById(R.id.eng1);
        mB[136] = (Button) findViewById(R.id.eng2);
        mB[137] = (Button) findViewById(R.id.eng3);
        mB[138] = (Button) findViewById(R.id.eng4);
        mB[139] = (Button) findViewById(R.id.eng5);
        mB[140] = (Button) findViewById(R.id.eng6);
        mB[141] = (Button) findViewById(R.id.eng7);
        mB[142] = (Button) findViewById(R.id.eng8);
        mB[143] = (Button) findViewById(R.id.eng9);
        mB[144] = (Button) findViewById(R.id.engzr);
		/* english keybord Special Character */
        mB[145] = (Button) findViewById(R.id.engsp1);
        mB[146] = (Button) findViewById(R.id.engsp2);
        mB[147] = (Button) findViewById(R.id.engsp3);
        mB[148] = (Button) findViewById(R.id.engsp4);
        mB[149] = (Button) findViewById(R.id.engsp5);
        mB[150] = (Button) findViewById(R.id.engsp6);
        mB[151] = (Button) findViewById(R.id.engsp7);
        mB[152] = (Button) findViewById(R.id.engsp8);
        mB[153] = (Button) findViewById(R.id.engsp9);
        mB[154] = (Button) findViewById(R.id.engsp12);
        mB[155] = (Button) findViewById(R.id.engsp13);
        mB[156] = (Button) findViewById(R.id.engsp14);
        mB[157] = (Button) findViewById(R.id.engsp15);
        mB[158] = (Button) findViewById(R.id.engsp16);
        mB[159] = (Button) findViewById(R.id.engsp17);
        mB[160] = (Button) findViewById(R.id.engsp18);
        mB[161] = (Button) findViewById(R.id.cSTENG);
        for(int i=81;i<=161;i++){
            mB[i].setTypeface(englishFont);
        }
		/* pashto first keyboard */
        mB[162] = (Button) findViewById(R.id.psP);
        mB[163] = (Button) findViewById(R.id.psH);
        mB[164] = (Button) findViewById(R.id.psY);
        mB[165] = (Button) findViewById(R.id.psXha);
        mB[166] = (Button) findViewById(R.id.psYY);
        mB[167] = (Button) findViewById(R.id.psT);
        mB[168] = (Button) findViewById(R.id.psR);
        mB[169] = (Button) findViewById(R.id.psE);
        mB[170] = (Button) findViewById(R.id.psW);
        mB[171] = (Button) findViewById(R.id.psQ);
        mB[172] = (Button) findViewById(R.id.ps1L);
        mB[173] = (Button) findViewById(R.id.ps1K);
        mB[174] = (Button) findViewById(R.id.ps1J);
        mB[175] = (Button) findViewById(R.id.ps1H);
        mB[176] = (Button) findViewById(R.id.ps1G);
        mB[177] = (Button) findViewById(R.id.ps1F);
        mB[178] = (Button) findViewById(R.id.ps1D);
        mB[179] = (Button) findViewById(R.id.ps1S);
        mB[180] = (Button) findViewById(R.id.ps1A);
        mB[181] = (Button) findViewById(R.id.ps2lm);
        mB[182] = (Button) findViewById(R.id.ps2n);
        mB[183] = (Button) findViewById(R.id.ps2B);
        mB[184] = (Button) findViewById(R.id.ps2T);
        mB[185] = (Button) findViewById(R.id.ps2CH);
        mB[186] = (Button) findViewById(R.id.ps2SH);
        mB[187] = (Button) findViewById(R.id.ps2Z);
		/* pashto second keyboard keys */
        mB[188] = (Button) findViewById(R.id.psLB);
        mB[189] = (Button) findViewById(R.id.psRB);
        mB[190] = (Button) findViewById(R.id.psSp);
        mB[191] = (Button) findViewById(R.id.psUP);
        mB[192] = (Button) findViewById(R.id.psSN);
        mB[193] = (Button) findViewById(R.id.psTE);
        mB[194] = (Button) findViewById(R.id.psRE);
        mB[195] = (Button) findViewById(R.id.psAS);
        mB[196] = (Button) findViewById(R.id.psDD);
        mB[197] = (Button) findViewById(R.id.psATT);
        mB[198] = (Button) findViewById(R.id.psSD);
        mB[199] = (Button) findViewById(R.id.psDDT);
        mB[200] = (Button) findViewById(R.id.psRH);
        mB[201] = (Button) findViewById(R.id.psKH);
        mB[202] = (Button) findViewById(R.id.psZD);
        mB[203] = (Button) findViewById(R.id.psHH);
        mB[204] = (Button) findViewById(R.id.psGH);
        mB[205] = (Button) findViewById(R.id.psCM);
        mB[206] = (Button) findViewById(R.id.psDL);
        mB[207] = (Button) findViewById(R.id.psSNS);
        mB[208] = (Button) findViewById(R.id.psNGN);
        mB[209] = (Button) findViewById(R.id.psZZ);
        mB[210] = (Button) findViewById(R.id.psSA);
        mB[211] = (Button) findViewById(R.id.psXAH);
        mB[212] = (Button) findViewById(R.id.psZAH);
        mB[213] = (Button) findViewById(R.id.psST);
        mB[214] = (Button) findViewById(R.id.psRZ);
        mB[215] = (Button) findViewById(R.id.psST2);
		/* pashto digits */
        mB[216] = (Button) findViewById(R.id.ps1);
        mB[217] = (Button) findViewById(R.id.ps2);
        mB[218] = (Button) findViewById(R.id.ps3);
        mB[219] = (Button) findViewById(R.id.ps4);
        mB[220] = (Button) findViewById(R.id.ps5);
        mB[221] = (Button) findViewById(R.id.ps6);
        mB[222] = (Button) findViewById(R.id.ps7);
        mB[223] = (Button) findViewById(R.id.ps8);
        mB[224] = (Button) findViewById(R.id.ps9);
        mB[225] = (Button) findViewById(R.id.pszr);
		/* urdu special characters */
        mB[226] = (Button) findViewById(R.id.psSpc1);
        mB[227] = (Button) findViewById(R.id.psSpc2);
        mB[228] = (Button) findViewById(R.id.psSp3);
        mB[229] = (Button) findViewById(R.id.psSp4);
        mB[230] = (Button) findViewById(R.id.psSp5);
        mB[231] = (Button) findViewById(R.id.psSp6);
        mB[232] = (Button) findViewById(R.id.psSp7);
        mB[233] = (Button) findViewById(R.id.psSp8);
        mB[234] = (Button) findViewById(R.id.psSp9);
        mB[235] = (Button) findViewById(R.id.psSp12);
        mB[236] = (Button) findViewById(R.id.psSp13);
        mB[237] = (Button) findViewById(R.id.psSp15);
        mB[238] = (Button) findViewById(R.id.psSp16);
        mB[239] = (Button) findViewById(R.id.psSp17);
        mB[240] = (Button) findViewById(R.id.psSp18);
        mB[241] = (Button) findViewById(R.id.psSp19);
        mB[242] = (Button) findViewById(R.id.psSTur);
        for(int i=162;i<=242;i++){
            mB[i].setTypeface(pashtoFont);
        }
		/* sindhi first keyboard */
        mB[243] = (Button) findViewById(R.id.sndhP);
        mB[244] = (Button) findViewById(R.id.sndhH);
        mB[245] = (Button) findViewById(R.id.sndhY);
        mB[246] = (Button) findViewById(R.id.sndhXha);
        mB[247] = (Button) findViewById(R.id.sndhYY);
        mB[248] = (Button) findViewById(R.id.sndhT);
        mB[249] = (Button) findViewById(R.id.sndhR);
        mB[250] = (Button) findViewById(R.id.sndhE);
        mB[251] = (Button) findViewById(R.id.sndhW);
        mB[252] = (Button) findViewById(R.id.sndhQ);
        mB[253] = (Button) findViewById(R.id.sndh1L);
        mB[254] = (Button) findViewById(R.id.sndh1K);
        mB[255] = (Button) findViewById(R.id.sndh1J);
        mB[256] = (Button) findViewById(R.id.sndh1H);
        mB[257] = (Button) findViewById(R.id.sndh1G);
        mB[258] = (Button) findViewById(R.id.sndh1F);
        mB[259] = (Button) findViewById(R.id.sndh1D);
        mB[260] = (Button) findViewById(R.id.sndh1S);
        mB[261] = (Button) findViewById(R.id.sndh1A);
        mB[262] = (Button) findViewById(R.id.sndh2lm);
        mB[263] = (Button) findViewById(R.id.sndh2n);
        mB[264] = (Button) findViewById(R.id.sndh2B);
        mB[265] = (Button) findViewById(R.id.sndh2T);
        mB[266] = (Button) findViewById(R.id.sndh2CH);
        mB[267] = (Button) findViewById(R.id.sndh2SH);
        mB[268] = (Button) findViewById(R.id.sndh2Z);
		/* sindhi second keyboard keys */
        mB[269] = (Button) findViewById(R.id.sndhLB);
        mB[270] = (Button) findViewById(R.id.sndhRB);
        mB[271] = (Button) findViewById(R.id.sndhSp);
        mB[272] = (Button) findViewById(R.id.sndhUP);
        mB[273] = (Button) findViewById(R.id.sndhSN);
        mB[274] = (Button) findViewById(R.id.sndhTE);
        mB[275] = (Button) findViewById(R.id.sndhRE);
        mB[276] = (Button) findViewById(R.id.sndhAS);
        mB[277] = (Button) findViewById(R.id.sndhDD);
        mB[278] = (Button) findViewById(R.id.sndhATT);
        mB[279] = (Button) findViewById(R.id.sndhSD);
        mB[280] = (Button) findViewById(R.id.sndhDDT);
        mB[281] = (Button) findViewById(R.id.sndhRH);
        mB[282] = (Button) findViewById(R.id.sndhKH);
        mB[283] = (Button) findViewById(R.id.sndhZD);
        mB[284] = (Button) findViewById(R.id.sndhHH);
        mB[285] = (Button) findViewById(R.id.sndhGH);
        mB[286] = (Button) findViewById(R.id.sndhCM);
        mB[287] = (Button) findViewById(R.id.sndhDL);
        mB[288] = (Button) findViewById(R.id.sndhSNS);
        mB[289] = (Button) findViewById(R.id.sndhNGN);
        mB[290] = (Button) findViewById(R.id.sndhZZ);
        mB[291] = (Button) findViewById(R.id.sndhSA);
        mB[292] = (Button) findViewById(R.id.sndhXAH);
        mB[293] = (Button) findViewById(R.id.sndhZAH);
        mB[294] = (Button) findViewById(R.id.sndhST);
        mB[295] = (Button) findViewById(R.id.sndhRZ);
        mB[296] = (Button) findViewById(R.id.sndhST2);
		/* sindhi digits */
        mB[297] = (Button) findViewById(R.id.sndh1);
        mB[298] = (Button) findViewById(R.id.sndh2);
        mB[299] = (Button) findViewById(R.id.sndh3);
        mB[300] = (Button) findViewById(R.id.sndh4);
        mB[301] = (Button) findViewById(R.id.sndh5);
        mB[302] = (Button) findViewById(R.id.sndh6);
        mB[303] = (Button) findViewById(R.id.sndh7);
        mB[304] = (Button) findViewById(R.id.sndh8);
        mB[305] = (Button) findViewById(R.id.sndh9);
        mB[306] = (Button) findViewById(R.id.sndhzr);
		/* sindhi special characters */
        mB[307] = (Button) findViewById(R.id.sndhSp1Sc);
        mB[308] = (Button) findViewById(R.id.sndhSp2Sc);
        mB[309] = (Button) findViewById(R.id.sndhSp3);
        mB[310] = (Button) findViewById(R.id.sndhSp4);
        mB[311] = (Button) findViewById(R.id.sndhSp5);
        mB[312] = (Button) findViewById(R.id.sndhSp6);
        mB[313] = (Button) findViewById(R.id.sndhSp7);
        mB[314] = (Button) findViewById(R.id.sndhSp8);
        mB[315] = (Button) findViewById(R.id.sndhSp9);
        mB[316] = (Button) findViewById(R.id.sndhSp12);
        mB[317] = (Button) findViewById(R.id.sndhSp13);
        mB[318] = (Button) findViewById(R.id.sndhSp15);
        mB[319] = (Button) findViewById(R.id.sndhSp16);
        mB[320] = (Button) findViewById(R.id.sndhSp17);
        mB[321] = (Button) findViewById(R.id.sndhSp18);
        mB[322] = (Button) findViewById(R.id.sndhSp19);
        mB[323] = (Button) findViewById(R.id.sndhSTur);
        for(int i=243;i<=323;i++){
            mB[i].setTypeface(sindhiFont);
        }
		/* urdu Alphabetic first keyboard */
        mB[324] = (Button) findViewById(R.id.alifmadd);
        mB[325] = (Button) findViewById(R.id.alif);
        mB[326] = (Button) findViewById(R.id.be);
        mB[327] = (Button) findViewById(R.id.pe);
        mB[328] = (Button) findViewById(R.id.te);
        mB[329] = (Button) findViewById(R.id.tte);
        mB[330] = (Button) findViewById(R.id.se);
        mB[331] = (Button) findViewById(R.id.jim);
        mB[332] = (Button) findViewById(R.id.che);
        mB[333] = (Button) findViewById(R.id.he);
        mB[334] = (Button) findViewById(R.id.khe);
        mB[335] = (Button) findViewById(R.id.dal);
        mB[336] = (Button) findViewById(R.id.ddal);
        mB[337] = (Button) findViewById(R.id.zal);
        mB[338] = (Button) findViewById(R.id.re);
        mB[339] = (Button) findViewById(R.id.rre);
        mB[340] = (Button) findViewById(R.id.ze);
        mB[341] = (Button) findViewById(R.id.zhe);
        mB[342] = (Button) findViewById(R.id.sin);
        mB[343] = (Button) findViewById(R.id.shin);
        mB[344] = (Button) findViewById(R.id.svad);
        mB[345] = (Button) findViewById(R.id.zvad);
        mB[346] = (Button) findViewById(R.id.toe);
        mB[347] = (Button) findViewById(R.id.zoe);
        mB[348] = (Button) findViewById(R.id.ain);
        mB[349] = (Button) findViewById(R.id.ghin);
		/* urdu Alphabetic second keyboard */
        mB[350] = (Button) findViewById(R.id.fe);
        mB[351] = (Button) findViewById(R.id.qaf);
        mB[352] = (Button) findViewById(R.id.kaf);
        mB[353] = (Button) findViewById(R.id.ghaf);
        mB[354] = (Button) findViewById(R.id.lam);
        mB[355] = (Button) findViewById(R.id.mim);
        mB[356] = (Button) findViewById(R.id.non);
        mB[357] = (Button) findViewById(R.id.nunghunna);
        mB[358] = (Button) findViewById(R.id.wawo);
        mB[359] = (Button) findViewById(R.id.hmzawawo);
        mB[360] = (Button) findViewById(R.id.he1);
        mB[361] = (Button) findViewById(R.id.he2);
        mB[362] = (Button) findViewById(R.id.teh);
        mB[363] = (Button) findViewById(R.id.hamza);
        mB[364] = (Button) findViewById(R.id.ye);
        mB[365] = (Button) findViewById(R.id.ye1);
        mB[366] = (Button) findViewById(R.id.yi);
        mB[367] = (Button) findViewById(R.id.yi1);
        mB[368] = (Button) findViewById(R.id.zabar);
        mB[369] = (Button) findViewById(R.id.zir);
        mB[370] = (Button) findViewById(R.id.pish);
        mB[371] = (Button) findViewById(R.id.shad);
        mB[372] = (Button) findViewById(R.id.saw);
        mB[373] = (Button) findViewById(R.id.rza);
        mB[374] = (Button) findViewById(R.id.rh);
        mB[375] = (Button) findViewById(R.id.as);
		/* urdu alphabetic digits */
        mB[376] = (Button) findViewById(R.id.ek);
        mB[377] = (Button) findViewById(R.id.du);
        mB[378] = (Button) findViewById(R.id.teen);
        mB[379] = (Button) findViewById(R.id.chaar);
        mB[380] = (Button) findViewById(R.id.panch);
        mB[381] = (Button) findViewById(R.id.chi);
        mB[382] = (Button) findViewById(R.id.sath);
        mB[383] = (Button) findViewById(R.id.ath);
        mB[384] = (Button) findViewById(R.id.nov);
        mB[385] = (Button) findViewById(R.id.das);
		/* urdu alphabatic special characters */
        mB[386] = (Button) findViewById(R.id.urAlpSp1);
        mB[387] = (Button) findViewById(R.id.urAlpSp2);
        mB[388] = (Button) findViewById(R.id.alpSp3);
        mB[389] = (Button) findViewById(R.id.alpSp4);
        mB[390] = (Button) findViewById(R.id.alpSp5);
        mB[391] = (Button) findViewById(R.id.alpSp6);
        mB[392] = (Button) findViewById(R.id.alpSp7);
        mB[393] = (Button) findViewById(R.id.alpSp8);
        mB[394] = (Button) findViewById(R.id.alpSp9);
        mB[395] = (Button) findViewById(R.id.alpSp10);
        mB[396] = (Button) findViewById(R.id.alpSp11);
        mB[397] = (Button) findViewById(R.id.alpSp12);
        mB[398] = (Button) findViewById(R.id.alpSp13);
        mB[399] = (Button) findViewById(R.id.alpSp14);
        mB[400] = (Button) findViewById(R.id.alpSp15);
        mB[401] = (Button) findViewById(R.id.alpSp16);
        mB[402] = (Button) findViewById(R.id.alpTur);
        mB[403] = (Button) findViewById(R.id.alpTur2);
        mB[404] = (Button) findViewById(R.id.alpTurSc);
        for(int i=324;i<=404;i++){
            mB[i].setTypeface(urduFont);
        }

		/* Hindi first keyboard keys */
        mB[405] = (Button) findViewById(R.id.hndP);
        mB[406] = (Button) findViewById(R.id.hndH);
        mB[407] = (Button) findViewById(R.id.hndY);
        mB[408] = (Button) findViewById(R.id.hndha);
        mB[409] = (Button) findViewById(R.id.hndYY);
        mB[410] = (Button) findViewById(R.id.hndT);
        mB[411] = (Button) findViewById(R.id.hndR);
        mB[412] = (Button) findViewById(R.id.hndE);
        mB[413] = (Button) findViewById(R.id.hndW);
        mB[414] = (Button) findViewById(R.id.hndQ);
        mB[415] = (Button) findViewById(R.id.hnd1L);
        mB[416] = (Button) findViewById(R.id.hnd1K);
        mB[417] = (Button) findViewById(R.id.hnd1J);
        mB[418] = (Button) findViewById(R.id.hnd1H);
        mB[419] = (Button) findViewById(R.id.hnd1G);
        mB[420] = (Button) findViewById(R.id.hnd1F);
        mB[421] = (Button) findViewById(R.id.hnd1D);
        mB[422] = (Button) findViewById(R.id.hnd1S);
        mB[423] = (Button) findViewById(R.id.hnd1A);
        mB[424] = (Button) findViewById(R.id.hnd2lm);
        mB[425] = (Button) findViewById(R.id.hnd2n);
        mB[426] = (Button) findViewById(R.id.hnd2B);
        mB[427] = (Button) findViewById(R.id.hnd2T);
        mB[428] = (Button) findViewById(R.id.hnd2CH);
        mB[429] = (Button) findViewById(R.id.hnd2SH);
        mB[430] = (Button) findViewById(R.id.hnd2Z);
		/* Hindi second keyboard keys */
        mB[431] = (Button) findViewById(R.id.hndLB);
        mB[432] = (Button) findViewById(R.id.hndRB);
        mB[433] = (Button) findViewById(R.id.hndP2);
        mB[434] = (Button) findViewById(R.id.hndUP);
        mB[435] = (Button) findViewById(R.id.hndSN);
        mB[436] = (Button) findViewById(R.id.hndTE);
        mB[437] = (Button) findViewById(R.id.hndRE);
        mB[438] = (Button) findViewById(R.id.hndAS);
        mB[439] = (Button) findViewById(R.id.hndDD);
        mB[440] = (Button) findViewById(R.id.hndTT);
        mB[441] = (Button) findViewById(R.id.hndSD);
        mB[442] = (Button) findViewById(R.id.hndDDT);
        mB[443] = (Button) findViewById(R.id.hndRH);
        mB[444] = (Button) findViewById(R.id.hndKH);
        mB[445] = (Button) findViewById(R.id.hndZD);
        mB[446] = (Button) findViewById(R.id.hndHH);
        mB[447] = (Button) findViewById(R.id.hndGH);
        mB[448] = (Button) findViewById(R.id.hndCM);
        mB[449] = (Button) findViewById(R.id.hndDL);
        mB[450] = (Button) findViewById(R.id.hndSNS);
        mB[451] = (Button) findViewById(R.id.hndNGN);
        mB[452] = (Button) findViewById(R.id.hndZZ);
        mB[453] = (Button) findViewById(R.id.hndSA);
        mB[454] = (Button) findViewById(R.id.hndXAH);
        mB[455] = (Button) findViewById(R.id.hndZAH);
        mB[456] = (Button) findViewById(R.id.hndcST);
        mB[457] = (Button) findViewById(R.id.hndRZ);
        mB[458] = (Button) findViewById(R.id.hndST2);
		/* Hindi digits */
        mB[459] = (Button) findViewById(R.id.hnd1);
        mB[460] = (Button) findViewById(R.id.hnd2);
        mB[461] = (Button) findViewById(R.id.hnd3);
        mB[462] = (Button) findViewById(R.id.hnd4);
        mB[463] = (Button) findViewById(R.id.hnd5);
        mB[464] = (Button) findViewById(R.id.hnd6);
        mB[465] = (Button) findViewById(R.id.hnd7);
        mB[466] = (Button) findViewById(R.id.hnd8);
        mB[467] = (Button) findViewById(R.id.hnd9);
        mB[468] = (Button) findViewById(R.id.hndzr);
		/* Hindi special characters */
        mB[469] = (Button) findViewById(R.id.hndsp1);
        mB[470] = (Button) findViewById(R.id.hndsp2);
        mB[471] = (Button) findViewById(R.id.hndsp3);
        mB[472] = (Button) findViewById(R.id.hndsp4);
        mB[473] = (Button) findViewById(R.id.hndsp5);
        mB[474] = (Button) findViewById(R.id.hndsp6);
        mB[475] = (Button) findViewById(R.id.hndsp7);
        mB[476] = (Button) findViewById(R.id.hndsp8);
        mB[477] = (Button) findViewById(R.id.hndsp9);
        mB[478] = (Button) findViewById(R.id.hndsp12);
        mB[479] = (Button) findViewById(R.id.hndsp13);
        mB[480] = (Button) findViewById(R.id.hndsp15);
        mB[481] = (Button) findViewById(R.id.hndsp16);
        mB[482] = (Button) findViewById(R.id.hndsp17);
        mB[483] = (Button) findViewById(R.id.hndsp18);
        mB[484] = (Button) findViewById(R.id.hndsp19);
        mB[485] = (Button) findViewById(R.id.cSTHnd);
		/* Persian first keyboard keys */
        mB[486] = (Button) findViewById(R.id.prsP);
        mB[487] = (Button) findViewById(R.id.prsH);
        mB[488] = (Button) findViewById(R.id.prsY);
        mB[489] = (Button) findViewById(R.id.prsha);
        mB[490] = (Button) findViewById(R.id.prsYY);
        mB[491] = (Button) findViewById(R.id.prsT);
        mB[492] = (Button) findViewById(R.id.prsR);
        mB[493] = (Button) findViewById(R.id.prsE);
        mB[494] = (Button) findViewById(R.id.prsW);
        mB[495] = (Button) findViewById(R.id.prsQ);
        mB[496] = (Button) findViewById(R.id.prs1L);
        mB[497] = (Button) findViewById(R.id.prs1K);
        mB[498] = (Button) findViewById(R.id.prs1J);
        mB[499] = (Button) findViewById(R.id.prs1H);
        mB[500] = (Button) findViewById(R.id.prs1G);
        mB[501] = (Button) findViewById(R.id.prs1F);
        mB[502] = (Button) findViewById(R.id.prs1D);
        mB[503] = (Button) findViewById(R.id.prs1S);
        mB[504] = (Button) findViewById(R.id.prs1A);
        mB[505] = (Button) findViewById(R.id.prs2lm);
        mB[506] = (Button) findViewById(R.id.prs2n);
        mB[507] = (Button) findViewById(R.id.prs2B);
        mB[508] = (Button) findViewById(R.id.prs2T);
        mB[509] = (Button) findViewById(R.id.prs2CH);
        mB[510] = (Button) findViewById(R.id.prs2SH);
        mB[511] = (Button) findViewById(R.id.prs2Z);
		/* Persian second keyboard keys */
        mB[512] = (Button) findViewById(R.id.prsLB);
        mB[513] = (Button) findViewById(R.id.prsRB);
        mB[514] = (Button) findViewById(R.id.prsSP);
        mB[515] = (Button) findViewById(R.id.prsUP);
        mB[516] = (Button) findViewById(R.id.prsSN);
        mB[517] = (Button) findViewById(R.id.prsTE);
        mB[518] = (Button) findViewById(R.id.prsRE);
        mB[519] = (Button) findViewById(R.id.prsAS);
        mB[520] = (Button) findViewById(R.id.prsDD);
        mB[521] = (Button) findViewById(R.id.prsATT);
        mB[522] = (Button) findViewById(R.id.prsSD);
        mB[523] = (Button) findViewById(R.id.prsDDT);
        mB[524] = (Button) findViewById(R.id.prsRH);
        mB[525] = (Button) findViewById(R.id.prsKH);
        mB[526] = (Button) findViewById(R.id.prsZD);
        mB[527] = (Button) findViewById(R.id.prsHH);
        mB[528] = (Button) findViewById(R.id.prsGH);
        mB[529] = (Button) findViewById(R.id.prsCM);
        mB[530] = (Button) findViewById(R.id.prsDL);
        mB[531] = (Button) findViewById(R.id.prsSNS);
        mB[532] = (Button) findViewById(R.id.prsNGN);
        mB[533] = (Button) findViewById(R.id.prsZZ);
        mB[534] = (Button) findViewById(R.id.prsSA);
        mB[535] = (Button) findViewById(R.id.prsXAH);
        mB[536] = (Button) findViewById(R.id.prsZAH);
        mB[537] = (Button) findViewById(R.id.prscST);
        mB[538] = (Button) findViewById(R.id.prsRZ);
        mB[539] = (Button) findViewById(R.id.prsST2);
		/* Persian digits */
        mB[540] = (Button) findViewById(R.id.prs1);
        mB[541] = (Button) findViewById(R.id.prs2);
        mB[542] = (Button) findViewById(R.id.prs3);
        mB[543] = (Button) findViewById(R.id.prs4);
        mB[544] = (Button) findViewById(R.id.prs5);
        mB[545] = (Button) findViewById(R.id.prs6);
        mB[546] = (Button) findViewById(R.id.prs7);
        mB[547] = (Button) findViewById(R.id.prs8);
        mB[548] = (Button) findViewById(R.id.prs9);
        mB[549] = (Button) findViewById(R.id.prszr);
		/* Persian special characters */
        mB[550] = (Button) findViewById(R.id.prssp1);
        mB[551] = (Button) findViewById(R.id.prssp2);
        mB[552] = (Button) findViewById(R.id.prssp3);
        mB[553] = (Button) findViewById(R.id.prssp4);
        mB[554] = (Button) findViewById(R.id.prssp5);
        mB[555] = (Button) findViewById(R.id.prssp6);
        mB[556] = (Button) findViewById(R.id.prssp7);
        mB[557] = (Button) findViewById(R.id.prssp8);
        mB[558] = (Button) findViewById(R.id.prssp9);
        mB[559] = (Button) findViewById(R.id.prssp12);
        mB[560] = (Button) findViewById(R.id.prssp13);
        mB[561] = (Button) findViewById(R.id.prssp15);
        mB[562] = (Button) findViewById(R.id.prssp16);
        mB[563] = (Button) findViewById(R.id.prssp17);
        mB[564] = (Button) findViewById(R.id.prssp18);
        mB[565] = (Button) findViewById(R.id.prssp19);
        mB[566] = (Button) findViewById(R.id.prscSTSc);
        for(int i=486;i<=566;i++){
            mB[i].setTypeface(persianFont);
        }
		/* Arabic first keyboard keys */
        mB[567] = (Button) findViewById(R.id.arbP);
        mB[568] = (Button) findViewById(R.id.arbH);
        mB[569] = (Button) findViewById(R.id.arbY);
        mB[570] = (Button) findViewById(R.id.arbha);
        mB[571] = (Button) findViewById(R.id.arbYY);
        mB[572] = (Button) findViewById(R.id.arbT);
        mB[573] = (Button) findViewById(R.id.arbR);
        mB[574] = (Button) findViewById(R.id.arbE);
        mB[575] = (Button) findViewById(R.id.arbW);
        mB[576] = (Button) findViewById(R.id.arbQ);
        mB[577] = (Button) findViewById(R.id.arb1L);
        mB[578] = (Button) findViewById(R.id.arb1K);
        mB[579] = (Button) findViewById(R.id.arb1J);
        mB[580] = (Button) findViewById(R.id.arb1H);
        mB[581] = (Button) findViewById(R.id.arb1G);
        mB[582] = (Button) findViewById(R.id.arb1F);
        mB[583] = (Button) findViewById(R.id.arb1D);
        mB[584] = (Button) findViewById(R.id.arb1S);
        mB[585] = (Button) findViewById(R.id.arb1A);
        mB[586] = (Button) findViewById(R.id.arb2lm);
        mB[587] = (Button) findViewById(R.id.arb2n);
        mB[588] = (Button) findViewById(R.id.arb2B);
        mB[589] = (Button) findViewById(R.id.arb2T);
        mB[590] = (Button) findViewById(R.id.arb2CH);
        mB[591] = (Button) findViewById(R.id.arb2SH);
        mB[592] = (Button) findViewById(R.id.arb2Z);
		/* Arabic second keyboard keys */
        mB[593] = (Button) findViewById(R.id.arbLB);
        mB[594] = (Button) findViewById(R.id.arbRB);
        mB[595] = (Button) findViewById(R.id.arbSP);
        mB[596] = (Button) findViewById(R.id.arbUP);
        mB[597] = (Button) findViewById(R.id.arbSN);
        mB[598] = (Button) findViewById(R.id.arbTE);
        mB[599] = (Button) findViewById(R.id.arbRE);
        mB[600] = (Button) findViewById(R.id.arbAS);
        mB[601] = (Button) findViewById(R.id.arbDD);
        mB[602] = (Button) findViewById(R.id.arbATT);
        mB[603] = (Button) findViewById(R.id.arbSD);
        mB[604] = (Button) findViewById(R.id.arbDDT);
        mB[605] = (Button) findViewById(R.id.arbRH);
        mB[606] = (Button) findViewById(R.id.arbKH);
        mB[607] = (Button) findViewById(R.id.arbZD);
        mB[608] = (Button) findViewById(R.id.arbHH);
        mB[609] = (Button) findViewById(R.id.arbGH);
        mB[610] = (Button) findViewById(R.id.arbCM);
        mB[611] = (Button) findViewById(R.id.arbDL);
        mB[612] = (Button) findViewById(R.id.arbSNS);
        mB[613] = (Button) findViewById(R.id.arbNGN);
        mB[614] = (Button) findViewById(R.id.arbZZ);
        mB[615] = (Button) findViewById(R.id.arbSA);
        mB[616] = (Button) findViewById(R.id.arbXAH);
        mB[617] = (Button) findViewById(R.id.arbZAH);
        mB[618] = (Button) findViewById(R.id.arbcST);
        mB[619] = (Button) findViewById(R.id.arbRZ);
        mB[620] = (Button) findViewById(R.id.arbST2);
		/* Arabic digits */
        mB[621] = (Button) findViewById(R.id.arb1);
        mB[622] = (Button) findViewById(R.id.arb2);
        mB[623] = (Button) findViewById(R.id.arb3);
        mB[624] = (Button) findViewById(R.id.arb4);
        mB[625] = (Button) findViewById(R.id.arb5);
        mB[626] = (Button) findViewById(R.id.arb6);
        mB[627] = (Button) findViewById(R.id.arb7);
        mB[628] = (Button) findViewById(R.id.arb8);
        mB[629] = (Button) findViewById(R.id.arb9);
        mB[630] = (Button) findViewById(R.id.arbzr);
		/* Arabic special characters */
        mB[631] = (Button) findViewById(R.id.arbsp1);
        mB[632] = (Button) findViewById(R.id.arbsp2);
        mB[633] = (Button) findViewById(R.id.arbsp3);
        mB[634] = (Button) findViewById(R.id.arbsp4);
        mB[635] = (Button) findViewById(R.id.arbsp5);
        mB[636] = (Button) findViewById(R.id.arbsp6);
        mB[637] = (Button) findViewById(R.id.arbsp7);
        mB[638] = (Button) findViewById(R.id.arbsp8);
        mB[639] = (Button) findViewById(R.id.arbsp9);
        mB[640] = (Button) findViewById(R.id.arbsp12);
        mB[641] = (Button) findViewById(R.id.arbsp13);
        mB[642] = (Button) findViewById(R.id.arbsp15);
        mB[643] = (Button) findViewById(R.id.arbsp16);
        mB[644] = (Button) findViewById(R.id.arbsp17);
        mB[645] = (Button) findViewById(R.id.arbsp18);
        mB[646] = (Button) findViewById(R.id.arbsp19);
        mB[647] = (Button) findViewById(R.id.arbcStSc);
        for(int i=567;i<=647;i++){
            mB[i].setTypeface(arabicFont);
        }
		/* french first keybord */
        mB[648] = (Button) findViewById(R.id.frenchA1);
        mB[649] = (Button) findViewById(R.id.frenchA2);
        mB[650] = (Button) findViewById(R.id.frenchA3);
        mB[651] = (Button) findViewById(R.id.frenchA4);
        mB[652] = (Button) findViewById(R.id.frenchA5);
        mB[653] = (Button) findViewById(R.id.frenchA6);
        mB[654] = (Button) findViewById(R.id.frenchA7);
        mB[655] = (Button) findViewById(R.id.frenchA8);
        mB[656] = (Button) findViewById(R.id.frenchA9);
        mB[657] = (Button) findViewById(R.id.frenchA10);
        mB[658] = (Button) findViewById(R.id.frenchA11);
        mB[659] = (Button) findViewById(R.id.frenchA12);
        mB[660] = (Button) findViewById(R.id.frenchA13);
        mB[661] = (Button) findViewById(R.id.frenchA14);
        mB[662] = (Button) findViewById(R.id.frenchA15);
        mB[663] = (Button) findViewById(R.id.frenchA16);
        mB[664] = (Button) findViewById(R.id.frenchA17);
        mB[665] = (Button) findViewById(R.id.frenchA18);
        mB[666] = (Button) findViewById(R.id.french19);
        mB[667] = (Button) findViewById(R.id.french20);
        mB[668] = (Button) findViewById(R.id.french21);
        mB[669] = (Button) findViewById(R.id.french22);
        mB[670] = (Button) findViewById(R.id.french23);
        mB[671] = (Button) findViewById(R.id.french24);
        mB[672] = (Button) findViewById(R.id.french25);
        mB[673] = (Button) findViewById(R.id.french26);
        mB[674] = (Button) findViewById(R.id.frenchcST);
		/* french second keybord capital */
        mB[675] = (Button) findViewById(R.id.frenchAC1);
        mB[676] = (Button) findViewById(R.id.frenchAC2);
        mB[677] = (Button) findViewById(R.id.frenchAC3);
        mB[678] = (Button) findViewById(R.id.frenchAC4);
        mB[679] = (Button) findViewById(R.id.frenchAC5);
        mB[680] = (Button) findViewById(R.id.frenchAC6);
        mB[681] = (Button) findViewById(R.id.frenchAC7);
        mB[682] = (Button) findViewById(R.id.frenchAC8);
        mB[683] = (Button) findViewById(R.id.frenchAC9);
        mB[684] = (Button) findViewById(R.id.frenchAC10);
        mB[685] = (Button) findViewById(R.id.frenchAC11);
        mB[686] = (Button) findViewById(R.id.frenchAC12);
        mB[687] = (Button) findViewById(R.id.frenchAC13);
        mB[688] = (Button) findViewById(R.id.frenchAC14);
        mB[689] = (Button) findViewById(R.id.frenchAC15);
        mB[690] = (Button) findViewById(R.id.frenchAC16);
        mB[691] = (Button) findViewById(R.id.frenchAC17);
        mB[692] = (Button) findViewById(R.id.frenchAC18);
        mB[693] = (Button) findViewById(R.id.frenchAC19);
        mB[694] = (Button) findViewById(R.id.frenchAC20);
        mB[695] = (Button) findViewById(R.id.frenchAC21);
        mB[696] = (Button) findViewById(R.id.frenchAC22);
        mB[697] = (Button) findViewById(R.id.frenchAC23);
        mB[698] = (Button) findViewById(R.id.frenchAC24);
        mB[699] = (Button) findViewById(R.id.frenchAC25);
        mB[700] = (Button) findViewById(R.id.frenchAC26);
        mB[701] = (Button) findViewById(R.id.frenchCcST);
		/* french keybord digits */
        mB[702] = (Button) findViewById(R.id.french1);
        mB[703] = (Button) findViewById(R.id.french2);
        mB[704] = (Button) findViewById(R.id.french3);
        mB[705] = (Button) findViewById(R.id.french4);
        mB[706] = (Button) findViewById(R.id.french5);
        mB[707] = (Button) findViewById(R.id.french6);
        mB[708] = (Button) findViewById(R.id.french7);
        mB[709] = (Button) findViewById(R.id.french8);
        mB[710] = (Button) findViewById(R.id.french9);
        mB[711] = (Button) findViewById(R.id.frenchzr);
		/* french keybord Special Character */
        mB[712] = (Button) findViewById(R.id.frenchsp1);
        mB[713] = (Button) findViewById(R.id.frenchsp2);
        mB[714] = (Button) findViewById(R.id.frenchsp3);
        mB[715] = (Button) findViewById(R.id.frenchsp4);
        mB[716] = (Button) findViewById(R.id.frenchsp5);
        mB[717] = (Button) findViewById(R.id.frenchsp6);
        mB[718] = (Button) findViewById(R.id.frenchsp7);
        mB[719] = (Button) findViewById(R.id.frenchsp8);
        mB[720] = (Button) findViewById(R.id.frenchsp9);
        mB[721] = (Button) findViewById(R.id.frenchsp10);
        mB[722] = (Button) findViewById(R.id.frenchsp11);
        mB[723] = (Button) findViewById(R.id.frenchsp12);
        mB[724] = (Button) findViewById(R.id.frenchsp13);
        mB[725] = (Button) findViewById(R.id.frenchsp14);
        mB[726] = (Button) findViewById(R.id.frenchsp15);
        mB[727] = (Button) findViewById(R.id.frenchsp16);
        mB[728] = (Button) findViewById(R.id.frenchsp17);
        mB[729] = (Button) findViewById(R.id.frenchsp18);
        mB[730] = (Button) findViewById(R.id.frenchSccST);
        for(int i=648;i<=730;i++){
            mB[i].setTypeface(englishFont);
        }
        // ///***************************************/////
        mB[731] = (Button) findViewById(R.id.urduspx);
        mB[732] = (Button) findViewById(R.id.urspxx);
        mB[733] = (Button) findViewById(R.id.urspxxx);
        mB[734] = (Button) findViewById(R.id.engspx);
        mB[735] = (Button) findViewById(R.id.engspxx);
        mB[736] = (Button) findViewById(R.id.engspxxx);
        mB[737] = (Button) findViewById(R.id.frenchspx);
        mB[738] = (Button) findViewById(R.id.psSpx);
        mB[739] = (Button) findViewById(R.id.psSpxx);
        mB[740] = (Button) findViewById(R.id.psSpxxx);
        mB[741] = (Button) findViewById(R.id.prsspx);
        mB[742] = (Button) findViewById(R.id.prsspxx);
        mB[743] = (Button) findViewById(R.id.prsspxxx);
        mB[744] = (Button) findViewById(R.id.arbspx);
        mB[745] = (Button) findViewById(R.id.arbspxx);
        mB[746] = (Button) findViewById(R.id.arbspxxx);
        mB[747] = (Button) findViewById(R.id.hndspx);
        mB[748] = (Button) findViewById(R.id.hndspxx);
        mB[749] = (Button) findViewById(R.id.hndspxxx);
        mB[750] = (Button) findViewById(R.id.sndhSpx);
        mB[751] = (Button) findViewById(R.id.sndhSpxx);
        mB[752] = (Button) findViewById(R.id.sndhSpxxx);
        mB[753] = (Button) findViewById(R.id.alpSpx);
        mB[754] = (Button) findViewById(R.id.alpSpxx);
        mB[755] = (Button) findViewById(R.id.alpSpxxx);

		/* Second key bord controls */
        mBSpace = (Button) findViewById(R.id.xSpace);
        mBSpace.setSoundEffectsEnabled(false);
        mBSpace.setTypeface(tf2);
        mBSpace2 = (Button) findViewById(R.id.xSpace2);
        mBSpace2.setSoundEffectsEnabled(false);
        mBSpace2.setTypeface(tf2);

        mBdone = (Button) findViewById(R.id.xDone);
        mBdone.setSoundEffectsEnabled(false);
        mBChange = (Button) findViewById(R.id.xChange);
        mBChange.setSoundEffectsEnabled(false);
        mBack = (Button) findViewById(R.id.xBack);
        mBack.setSoundEffectsEnabled(false);

        mBdone2 = (Button) findViewById(R.id.xDone2);
        mBdone2.setSoundEffectsEnabled(false);
        mBChange2 = (Button) findViewById(R.id.xChange2);
        mBChange2.setSoundEffectsEnabled(false);
        mBack2 = (Button) findViewById(R.id.xBack2);
        mBack2.setSoundEffectsEnabled(false);
        mBSpace3 = (Button) findViewById(R.id.xSpaceUrSp);
        mBSpace3.setSoundEffectsEnabled(false);
        mBackUrSC = (Button) findViewById(R.id.xBackursp);
        mBackUrSC.setSoundEffectsEnabled(false);
        spctourdchng = (Button) findViewById(R.id.alpSpcUr);
        spctourdchng.setSoundEffectsEnabled(false);
        //
        urduSpch1 = (Button) findViewById(R.id.alpSp1);
        urduSpch1.setSoundEffectsEnabled(false);
        urduSpch1.setTypeface(tf2, Typeface.BOLD);
        urduSpch1.setTextSize(20);
        //
        urduSpch2 = (Button) findViewById(R.id.alpSp2);
        urduSpch2.setSoundEffectsEnabled(false);
        urduSpch2.setTypeface(tf2, Typeface.BOLD);
        urduSpch2.setTextSize(20);
        //
        urduSpch3 = (Button) findViewById(R.id.alpSpcUr);
        urduSpch3.setSoundEffectsEnabled(false);
        urduSpch3.setTypeface(tf2, Typeface.BOLD);
        urduSpch3.setTextSize(20);
        // xBackursp = (Button) findViewById(R.id.xBackursp);
        xDoneursp = (Button) findViewById(R.id.xDoneursp);
        xDoneursp.setSoundEffectsEnabled(false);
        urTOeng = (Button) findViewById(R.id.eng);
        urTOeng.setSoundEffectsEnabled(false);
        urTOeng2 = (Button) findViewById(R.id.uToeng2);
        urTOeng2.setSoundEffectsEnabled(false);
        urTOeng3 = (Button) findViewById(R.id.engSP3);
        urTOeng3.setSoundEffectsEnabled(false);
        engTour1 = (Button) findViewById(R.id.engtour1);
        engTour1.setSoundEffectsEnabled(false);
        enTour2 = (Button) findViewById(R.id.engtour2);
        enTour2.setSoundEffectsEnabled(false);
        engTour3 = (Button) findViewById(R.id.enToUr3);
        engTour3.setSoundEffectsEnabled(false);
        spCharEng = (Button) findViewById(R.id.xChangeEngtoCap);
        spCharEng.setSoundEffectsEnabled(false);
        engToenSpChar = (Button) findViewById(R.id.engToEnSp);
        engToenSpChar.setSoundEffectsEnabled(false);
        changeEngCTOL = (Button) findViewById(R.id.xChangeEngtoEngl);
        changeEngCTOL.setSoundEffectsEnabled(false);
        engSPCHARtoEng = (Button) findViewById(R.id.enSCtoENG);
        engSPCHARtoEng.setSoundEffectsEnabled(false);
        xDoneEng = (Button) findViewById(R.id.xDoneEng);
        xDoneEng.setSoundEffectsEnabled(false);
        xBackEng = (Button) findViewById(R.id.xBackEng);
        xBackEng.setSoundEffectsEnabled(false);
        xChangeEngtoCap = (Button) findViewById(R.id.xChangeEngtoCap);
        xChangeEngtoCap.setSoundEffectsEnabled(false);
        mSpaceEng = (Button) findViewById(R.id.xSpaceEng);
        mSpaceEng.setSoundEffectsEnabled(false);
        engCAPTOsp = (Button) findViewById(R.id.EnCAPtoSP);
        engCAPTOsp.setSoundEffectsEnabled(false);
        engcTOengl = (Button) findViewById(R.id.xChangeEngtoEngl);
        engcTOengl.setSoundEffectsEnabled(false);
        xbackengc = (Button) findViewById(R.id.xBackEngC);
        xbackengc.setSoundEffectsEnabled(false);
        xDoneEngc = (Button) findViewById(R.id.xDoneEngC);
        xDoneEngc.setSoundEffectsEnabled(false);
        xSpaceEngC = (Button) findViewById(R.id.xSpaceEngC);
        xSpaceEngC.setSoundEffectsEnabled(false);
        xBackEngSc = (Button) findViewById(R.id.xBackengsp);
        xBackEngSc.setSoundEffectsEnabled(false);
        xDoneEsp = (Button) findViewById(R.id.xDoneENGsp);
        // xDoneEsp.setSoundEffectsEnabled(false);
        xSpaceEngsp = (Button) findViewById(R.id.xSpaceENGsp);
        xSpaceEngsp.setSoundEffectsEnabled(false);
		/* pashto keyboard buttons control */
        psToEng = (Button) findViewById(R.id.psToEng);
        psToEng.setSoundEffectsEnabled(false);
        psToEng2 = (Button) findViewById(R.id.psToEng2);
        psToEng2.setSoundEffectsEnabled(false);
        psToEngSc = (Button) findViewById(R.id.psToEngSc);
        psToEngSc.setSoundEffectsEnabled(false);
        psToPsSc = (Button) findViewById(R.id.psSpcPs);
        psToPsSc.setSoundEffectsEnabled(false);

        psPsToSc = (Button) findViewById(R.id.psSp1);
        psPsToSc.setSoundEffectsEnabled(false);
        psPs2ToSc = (Button) findViewById(R.id.psSp2);
        psPs2ToSc.setSoundEffectsEnabled(false);

        psChange = (Button) findViewById(R.id.psChange);
        psChange.setSoundEffectsEnabled(false);
        psChange2 = (Button) findViewById(R.id.psChange2);
        psChange2.setSoundEffectsEnabled(false);

        psSpace = (Button) findViewById(R.id.psSpace);
        psSpace.setSoundEffectsEnabled(false);
        psSpace2 = (Button) findViewById(R.id.psSpace2);
        psSpace2.setSoundEffectsEnabled(false);
        psSpaceSc = (Button) findViewById(R.id.psSpaceSc);
        psSpaceSc.setSoundEffectsEnabled(false);

        psDone = (Button) findViewById(R.id.psDone);
        psDone.setSoundEffectsEnabled(false);
        psDone2 = (Button) findViewById(R.id.psDone2);
        psDone2.setSoundEffectsEnabled(false);
        psDoneSc = (Button) findViewById(R.id.psDoneSc);
        psDoneSc.setSoundEffectsEnabled(false);

        psBack = (Button) findViewById(R.id.psBack);
        psBack.setSoundEffectsEnabled(false);
        psBack2 = (Button) findViewById(R.id.psBack2);
        psBack2.setSoundEffectsEnabled(false);
        psBackSc = (Button) findViewById(R.id.psBackSc);
        psBackSc.setSoundEffectsEnabled(false);
		/* sindhi keyboard control */
        sndhToEng = (Button) findViewById(R.id.sndhToEng);
        sndhToEng.setSoundEffectsEnabled(false);
        sndhToEng2 = (Button) findViewById(R.id.sndhToEng2);
        sndhToEng2.setSoundEffectsEnabled(false);
        sndhToEngSc = (Button) findViewById(R.id.sndhToEngSc);
        sndhToEngSc.setSoundEffectsEnabled(false);
        sndhToSndhSc = (Button) findViewById(R.id.sndhTosndhSc);
        sndhToSndhSc.setSoundEffectsEnabled(false);

        sndhChange = (Button) findViewById(R.id.sndhChange);
        sndhChange.setSoundEffectsEnabled(false);
        sndhChange2 = (Button) findViewById(R.id.sndhChange2);
        sndhChange2.setSoundEffectsEnabled(false);

        sndhPsToSc = (Button) findViewById(R.id.sndhSp1);
        sndhPsToSc.setSoundEffectsEnabled(false);
        sndhPs2ToSc = (Button) findViewById(R.id.sndhSp2);
        sndhPs2ToSc.setSoundEffectsEnabled(false);

        sndhSpace = (Button) findViewById(R.id.sndhSpace);
        sndhSpace.setSoundEffectsEnabled(false);
        sndhSpace2 = (Button) findViewById(R.id.sndhSpace2);
        sndhSpace2.setSoundEffectsEnabled(false);
        sndhSpaceSc = (Button) findViewById(R.id.sndhSpaceSc);
        sndhSpaceSc.setSoundEffectsEnabled(false);

        sndhDone = (Button) findViewById(R.id.sndhDone);
        sndhDone.setSoundEffectsEnabled(false);
        sndhDone2 = (Button) findViewById(R.id.sndhDone2);
        sndhDone2.setSoundEffectsEnabled(false);
        sndhDoneSc = (Button) findViewById(R.id.sndhDoneSc);
        sndhDoneSc.setSoundEffectsEnabled(false);

        sndhBack = (Button) findViewById(R.id.sndhBack);
        sndhBack.setSoundEffectsEnabled(false);
        sndhBack2 = (Button) findViewById(R.id.sndhBack2);
        sndhBack2.setSoundEffectsEnabled(false);
        sndhBackSc = (Button) findViewById(R.id.sndhBackSc);
        sndhBackSc.setSoundEffectsEnabled(false);
		/* Urdu Alphabetic keyboard control */
        urAlpToEng = (Button) findViewById(R.id.alpToEng);
        urAlpToEng.setSoundEffectsEnabled(false);
        urAlpToEng2 = (Button) findViewById(R.id.alpToEng2);
        urAlpToEng2.setSoundEffectsEnabled(false);
        urAlpToEngSc = (Button) findViewById(R.id.alpToEngSc);
        urAlpToEngSc.setSoundEffectsEnabled(false);
        urAlpScToUrAlp = (Button) findViewById(R.id.alpScToAlp);
        urAlpScToUrAlp.setSoundEffectsEnabled(false);

        urAlpChange = (Button) findViewById(R.id.alpChange);
        urAlpChange.setSoundEffectsEnabled(false);
        urAlpChange2 = (Button) findViewById(R.id.alpChange2);
        urAlpChange2.setSoundEffectsEnabled(false);

        urAlpToUrAlpSc = (Button) findViewById(R.id.alpToAlpSc);
        urAlpToUrAlpSc.setSoundEffectsEnabled(false);
        urAlp2ToUrAlpSc = (Button) findViewById(R.id.alp2ToAlpSc);
        urAlp2ToUrAlpSc.setSoundEffectsEnabled(false);

        urAlpSpace = (Button) findViewById(R.id.alpSpace);
        urAlpSpace.setSoundEffectsEnabled(false);
        urAlpSpace2 = (Button) findViewById(R.id.alpSpace2);
        urAlpSpace2.setSoundEffectsEnabled(false);
        urAlpSpaceSc = (Button) findViewById(R.id.alpSpaceSc);
        urAlpSpaceSc.setSoundEffectsEnabled(false);

        urAlpDone = (Button) findViewById(R.id.alpDone);
        urAlpDone.setSoundEffectsEnabled(false);
        urAlpDone2 = (Button) findViewById(R.id.alpDone2);
        urAlpDone2.setSoundEffectsEnabled(false);
        urAlpDoneSc = (Button) findViewById(R.id.alpDoneSc);
        urAlpDoneSc.setSoundEffectsEnabled(false);

        urAlpBack = (Button) findViewById(R.id.alpBack);
        urAlpBack.setSoundEffectsEnabled(false);
        urAlpBack2 = (Button) findViewById(R.id.alpBack2);
        urAlpBack2.setSoundEffectsEnabled(false);
        urAlpBackSc = (Button) findViewById(R.id.alpBackSc);
        urAlpBackSc.setSoundEffectsEnabled(false);
		/* Hindi keyboard control */
        hndToLng = (Button) findViewById(R.id.hndLng);
        hndToLng.setSoundEffectsEnabled(false);
        hndToLng2 = (Button) findViewById(R.id.hndLng2);
        hndToLng2.setSoundEffectsEnabled(false);
        hndToLngSc = (Button) findViewById(R.id.hndLngSc);
        hndToLngSc.setSoundEffectsEnabled(false);
        hndScToHnd = (Button) findViewById(R.id.hndScToHnd);
        hndScToHnd.setSoundEffectsEnabled(false);

        hndToHndSc = (Button) findViewById(R.id.hndToHndSc);
        hndToHndSc.setSoundEffectsEnabled(false);
        hnd2ToHndSc = (Button) findViewById(R.id.hnd2ToHndSc);
        hnd2ToHndSc.setSoundEffectsEnabled(false);

        hndChange = (Button) findViewById(R.id.hndChange);
        hndChange.setSoundEffectsEnabled(false);
        hndChange2 = (Button) findViewById(R.id.hndChange2);
        hndChange2.setSoundEffectsEnabled(false);

        hndSpace = (Button) findViewById(R.id.hndSpace);
        hndSpace.setSoundEffectsEnabled(false);
        hndSpace2 = (Button) findViewById(R.id.hndSpace2);
        hndSpace2.setSoundEffectsEnabled(false);
        hndSpaceSc = (Button) findViewById(R.id.hndSpaceSc);
        hndSpaceSc.setSoundEffectsEnabled(false);

        hndDone = (Button) findViewById(R.id.hndDone);
        hndDone.setSoundEffectsEnabled(false);
        hndDone2 = (Button) findViewById(R.id.hndDone2);
        urAlpDone2.setSoundEffectsEnabled(false);
        hndDoneSc = (Button) findViewById(R.id.hndDoneSc);
        hndDoneSc.setSoundEffectsEnabled(false);

        hndBack = (Button) findViewById(R.id.hndBack);
        hndBack.setSoundEffectsEnabled(false);
        hndBack2 = (Button) findViewById(R.id.hndBack2);
        hndBack2.setSoundEffectsEnabled(false);
        hndBackSc = (Button) findViewById(R.id.hndBackSc);
        hndBackSc.setSoundEffectsEnabled(false);

		/* Persian keyboard control */
        // Typeface font = Typeface.createFromAsset( getAssets(),
        // "fontawesome_webfont.ttf" );
        prsToLng = (Button) findViewById(R.id.prsLng);
        // prsToLng.setTypeface(font);
        prsToLng.setSoundEffectsEnabled(false);
        prsToLng2 = (Button) findViewById(R.id.prsLng2);
        prsToLng2.setSoundEffectsEnabled(false);
        prsToLngSc = (Button) findViewById(R.id.prsLngSc);
        prsToLngSc.setSoundEffectsEnabled(false);
        prsScToPrs = (Button) findViewById(R.id.prsScToPrs);
        prsScToPrs.setSoundEffectsEnabled(false);

        prsToPrsSc = (Button) findViewById(R.id.prsToPrsSc);
        prsToPrsSc.setSoundEffectsEnabled(false);
        prs2ToPrsSc = (Button) findViewById(R.id.prs2ToPrsSc);
        prs2ToPrsSc.setSoundEffectsEnabled(false);

        prsChange = (Button) findViewById(R.id.prsChange);
        prsChange.setSoundEffectsEnabled(false);
        prsChange2 = (Button) findViewById(R.id.prsChange2);
        prsChange2.setSoundEffectsEnabled(false);

        prsSpace = (Button) findViewById(R.id.prsSpace);
        prsSpace.setSoundEffectsEnabled(false);
        prsSpace2 = (Button) findViewById(R.id.prsSpace2);
        prsSpace2.setSoundEffectsEnabled(false);
        prsSpaceSc = (Button) findViewById(R.id.prsSpaceSc);
        prsSpaceSc.setSoundEffectsEnabled(false);

        prsDone = (Button) findViewById(R.id.prsDone);
        prsDone.setSoundEffectsEnabled(false);
        prsDone2 = (Button) findViewById(R.id.prsDone2);
        prsDone2.setSoundEffectsEnabled(false);
        prsDoneSc = (Button) findViewById(R.id.prsDoneSc);
        prsDoneSc.setSoundEffectsEnabled(false);

        prsBack = (Button) findViewById(R.id.prsBack);
        prsBack.setSoundEffectsEnabled(false);
        prsBack2 = (Button) findViewById(R.id.prsBack2);
        prsBack2.setSoundEffectsEnabled(false);
        prsBackSc = (Button) findViewById(R.id.prsBackSc);
        prsBackSc.setSoundEffectsEnabled(false);
		/* Arabic keyboard control */
        arbToLng = (Button) findViewById(R.id.arbLng);
        arbToLng.setSoundEffectsEnabled(false);
        arbToLng2 = (Button) findViewById(R.id.arbLng2);
        arbToLng2.setSoundEffectsEnabled(false);
        arbToLngSc = (Button) findViewById(R.id.arbLngSc);
        arbToLngSc.setSoundEffectsEnabled(false);
        arbScToArb = (Button) findViewById(R.id.arbScToArb);
        arbScToArb.setSoundEffectsEnabled(false);

        arbToArbSc = (Button) findViewById(R.id.arbToArbSc);
        arbToArbSc.setSoundEffectsEnabled(false);

        arb2ToArbSc = (Button) findViewById(R.id.arb2ToArbSc);
        arb2ToArbSc.setSoundEffectsEnabled(false);

        arbChange = (Button) findViewById(R.id.arbChange);
        arbChange.setSoundEffectsEnabled(false);
        arbChange2 = (Button) findViewById(R.id.arbChange2);
        arbChange2.setSoundEffectsEnabled(false);

        arbSpace = (Button) findViewById(R.id.arbSpace);
        arbSpace.setSoundEffectsEnabled(false);
        arbSpace2 = (Button) findViewById(R.id.arbSpace2);
        arbSpace2.setSoundEffectsEnabled(false);
        arbSpaceSc = (Button) findViewById(R.id.arbSpaceSc);
        arbSpaceSc.setSoundEffectsEnabled(false);

        arbDone = (Button) findViewById(R.id.arbDone);
        arbDone.setSoundEffectsEnabled(false);
        arbDone2 = (Button) findViewById(R.id.arbDone2);
        arbDone2.setSoundEffectsEnabled(false);
        arbDoneSc = (Button) findViewById(R.id.arbDoneSc);
        arbDoneSc.setSoundEffectsEnabled(false);

        arbBack = (Button) findViewById(R.id.arbBack);
        arbBack.setSoundEffectsEnabled(false);
        arbBack2 = (Button) findViewById(R.id.arbBack2);
        arbBack2.setSoundEffectsEnabled(false);
        arbBackSc = (Button) findViewById(R.id.arbBackSc);
        arbBackSc.setSoundEffectsEnabled(false);
		/* French keyboard control */
        frenchToLng = (Button) findViewById(R.id.frenchLng);
        frenchToLng.setSoundEffectsEnabled(false);
        frenchToLngC = (Button) findViewById(R.id.frenchLngC);
        frenchToLngC.setSoundEffectsEnabled(false);
        frenchToLngSc = (Button) findViewById(R.id.frenchLngSc);
        frenchToLngSc.setSoundEffectsEnabled(false);
        frenchScTofrench = (Button) findViewById(R.id.frenchScTofrench);
        frenchScTofrench.setSoundEffectsEnabled(false);

        frenchTofrenchSc = (Button) findViewById(R.id.frenchTofrenchSc);
        frenchTofrenchSc.setSoundEffectsEnabled(false);
        frenchCTofrenchSc = (Button) findViewById(R.id.frenchCTofrenchSc);
        frenchCTofrenchSc.setSoundEffectsEnabled(false);

        frenchChange = (Button) findViewById(R.id.frenchChange);
        frenchChange.setSoundEffectsEnabled(false);
        frenchChangeC = (Button) findViewById(R.id.frenchChangeC);
        frenchChangeC.setSoundEffectsEnabled(false);

        frenchSpace = (Button) findViewById(R.id.frenchSpace);
        frenchSpace.setSoundEffectsEnabled(false);
        frenchSpaceC = (Button) findViewById(R.id.frenchSpaceC);
        frenchSpaceC.setSoundEffectsEnabled(false);
        frenchSpaceSc = (Button) findViewById(R.id.frenchSpaceSc);
        frenchSpaceSc.setSoundEffectsEnabled(false);

        frenchDone = (Button) findViewById(R.id.frenchDone);
        frenchDone.setSoundEffectsEnabled(false);
        frenchDoneC = (Button) findViewById(R.id.frenchDoneC);
        frenchDoneC.setSoundEffectsEnabled(false);
        frenchDoneSc = (Button) findViewById(R.id.frenchDoneSc);
        frenchDoneSc.setSoundEffectsEnabled(false);

        frenchBack = (Button) findViewById(R.id.frenchBack);
        frenchBack.setSoundEffectsEnabled(false);
        frenchBackC = (Button) findViewById(R.id.frenchBackC);
        frenchBackC.setSoundEffectsEnabled(false);
        frenchBackSc = (Button) findViewById(R.id.frenchBackSc);
        frenchBackSc.setSoundEffectsEnabled(false);

        for (int i = 0; i < mB.length; i++) {
            mB[i].setSoundEffectsEnabled(false);
            mB[i].setTypeface(tf2, Typeface.BOLD);
            mB[i].setTextSize(20);
            mB[i].setOnTouchListener(this);
        }

        mBSpace.setOnTouchListener(this);
        mBdone.setOnTouchListener(this);
        mBack.setOnTouchListener(this);

        mBChange.setOnClickListener(this);

        mBSpace2.setOnTouchListener(this);
        mBdone2.setOnTouchListener(this);
        mBack2.setOnTouchListener(this);

        mBChange2.setOnClickListener(this);

        mBSpace3.setOnTouchListener(this);
        mBackUrSC.setOnTouchListener(this);

        spctourdchng.setOnClickListener(this);

        urduSpch1.setOnClickListener(this);
        urduSpch2.setOnClickListener(this);
        urduSpch3.setOnClickListener(this);

        urTOeng.setOnClickListener(this);
        urTOeng.setTypeface(tf2, Typeface.BOLD);
        urTOeng.setTextSize(20);

        engTour1.setOnClickListener(this);
        engTour1.setTypeface(tf2, Typeface.BOLD);
        engTour1.setTextSize(20);

        urTOeng3.setOnClickListener(this);
        urTOeng3.setTypeface(tf2, Typeface.BOLD);
        urTOeng3.setTextSize(20);

        urTOeng2.setOnClickListener(this);
        urTOeng2.setTypeface(tf2, Typeface.BOLD);
        urTOeng2.setTextSize(20);

        enTour2.setOnClickListener(this);
        enTour2.setTypeface(tf2, Typeface.BOLD);
        enTour2.setTextSize(20);

        engTour3.setOnClickListener(this);
        engTour3.setTypeface(tf2, Typeface.BOLD);
        engTour3.setTextSize(20);

        spCharEng.setOnClickListener(this);
        engToenSpChar.setOnClickListener(this);
        changeEngCTOL.setOnClickListener(this);
        engSPCHARtoEng.setOnClickListener(this);
        engSPCHARtoEng.setTypeface(tf2, Typeface.BOLD);
        engSPCHARtoEng.setTextSize(20);
        engCAPTOsp.setOnClickListener(this);

        xDoneEng.setOnTouchListener(this);
        xBackEng.setOnTouchListener(this);
        xChangeEngtoCap.setOnClickListener(this);
        mSpaceEng.setOnTouchListener(this);
        engcTOengl.setOnClickListener(this);
        xbackengc.setOnTouchListener(this);
        xDoneEngc.setOnTouchListener(this);
        xSpaceEngC.setOnTouchListener(this);
        xDoneursp.setOnTouchListener(this);
        xBackEngSc.setOnTouchListener(this);
        xDoneEsp.setOnTouchListener(this);
        xSpaceEngsp.setOnTouchListener(this);
		/* pashto keyboard buttons onclick */
        psChange.setOnClickListener(this);
        psChange2.setOnClickListener(this);

        psSpace.setOnTouchListener(this);
        psSpace2.setOnTouchListener(this);
        psSpaceSc.setOnTouchListener(this);

        psDone.setOnTouchListener(this);
        psDone2.setOnTouchListener(this);
        psDoneSc.setOnTouchListener(this);

        psBack.setOnTouchListener(this);
        psBack2.setOnTouchListener(this);
        psBackSc.setOnTouchListener(this);
        // psBack.setOnLongClickListener(this);

        psToEng.setOnClickListener(this);
        psToEng.setTypeface(tf2, Typeface.BOLD);
        psToEng.setTextSize(20);

        psToEng2.setOnClickListener(this);
        psToEng2.setTypeface(tf2, Typeface.BOLD);
        psToEng2.setTextSize(20);

        psToEngSc.setOnClickListener(this);
        psToEngSc.setTypeface(tf2, Typeface.BOLD);
        psToEngSc.setTextSize(20);

        psToPsSc.setOnClickListener(this);
        psToPsSc.setTypeface(tf2, Typeface.BOLD);
        psToPsSc.setTextSize(20);

        psPsToSc.setOnClickListener(this);
        psPsToSc.setTypeface(tf2, Typeface.BOLD);
        psPsToSc.setTextSize(20);

        psPs2ToSc.setOnClickListener(this);
        psPs2ToSc.setTypeface(tf2, Typeface.BOLD);
        psPs2ToSc.setTextSize(20);

		/* sindhi keyboard buttons onclick */
        sndhChange.setOnClickListener(this);
        sndhChange2.setOnClickListener(this);

        sndhPsToSc.setOnClickListener(this);
        sndhPsToSc.setTypeface(tf2, Typeface.BOLD);
        sndhPsToSc.setTextSize(20);

        sndhPs2ToSc.setOnClickListener(this);
        sndhPs2ToSc.setTypeface(tf2, Typeface.BOLD);
        sndhPs2ToSc.setTextSize(20);

        sndhSpace.setOnTouchListener(this);
        sndhSpace2.setOnTouchListener(this);
        sndhSpaceSc.setOnTouchListener(this);

        sndhDone.setOnTouchListener(this);
        sndhDone2.setOnTouchListener(this);
        sndhDoneSc.setOnTouchListener(this);

        sndhBack.setOnTouchListener(this);
        sndhBack2.setOnTouchListener(this);
        sndhBackSc.setOnTouchListener(this);

        sndhToEng.setOnClickListener(this);
        sndhToEng.setTypeface(tf2, Typeface.BOLD);
        sndhToEng.setTextSize(20);

        sndhToEng2.setOnClickListener(this);
        sndhToEng2.setTypeface(tf2, Typeface.BOLD);
        sndhToEng2.setTextSize(20);

        sndhToEngSc.setOnClickListener(this);
        sndhToEngSc.setTypeface(tf2, Typeface.BOLD);
        sndhToEngSc.setTextSize(20);

        sndhToSndhSc.setOnClickListener(this);
        sndhToSndhSc.setTypeface(tf2, Typeface.BOLD);
        sndhToSndhSc.setTextSize(20);
		/* Urdu Alphabetic keyboard buttons onclick */
        urAlpChange.setOnClickListener(this);
        urAlpChange2.setOnClickListener(this);

        urAlpToUrAlpSc.setOnClickListener(this);
        urAlpToUrAlpSc.setTypeface(tf2, Typeface.BOLD);
        urAlpToUrAlpSc.setTextSize(20);

        urAlp2ToUrAlpSc.setOnClickListener(this);
        urAlp2ToUrAlpSc.setTypeface(tf2, Typeface.BOLD);
        urAlp2ToUrAlpSc.setTextSize(20);

        urAlpSpace.setOnTouchListener(this);
        urAlpSpace2.setOnTouchListener(this);
        urAlpSpaceSc.setOnTouchListener(this);

        urAlpDone.setOnTouchListener(this);
        urAlpDone2.setOnTouchListener(this);
        urAlpDoneSc.setOnTouchListener(this);

        urAlpBack.setOnTouchListener(this);
        urAlpBack2.setOnTouchListener(this);
        urAlpBackSc.setOnTouchListener(this);

        urAlpToEng.setOnClickListener(this);
        urAlpToEng.setTypeface(tf2, Typeface.BOLD);
        urAlpToEng.setTextSize(20);

        urAlpToEng2.setOnClickListener(this);
        urAlpToEng2.setTypeface(tf2, Typeface.BOLD);
        urAlpToEng2.setTextSize(20);

        urAlpToEngSc.setOnClickListener(this);
        urAlpToEngSc.setTypeface(tf2, Typeface.BOLD);
        urAlpToEngSc.setTextSize(20);

        urAlpScToUrAlp.setOnClickListener(this);
        urAlpScToUrAlp.setTypeface(tf2, Typeface.BOLD);
        urAlpScToUrAlp.setTextSize(20);
		/* Hindi keyboard buttons onclick */
        hndToLng.setOnClickListener(this);
        hndToLng.setTypeface(tf2, Typeface.BOLD);
        hndToLng.setTextSize(20);

        hndToLng2.setOnClickListener(this);
        hndToLng2.setTypeface(tf2, Typeface.BOLD);
        hndToLng2.setTextSize(20);

        hndToLngSc.setOnClickListener(this);
        hndToLngSc.setTypeface(tf2, Typeface.BOLD);
        hndToLngSc.setTextSize(20);

        hndScToHnd.setOnClickListener(this);
        hndScToHnd.setTypeface(tf2, Typeface.BOLD);
        hndScToHnd.setTextSize(20);

        hndToHndSc.setOnClickListener(this);
        hndToHndSc.setTypeface(tf2, Typeface.BOLD);
        hndToHndSc.setTextSize(20);

        hnd2ToHndSc.setOnClickListener(this);
        hnd2ToHndSc.setTypeface(tf2, Typeface.BOLD);
        hnd2ToHndSc.setTextSize(20);

        hndChange.setOnClickListener(this);
        hndChange2.setOnClickListener(this);

        hndSpace.setOnTouchListener(this);
        hndSpace2.setOnTouchListener(this);
        hndSpaceSc.setOnTouchListener(this);

        hndDone.setOnTouchListener(this);
        hndDone2.setOnTouchListener(this);
        hndDoneSc.setOnTouchListener(this);

        hndBack.setOnTouchListener(this);
        hndBack2.setOnTouchListener(this);
        hndBackSc.setOnTouchListener(this);

		/* Persian keyboard buttons onclick */
        prsToLng.setOnClickListener(this);
        prsToLng.setTypeface(tf2, Typeface.BOLD);
        prsToLng.setTextSize(20);

        prsToLng2.setOnClickListener(this);
        prsToLng2.setTypeface(tf2, Typeface.BOLD);
        prsToLng2.setTextSize(20);

        prsToLngSc.setOnClickListener(this);
        prsToLngSc.setTypeface(tf2, Typeface.BOLD);
        prsToLngSc.setTextSize(20);

        prsScToPrs.setOnClickListener(this);
        prsScToPrs.setTypeface(tf2, Typeface.BOLD);
        prsScToPrs.setTextSize(20);

        prsToPrsSc.setOnClickListener(this);
        prsToPrsSc.setTypeface(tf2, Typeface.BOLD);
        prsToPrsSc.setTextSize(20);

        prs2ToPrsSc.setOnClickListener(this);
        prs2ToPrsSc.setTypeface(tf2, Typeface.BOLD);
        prs2ToPrsSc.setTextSize(20);

        prsChange.setOnClickListener(this);
        prsChange2.setOnClickListener(this);

        prsSpace.setOnTouchListener(this);
        prsSpace2.setOnTouchListener(this);
        prsSpaceSc.setOnTouchListener(this);

        prsDone.setOnTouchListener(this);
        prsDone2.setOnTouchListener(this);
        prsDoneSc.setOnTouchListener(this);

        prsBack.setOnTouchListener(this);
        prsBack2.setOnTouchListener(this);
        prsBackSc.setOnTouchListener(this);
		/* Arabic keyboard buttons onclick */
        arbToLng.setOnClickListener(this);
        arbToLng.setTypeface(tf2, Typeface.BOLD);
        arbToLng.setTextSize(20);

        arbToLng2.setOnClickListener(this);
        arbToLng2.setTypeface(tf2, Typeface.BOLD);
        arbToLng2.setTextSize(20);

        arbToLngSc.setOnClickListener(this);
        arbToLngSc.setTypeface(tf2, Typeface.BOLD);
        arbToLngSc.setTextSize(20);

        arbScToArb.setOnClickListener(this);
        arbScToArb.setTypeface(tf2, Typeface.BOLD);
        arbScToArb.setTextSize(20);

        arbToArbSc.setOnClickListener(this);
        arbToArbSc.setTypeface(tf2, Typeface.BOLD);
        arbToArbSc.setTextSize(20);

        arb2ToArbSc.setOnClickListener(this);
        arb2ToArbSc.setTypeface(tf2, Typeface.BOLD);
        arb2ToArbSc.setTextSize(20);

        arbChange.setOnClickListener(this);
        arbChange2.setOnClickListener(this);

        arbSpace.setOnTouchListener(this);
        arbSpace2.setOnTouchListener(this);
        arbSpaceSc.setOnTouchListener(this);

        arbDone.setOnTouchListener(this);
        arbDone2.setOnTouchListener(this);
        arbDoneSc.setOnTouchListener(this);

        arbBack.setOnTouchListener(this);
        arbBack2.setOnTouchListener(this);
        arbBackSc.setOnTouchListener(this);
		/* French keyboard buttons onclick */
        frenchToLng.setOnClickListener(this);
        frenchToLng.setTypeface(tf2, Typeface.BOLD);
        frenchToLng.setTextSize(20);

        frenchToLngC.setOnClickListener(this);
        frenchToLngC.setTypeface(tf2, Typeface.BOLD);
        frenchToLngC.setTextSize(20);

        frenchToLngSc.setOnClickListener(this);
        frenchToLngSc.setTypeface(tf2, Typeface.BOLD);
        frenchToLngSc.setTextSize(20);

        frenchScTofrench.setOnClickListener(this);
        frenchScTofrench.setTypeface(tf2, Typeface.BOLD);
        frenchScTofrench.setTextSize(20);

        frenchTofrenchSc.setOnClickListener(this);
        frenchTofrenchSc.setTypeface(tf2, Typeface.BOLD);
        frenchTofrenchSc.setTextSize(20);

        frenchCTofrenchSc.setOnClickListener(this);
        frenchCTofrenchSc.setTypeface(tf2, Typeface.BOLD);
        frenchCTofrenchSc.setTextSize(20);

        frenchChange.setOnClickListener(this);
        frenchChangeC.setOnClickListener(this);

        frenchSpace.setOnTouchListener(this);
        frenchSpaceC.setOnTouchListener(this);
        frenchSpaceSc.setOnTouchListener(this);

        frenchDone.setOnTouchListener(this);
        frenchDoneC.setOnTouchListener(this);
        frenchDoneSc.setOnTouchListener(this);

        frenchBack.setOnTouchListener(this);
        frenchBackC.setOnTouchListener(this);
        frenchBackSc.setOnTouchListener(this);

        xChangeEngtoCap.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                check = true;

                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutC.setVisibility(RelativeLayout.VISIBLE);
                return true;
            }
        });
        frenchChange.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                check1 = true;

                mFrenchLayout.setVisibility(RelativeLayout.GONE);
                mFrenchLayoutC.setVisibility(RelativeLayout.VISIBLE);
                return true;
            }
        });

    }

    private void addText(View v) {
        int temp = mEt.getSelectionEnd();
        if (temp >= 0) {
            String b = "";
            b = (String) v.getTag();

            mEt.setText((mEt.getText().toString()
                    .substring(0, mEt.getSelectionEnd()) + b.concat(mEt
                    .getText().toString()
                    .substring(mEt.getSelectionEnd(), mEt.getText().length()))));

            mEt.setSelection(temp + 1);
        }
    }

    private void vibration() {
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(50);
    }

    private void sound() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
        if (loaded) {
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
        }

    }

    @Override
    public void onClick(View v) {
        if (defaultSound == true) {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float vol = (float) 0.5;
            am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
        }
        switch (v.getId()) {

            case R.id.xEt:
                // hideDefaultKeyboard();
                enableKeyboard();
                cursorPosition = mEt.getSelectionStart();
                break;
            case R.id.alpSp1:
                mKLayout.setVisibility(RelativeLayout.GONE);
                mKLayoutSch.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpSp2:
                mKLayout2.setVisibility(RelativeLayout.GONE);
                mKLayoutSch.setVisibility(RelativeLayout.VISIBLE);
                break;

            case R.id.uToeng2:
                selectLanguage(v);
                break;
            case R.id.alpSpcUr:
                mKLayout.setVisibility(RelativeLayout.VISIBLE);
                mKLayoutSch.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.eng:
                selectLanguage(v);
                break;
            case R.id.engSP3:
                selectLanguage(v);
                break;
            case R.id.engtour1:
                selectLanguage(v);
                check = true;
                break;
            case R.id.engtour2:
                selectLanguage(v);
                check = true;
                break;
            case R.id.enToUr3:
                selectLanguage(v);
                check = true;
                break;
            case R.id.xChangeEngtoCap:
                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutC.setVisibility(RelativeLayout.VISIBLE);
                check = false;
                break;

            case R.id.engToEnSp:
                mELayout.setVisibility(RelativeLayout.GONE);
                mELayoutSC.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChangeEngtoEngl:
                mELayout.setVisibility(RelativeLayout.VISIBLE);
                mELayoutC.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.enSCtoENG:
                mELayout.setVisibility(RelativeLayout.VISIBLE);
                mELayoutSC.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.EnCAPtoSP:
                mELayoutC.setVisibility(RelativeLayout.GONE);
                mELayoutSC.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChange:
                mKLayout.setVisibility(RelativeLayout.GONE);
                mKLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.xChange2:
                mKLayout.setVisibility(RelativeLayout.VISIBLE);
                mKLayout2.setVisibility(RelativeLayout.GONE);
                break;
            case R.id.psChange:
                mPsLayout.setVisibility(RelativeLayout.GONE);
                mPsLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psChange2:
                mPsLayout2.setVisibility(RelativeLayout.GONE);
                mPsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psToEng:
                selectLanguage(v);
                break;
            case R.id.psToEng2:
                selectLanguage(v);
                break;
            case R.id.psToEngSc:
                selectLanguage(v);
                break;
            case R.id.psSpcPs:
                mPsLayoutSc.setVisibility(RelativeLayout.GONE);
                mPsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhToEng:
                selectLanguage(v);
                break;
            case R.id.sndhToEng2:
                selectLanguage(v);
                break;
            case R.id.sndhToEngSc:
                selectLanguage(v);
                break;
            case R.id.sndhTosndhSc:
                mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
                mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psSp1:
                mPsLayout.setVisibility(RelativeLayout.GONE);
                mPsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.psSp2:
                mPsLayout2.setVisibility(RelativeLayout.GONE);
                mPsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhChange:
                mSndhLayout.setVisibility(RelativeLayout.GONE);
                mSndhLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhChange2:
                mSndhLayout2.setVisibility(RelativeLayout.GONE);
                mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhSp1:
                mSndhLayout.setVisibility(RelativeLayout.GONE);
                mSndhLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.sndhSp2:
                mSndhLayout2.setVisibility(RelativeLayout.GONE);
                mSndhLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpChange:
                mUrAlpLayout.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpChange2:
                mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpToEng:
                selectLanguage(v);
                break;
            case R.id.alpToEng2:
                selectLanguage(v);
                break;
            case R.id.alpToEngSc:
                selectLanguage(v);
                break;
            case R.id.hndChange:
                mHndLayout.setVisibility(RelativeLayout.GONE);
                mHndLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.hndChange2:
                mHndLayout2.setVisibility(RelativeLayout.GONE);
                mHndLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpScToAlp:
                mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
                mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alpToAlpSc:
                mUrAlpLayout.setVisibility(RelativeLayout.GONE);
                mUrAlpLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.alp2ToAlpSc:
                mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
                mUrAlpLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.hndLng:
                selectLanguage(v);
                break;
            case R.id.hndLng2:
                selectLanguage(v);
                break;
            case R.id.hndLngSc:
                selectLanguage(v);
                break;
            case R.id.hndScToHnd:
                mHndLayoutSc.setVisibility(RelativeLayout.GONE);
                mHndLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.hndToHndSc:
                mHndLayout.setVisibility(RelativeLayout.GONE);
                mHndLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.hnd2ToHndSc:
                mHndLayout2.setVisibility(RelativeLayout.GONE);
                mHndLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.prsLng:
                selectLanguage(v);
                break;
            case R.id.prsLng2:
                selectLanguage(v);
                break;
            case R.id.prsLngSc:
                selectLanguage(v);
                break;
            case R.id.prsScToPrs:
                mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
                mPrsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.prsToPrsSc:
                mPrsLayout.setVisibility(RelativeLayout.GONE);
                mPrsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.prs2ToPrsSc:
                mPrsLayout2.setVisibility(RelativeLayout.GONE);
                mPrsLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.prsChange:
                mPrsLayout.setVisibility(RelativeLayout.GONE);
                mPrsLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.prsChange2:
                mPrsLayout2.setVisibility(RelativeLayout.GONE);
                mPrsLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.arbLng:
                selectLanguage(v);
                break;
            case R.id.arbLng2:
                selectLanguage(v);
                break;
            case R.id.arbLngSc:
                selectLanguage(v);
                break;
            case R.id.arbScToArb:
                mArbLayoutSc.setVisibility(RelativeLayout.GONE);
                mArbLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.arbToArbSc:
                mArbLayout.setVisibility(RelativeLayout.GONE);
                mArbLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.arb2ToArbSc:
                mArbLayout2.setVisibility(RelativeLayout.GONE);
                mArbLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.arbChange:
                mArbLayout.setVisibility(RelativeLayout.GONE);
                mArbLayout2.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.arbChange2:
                mArbLayout2.setVisibility(RelativeLayout.GONE);
                mArbLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.frenchLng:
                check1 = true;
                selectLanguage(v);
                break;
            case R.id.frenchLngC:
                check1 = true;
                selectLanguage(v);
                break;
            case R.id.frenchLngSc:
                check1 = true;
                selectLanguage(v);
                break;
            case R.id.frenchScTofrench:
                mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
                mFrenchLayout.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.frenchTofrenchSc:
                mFrenchLayout.setVisibility(RelativeLayout.GONE);
                mFrenchLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.frenchCTofrenchSc:
                mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
                mFrenchLayoutSc.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.frenchChange:
                check1 = false;
                mFrenchLayout.setVisibility(RelativeLayout.GONE);
                mFrenchLayoutC.setVisibility(RelativeLayout.VISIBLE);
                break;
            case R.id.frenchChangeC:
                mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
                mFrenchLayout.setVisibility(RelativeLayout.VISIBLE);
                break;

        }

    }

    @SuppressWarnings("rawtypes")
    private void selectLanguage(View v) {
        PopupMenu popup = new PopupMenu(keyboardUpdateActivty.this, v);
        popup.getMenuInflater().inflate(R.menu.languages_menu, popup.getMenu());
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass()
                    .getDeclaredMethod("setForceShowIcon", argTypes)
                    .invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.arbKB:
                        arabicKeyboard();
                        break;
                    case R.id.engKB:

                        englishKeyboard();
                        break;
                    case R.id.frenchKB:

                        frenchKeyboard();
                        break;
                    case R.id.hndKB:

                        hindiKeyboard();
                        break;
                    case R.id.psKB:

                        pashtoKeyboard();
                        break;
                    case R.id.prsKB:

                        persianKeyboard();
                        break;
                    case R.id.sndhKB:

                        sindhiKeyboard();
                        break;
                    case R.id.urPhKB:

                        urduPhoneticKeyboard();
                        break;
                    case R.id.urAlpKB:


                        hideKeyboard();
                        urduAlphabeticKeyboard();
                        break;
                    case R.id.defaultkb:
                        CallDefaultKeyBoard();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == mEt && hasFocus == true) {
            isEdit = true;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.psBack:
                mEt.setText("");
                return true;

            default:
                break;
        }
        return true;
    }

    private void urduPhoneticKeyboard() {
        mEt.setTypeface(urduFont);
        lan=7;
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mKLayout.setVisibility(RelativeLayout.VISIBLE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void pashtoKeyboard() {
        lan=4;
        mEt.setTypeface(pashtoFont);
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.VISIBLE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void sindhiKeyboard() {
        mEt.setTypeface(sindhiFont);
        lan=6;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.VISIBLE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void urduAlphabeticKeyboard() {
        mEt.setTypeface(urduFont);
        lan=8;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.VISIBLE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);

    }

    private void hindiKeyboard() {
        lan=3;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.VISIBLE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void persianKeyboard() {
        mEt.setTypeface(persianFont);
        lan=5;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.VISIBLE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void arabicKeyboard() {
        mEt.setTypeface(arabicFont);
        lan=0;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.VISIBLE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void englishKeyboard() {
        mEt.setTypeface(englishFont);
        lan=1;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.VISIBLE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void frenchKeyboard() {
        mEt.setTypeface(frenchFont);
        lan=3;
        mKLayout.setVisibility(RelativeLayout.GONE);
        mKLayout2.setVisibility(RelativeLayout.GONE);
        mKLayoutSch.setVisibility(RelativeLayout.GONE);
        mPsLayout.setVisibility(RelativeLayout.GONE);
        mPsLayout2.setVisibility(RelativeLayout.GONE);
        mPsLayoutSc.setVisibility(RelativeLayout.GONE);
        mSndhLayout.setVisibility(RelativeLayout.GONE);
        mSndhLayout2.setVisibility(RelativeLayout.GONE);
        mSndhLayoutSc.setVisibility(RelativeLayout.GONE);
        mELayout.setVisibility(RelativeLayout.GONE);
        mELayoutC.setVisibility(RelativeLayout.GONE);
        mELayoutSC.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout.setVisibility(RelativeLayout.GONE);
        mUrAlpLayout2.setVisibility(RelativeLayout.GONE);
        mUrAlpLayoutSc.setVisibility(RelativeLayout.GONE);
        mHndLayout.setVisibility(RelativeLayout.GONE);
        mHndLayout2.setVisibility(RelativeLayout.GONE);
        mHndLayoutSc.setVisibility(RelativeLayout.GONE);
        mPrsLayout.setVisibility(RelativeLayout.GONE);
        mPrsLayout2.setVisibility(RelativeLayout.GONE);
        mPrsLayoutSc.setVisibility(RelativeLayout.GONE);
        mArbLayout.setVisibility(RelativeLayout.GONE);
        mArbLayout2.setVisibility(RelativeLayout.GONE);
        mArbLayoutSc.setVisibility(RelativeLayout.GONE);
        mFrenchLayout.setVisibility(RelativeLayout.VISIBLE);
        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
        mFrenchLayoutSc.setVisibility(RelativeLayout.GONE);
    }

    private void keyBoardSetting() {

        final Dialog dialog = new Dialog(keyboardUpdateActivty.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(R.layout.kb_setting_1);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        spinner = (Spinner) dialog.findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        if (!soundState) {
            spinner.setClickable(false);
        } else {
            spinner.setClickable(true);
        }
        List<String> soundList = new ArrayList<String>();
        soundList.add("Default Sound");
        soundList.add("Sound_001");
        soundList.add("Sound_002");
        soundList.add("Sound_003");
        soundList.add("Sound_004");
        soundList.add("Sound_005");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, soundList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(preferences.getInt("spinnerSelection", 0));

        _sound = (CheckBox) dialog.findViewById(R.id.sound_);
        _sound.setChecked(!_sound.isChecked());
        _sound.setChecked(getFromSP("sound"));
        _sound.setOnCheckedChangeListener(this);
        _vib = (CheckBox) dialog.findViewById(R.id.vib_);
        _vib.setChecked(!_vib.isChecked());
        _vib.setChecked(getFromSP1("vibration"));
        _vib.setOnCheckedChangeListener(this);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok_btn);
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = PreferenceManager
                        .getDefaultSharedPreferences(keyboardUpdateActivty.this);
                soundState = getFromSP("key");
                vibState = getFromSP1("key");
                kbSound = preferences.getInt("spinnerSelection", 0);
                if (kbSound == 0) {
                    defaultSound = true;
                }
                loadSound();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private boolean getFromSP(String key) {
        return preferences.getBoolean("key", true);

    }

    private boolean getFromSP1(String key) {
        return preferences.getBoolean("key1", true);
    }

    private boolean getFromSP2(String key) {
        return preferences.getBoolean("alert", false);
    }

    private void saveInSp(String key, boolean value) {
        if (value) {
            spinner.setClickable(true);
        } else {
            spinner.setClickable(false);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("key", value);
        editor.commit();
    }

    private void saveInSp1(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("key1", value);
        editor.commit();
    }

    private void saveInSp2(String string, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("alert", value);
        editor.commit();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sound_:
                saveInSp("sound", isChecked);
                break;
            case R.id.vib_:
                saveInSp1("vibration", isChecked);
                break;
            case R.id.alert_backspace:
                saveInSp2("alert", isChecked);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        int item = spinner.getSelectedItemPosition();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("spinnerSelection", item);
        editor.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // if (defaultSound == true) {
                // AudioManager am = (AudioManager)
                // getSystemService(Context.AUDIO_SERVICE);
                // float vol = (float) 0.5;
                // am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
                // }
                v.setPressed(true);

                if (soundState) {
                    if (defaultSound == true) {
                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        float vol = (float) 0.5;
                        am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
                    } else {
                        sound();
                    }
                }
                if (vibState) {
                    vibration();
                }

                // BackSpace Buttons of all Keyboards
                if (v.getId() == R.id.xBack || v.getId() == R.id.xBack2
                        || v.getId() == R.id.xBackursp
                        || v.getId() == R.id.xBackEng
                        || v.getId() == R.id.xBackEngC
                        || v.getId() == R.id.xBackengsp || v.getId() == R.id.psBack
                        || v.getId() == R.id.psBack2 || v.getId() == R.id.psBackSc

                        || v.getId() == R.id.sndhBack
                        || v.getId() == R.id.sndhBack2
                        || v.getId() == R.id.sndhBackSc
                        || v.getId() == R.id.alpBack || v.getId() == R.id.alpBack2
                        || v.getId() == R.id.alpBackSc || v.getId() == R.id.hndBack
                        || v.getId() == R.id.hndBack2
                        || v.getId() == R.id.hndBackSc || v.getId() == R.id.prsBack
                        || v.getId() == R.id.prsBack2
                        || v.getId() == R.id.prsBackSc || v.getId() == R.id.arbBack
                        || v.getId() == R.id.arbBack2
                        || v.getId() == R.id.arbBackSc
                        || v.getId() == R.id.frenchBack
                        || v.getId() == R.id.frenchBackC
                        || v.getId() == R.id.frenchBackSc) {
                    isdeleteChar(v);

                    final int interval = 2000;
                    handler = new Handler();
                    runnable = new Runnable() {
                        public void run() {
                            mTimer.cancel();
                            alertstate = getFromSP2("alert");
                            if (alertstate == false) {
                                final Dialog myDialog = new Dialog(
                                        keyboardUpdateActivty.this);
                                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                myDialog.setCanceledOnTouchOutside(false);
                                myDialog.setContentView(R.layout.backspace_alert);
                                ((ViewGroup) myDialog.getWindow().getDecorView())
                                        .getChildAt(0).startAnimation(
                                        AnimationUtils.loadAnimation(
                                                keyboardUpdateActivty.this,
                                                R.anim.shake));

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(myDialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                _alerthide = (CheckBox) myDialog
                                        .findViewById(R.id.alert_backspace);
                                _alerthide.setChecked(getFromSP2("alert"));
                                Button okey = (Button) myDialog
                                        .findViewById(R.id.alert_btnokey);
                                Button cancel = (Button) myDialog
                                        .findViewById(R.id.alert_btncancle);
                                okey.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mEt.setText("");
                                        myDialog.cancel();
                                    }
                                });
                                cancel.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        myDialog.cancel();
                                    }
                                });
                                _alerthide
                                        .setOnCheckedChangeListener(keyboardUpdateActivty.this);
                                myDialog.show();
                                myDialog.getWindow().setAttributes(lp);
                            } else {
                                mEt.setText("");
                            }
                        }
                    };
                    handler.postAtTime(runnable, System.currentTimeMillis()
                            + interval);
                    handler.postDelayed(runnable, interval);
                }
                // Space and End Buttons of all Keyboards
                else if (v.getId() == R.id.xDone || v.getId() == R.id.xDone2
                        || v.getId() == R.id.xDoneursp
                        || v.getId() == R.id.xDoneEng
                        || v.getId() == R.id.xDoneEngC
                        || v.getId() == R.id.xDoneENGsp || v.getId() == R.id.psDone
                        || v.getId() == R.id.psDone2 || v.getId() == R.id.psDoneSc
                        || v.getId() == R.id.xSpace || v.getId() == R.id.xSpace2
                        || v.getId() == R.id.xSpaceUrSp
                        || v.getId() == R.id.xSpaceEng
                        || v.getId() == R.id.xSpaceEngC
                        || v.getId() == R.id.xSpaceENGsp
                        || v.getId() == R.id.psSpace || v.getId() == R.id.psSpace2
                        || v.getId() == R.id.psSpaceSc
                        || v.getId() == R.id.sndhSpace
                        || v.getId() == R.id.sndhSpace2
                        || v.getId() == R.id.sndhSpaceSc
                        || v.getId() == R.id.sndhDone
                        || v.getId() == R.id.sndhDone2
                        || v.getId() == R.id.sndhDoneSc
                        || v.getId() == R.id.alpSpace
                        || v.getId() == R.id.alpSpace2
                        || v.getId() == R.id.alpSpaceSc
                        || v.getId() == R.id.alpDone || v.getId() == R.id.alpDone2
                        || v.getId() == R.id.alpDoneSc
                        || v.getId() == R.id.hndSpace
                        || v.getId() == R.id.hndSpace2
                        || v.getId() == R.id.hndSpaceSc
                        || v.getId() == R.id.hndDone || v.getId() == R.id.hndDone2
                        || v.getId() == R.id.hndDoneSc
                        || v.getId() == R.id.prsSpace
                        || v.getId() == R.id.prsSpace2
                        || v.getId() == R.id.prsSpaceSc
                        || v.getId() == R.id.prsDone || v.getId() == R.id.prsDone2
                        || v.getId() == R.id.prsDoneSc
                        || v.getId() == R.id.arbSpace
                        || v.getId() == R.id.arbSpace2
                        || v.getId() == R.id.arbSpaceSc
                        || v.getId() == R.id.arbDone || v.getId() == R.id.arbDone2
                        || v.getId() == R.id.arbDoneSc
                        || v.getId() == R.id.frenchSpace
                        || v.getId() == R.id.frenchSpaceC
                        || v.getId() == R.id.frenchSpaceSc
                        || v.getId() == R.id.frenchDone
                        || v.getId() == R.id.frenchDoneC
                        || v.getId() == R.id.frenchDoneSc) {
                    addText(v);

                }
                // else if(v.getId()==R.id.uToeng2){
                //
                // }
                // All Text Buttons Special Case
                else {

                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        showPopupWindow(v);
                        popupWindow.showAtLocation(keyboardPopup,
                                Gravity.NO_GRAVITY, location.left - 25,
                                location.top - v.getHeight());
                    } else {
                        showPopupWindow(v);
                        popupWindow.showAtLocation(keyboardPopup,
                                Gravity.NO_GRAVITY, location.left - 25,
                                location.top - v.getHeight());
                    }

                }
                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                v.setPressed(false);
                if (v != mBdone && v != mBack && v != psBack && v != xbackengc
                        && v != mBChange && v != mNum && v != mBdone2
                        && v != xDoneursp && v != mBack2 && v != mBChange2
                        && v != xChangeEngtoCap && v != xBackEng && v != xDoneEng
                        && v != engcTOengl && v != mBackUrSC && v != xBackEngSc
                        && v != xDoneEngc) {

                    if (check == false) {
                        mELayoutC.setVisibility(RelativeLayout.GONE);
                        mELayout.setVisibility(RelativeLayout.VISIBLE);
                    } else if (check1 == false) {
                        mFrenchLayoutC.setVisibility(RelativeLayout.GONE);
                        mFrenchLayout.setVisibility(RelativeLayout.VISIBLE);
                    }
                }
                if (v.getId() == R.id.xDone || v.getId() == R.id.xDone2
                        || v.getId() == R.id.xDoneursp
                        || v.getId() == R.id.xDoneEng
                        || v.getId() == R.id.xDoneEngC
                        || v.getId() == R.id.xDoneENGsp || v.getId() == R.id.psDone
                        || v.getId() == R.id.psDone2 || v.getId() == R.id.psDoneSc
                        || v.getId() == R.id.xSpace || v.getId() == R.id.xSpace2
                        || v.getId() == R.id.xSpaceUrSp
                        || v.getId() == R.id.xSpaceEng
                        || v.getId() == R.id.xSpaceEngC
                        || v.getId() == R.id.xSpaceENGsp
                        || v.getId() == R.id.psSpace || v.getId() == R.id.psSpace2
                        || v.getId() == R.id.psSpaceSc
                        || v.getId() == R.id.sndhSpace
                        || v.getId() == R.id.sndhSpace2
                        || v.getId() == R.id.sndhSpaceSc
                        || v.getId() == R.id.sndhDone
                        || v.getId() == R.id.sndhDone2
                        || v.getId() == R.id.sndhDoneSc
                        || v.getId() == R.id.alpSpace
                        || v.getId() == R.id.alpSpace2
                        || v.getId() == R.id.alpSpaceSc
                        || v.getId() == R.id.alpDone || v.getId() == R.id.alpDone2
                        || v.getId() == R.id.alpDoneSc
                        || v.getId() == R.id.hndSpace
                        || v.getId() == R.id.hndSpace2
                        || v.getId() == R.id.hndSpaceSc
                        || v.getId() == R.id.hndDone || v.getId() == R.id.hndDone2
                        || v.getId() == R.id.hndDoneSc
                        || v.getId() == R.id.prsDoneSc
                        || v.getId() == R.id.prsSpace
                        || v.getId() == R.id.prsSpace2
                        || v.getId() == R.id.prsSpaceSc
                        || v.getId() == R.id.prsDone || v.getId() == R.id.prsDone2
                        || v.getId() == R.id.prsDoneSc
                        || v.getId() == R.id.arbSpace
                        || v.getId() == R.id.arbSpace2
                        || v.getId() == R.id.arbSpaceSc
                        || v.getId() == R.id.arbDone || v.getId() == R.id.arbDone2
                        || v.getId() == R.id.arbDoneSc
                        || v.getId() == R.id.frenchSpace
                        || v.getId() == R.id.frenchSpaceC
                        || v.getId() == R.id.frenchSpaceSc
                        || v.getId() == R.id.frenchDone
                        || v.getId() == R.id.frenchDoneC
                        || v.getId() == R.id.frenchDoneSc) {
                    // Done Nothing
                }
                // BackSpace Buttons
                else if (v.getId() == R.id.xBack || v.getId() == R.id.xBack2
                        || v.getId() == R.id.xBackursp
                        || v.getId() == R.id.xBackEng
                        || v.getId() == R.id.xBackEngC
                        || v.getId() == R.id.xBackengsp || v.getId() == R.id.psBack
                        || v.getId() == R.id.psBack2 || v.getId() == R.id.psBackSc

                        || v.getId() == R.id.sndhBack
                        || v.getId() == R.id.sndhBack2
                        || v.getId() == R.id.sndhBackSc
                        || v.getId() == R.id.alpBack || v.getId() == R.id.alpBack2
                        || v.getId() == R.id.alpBackSc || v.getId() == R.id.hndBack
                        || v.getId() == R.id.hndBack2
                        || v.getId() == R.id.hndBackSc || v.getId() == R.id.prsBack
                        || v.getId() == R.id.prsBack2
                        || v.getId() == R.id.prsBackSc || v.getId() == R.id.arbBack
                        || v.getId() == R.id.arbBack2
                        || v.getId() == R.id.arbBackSc
                        || v.getId() == R.id.frenchBack
                        || v.getId() == R.id.frenchBackC
                        || v.getId() == R.id.frenchBackSc) {
                    mTimer.cancel();
                    handler.removeCallbacks(runnable);
                } else {
                    popupWindow.dismiss();
                    addText(v);
                }
                return true;
            }
        }
        return false;
    }

    protected void showPopupWindow(View v) {
        final Button pressbutton = (Button) v;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        keyboardPopup = inflater.inflate(R.layout.keyboardpop, null);
        popupWindow = new PopupWindow(keyboardPopup, v.getWidth() + 50,
                v.getHeight() + 30);
        TextView keyboardKey = (TextView) keyboardPopup
                .findViewById(R.id.popuptv);
        keyboardKey.setText(pressbutton.getText().toString());

        keyLocation = new int[2];
        pressbutton.getLocationOnScreen(keyLocation);
        location = new Rect();

        location.left = keyLocation[0];
        location.top = keyLocation[1];

        location.right = location.left + pressbutton.getWidth();
        location.bottom = location.top + pressbutton.getHeight();
    }

    protected void CallDefaultKeyBoard() {
        final Dialog def_kbdialog = new Dialog(keyboardUpdateActivty.this);
        def_kbdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        def_kbdialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        def_kbdialog.setCanceledOnTouchOutside(false);

        def_kbdialog.setContentView(R.layout.default_keyboard_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(def_kbdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Button okey = (Button) def_kbdialog.findViewById(R.id.dialog_done);
        Button cancel = (Button) def_kbdialog.findViewById(R.id.dialoge_cancel);
        ImageView finish = (ImageView) def_kbdialog
                .findViewById(R.id.btn_hidekeyboard);
        message = (EditText) def_kbdialog.findViewById(R.id.et_dialouge);
        okey.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // editTextMainScreen.append(message.getText().toString());
                // message.setText("");
                // myDialog.cancel();
                // Main_Activity.flag = true;
                // Main_Activity.getInstance().tvClass = Main_Activity
                // .getInstance().new TextViewClass();
                // int i = Main_Activity.counter + 1;
                // Main_Activity.counter = i;
                // Util.IMG2 = i;
                // Util.text = " "
                // + KeyboardActivity.this.edText.getText().toString();
                // Main_Activity.getInstance().tvClass.text = " "
                // + edText.getText().toString();
                // Main_Activity.getInstance().textBitmapImage(
                // Main_Activity.getInstance().getBitmapFromView(
                // Main_Activity.getInstance().tv), 3,
                // Main_Activity.getInstance().tvClass.text, Util.IMG2,
                // Main_Activity.getInstance().tvClass);
                // finish();
                Toast.makeText(getApplicationContext(), "Temprary Disable",
                        Toast.LENGTH_SHORT).show();
                def_kbdialog.cancel();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                def_kbdialog.cancel();
                finish();
            }
        });
        finish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mEt.append(message.getText().toString());
                message.setText("");
                def_kbdialog.cancel();
            }
        });

        def_kbdialog.show();
        def_kbdialog.getWindow().setAttributes(lp);
        message.append(mEt.getText().toString());
        mEt.setText("");
        def_kbdialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}