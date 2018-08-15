package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.ArrayList;

@lombok.Data
public class ResponsesParameters {

    private String id;

    private boolean isList;

    private String dataType;

    private String name;

    private String value;

    private boolean required;

    private ArrayList<String> prompts;


}
