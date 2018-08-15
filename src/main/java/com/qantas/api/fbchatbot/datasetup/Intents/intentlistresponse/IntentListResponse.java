package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IntentListResponse {

    private String id;
    private String name;
    private long priority;
    private boolean fallbackIntent;

    private ArrayList<String> actions;
    private ArrayList<String> contextIn;

    private ArrayList<Events> events;
    private ArrayList<ContextOut> contextOut;
    private ArrayList<Parameters> parameters;

    /*@Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", priority = "+priority+", events = "+events+", name = "+name+", contextOut = "+contextOut+", parameters = "+parameters+", fallbackIntent = "+fallbackIntent+", actions = "+actions+", contextIn = "+contextIn+"]";
    }*/

}
