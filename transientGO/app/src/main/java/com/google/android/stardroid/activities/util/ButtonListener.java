package com.google.android.stardroid.activities.util;

import com.google.android.stardroid.renderer.util.SearchHelper;

import java.util.EventObject;

/**
 * Created by landon on 12/3/16.
 */

public class ButtonListener extends EventObject {

    private SearchHelper s;

    public ButtonListener(Object source, SearchHelper sH) {
        super(source);
        s = sH;
    }

    public boolean searcher(){
        return s.INSTANCE.targetInFocusRadius();
    }
}
