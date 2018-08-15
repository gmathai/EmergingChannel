package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

import java.util.List;

@Data
public class IntentListResponseFinal {

    private int intentListCount;

    private List<String> id;
    private List<String> name;
    private List<String> parentId;
    private List<Long> priority;
    private List<Boolean> fallbackIntent;
    private List<String> actions;
    private List<List<String>> contextIn;
    private EventsFinal events;
    private ContextOutFinal contextOut;
    private ParametersFinal parameters;

}
