package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.ArrayList;

@lombok.Data
public class IntentDetailResponse {

    private String id;
    private String name;
    private boolean auto;
    private boolean fallbackIntent;
    private boolean webhookUsed;
    private long lastUpdate;
    private boolean webhookForSlotFilling;
    private long priority;

    private ArrayList<UserSays> userSays;

    private String[] templates; /// No data as of now
    private String[] events; /// No data as of now
    private String[] followUpIntents; /// No data as of now
    private String[] contexts; /// No data as of now

    private ArrayList<Responses> responses;

    /*@Override
    public String toString()
    {
        return "ClassPojo [templates = "+templates+", lastUpdate = "+lastUpdate+", events = "+events+", webhookForSlotFilling = "+webhookForSlotFilling+", responses = "+responses+", id = "+id+", priority = "+priority+", name = "+name+", followUpIntents = "+followUpIntents+", auto = "+auto+", contexts = "+contexts+", webhookUsed = "+webhookUsed+", userSays = "+userSays+", fallbackIntent = "+fallbackIntent+"]";
    }*/
}
