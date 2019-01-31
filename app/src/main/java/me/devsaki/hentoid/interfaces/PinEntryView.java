package me.devsaki.hentoid.interfaces;

import android.support.annotation.StringRes;

public interface PinEntryView {
    void clearPin();
    void setText(@StringRes int id);
}
