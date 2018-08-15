package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.ArrayList;

@lombok.Data
public class UserSays {

    private String id;

    private int count;

    private int updated;

    private ArrayList<Data> data;

    private boolean isTemplate;

    private boolean isAuto;

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", count = "+count+", updated = "+updated+", data = "+data+", isTemplate = "+isTemplate+", isAuto = "+isAuto+"]";
    }
}
