package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.List;

@lombok.Data
public class ResponsesMessagesPayloadMessages {

    private String platform; //
    private Integer type; //
    private String speech; //
    private String title; //
    private String senderaction; //

    private List<String> speechArray; //
//    private List<String> subtitle;
//    private List<String> imageURL;


    private List<ResponsesMessagesPayloadMessagesButtons> buttons; //
    private List<ResponsesMessagesPayloadMessagesCarousalItems> items; //
    private List<ResponsesMessagesPayloadMessagesReplies> replies; //


}
