package com.multilanguage.notes.notepad.model;

import java.io.Serializable;

/**
 * Created by UDNA on 8/4/2016.
 */
public class MemoInfo  {
    String title;
    String memo_text;
    String memo_time;

    public int getMemo_language() {
        return memo_language;
    }

    public void setMemo_language(int memo_language) {
        this.memo_language = memo_language;
    }

    public String getMemo_time() {
        return memo_time;
    }

    public void setMemo_time(String memo_time) {
        this.memo_time = memo_time;
    }

    int memo_language;


    public int getMemo_id() {
        return memo_id;
    }

    public void setMemo_id(int memo_id) {
        this.memo_id = memo_id;
    }

    int memo_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo_text() {
        return memo_text;
    }

    public void setMemo_text(String memo_text) {
        this.memo_text = memo_text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
}
