package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.List;

@lombok.Data
public class ResponsesMessagesFinal {

    private List<Integer> type;
    private List<ResponsesMessagesPayloadMessages> responsesMessagesPayloadMessages;
    private List<String> lang;

//    private String type;

}
