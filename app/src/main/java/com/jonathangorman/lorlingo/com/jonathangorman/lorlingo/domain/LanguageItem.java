package com.jonathangorman.lorlingo.com.jonathangorman.lorlingo.domain;

import java.util.Locale;

public class LanguageItem {

    private Integer imageId;
    private String displayText;
    private String nameId;
    private boolean ttsAvailable;
    private Locale locale;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public boolean isTtsAvailable() {
        return ttsAvailable;
    }

    public void setTtsAvailable(boolean ttsAvailable) {
        this.ttsAvailable = ttsAvailable;
    }


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
