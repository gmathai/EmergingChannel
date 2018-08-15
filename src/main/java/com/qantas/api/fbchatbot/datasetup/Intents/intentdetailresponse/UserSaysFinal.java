package com.qantas.api.fbchatbot.datasetup.Intents.intentdetailresponse;

import java.util.List;

@lombok.Data
public class UserSaysFinal {

//    private int userSaysCount;
    private List<String> id;
    private List<Boolean> isTemplate;
    private List<Integer> count;
    private List<Integer> updated;
    private List<Boolean> isAuto;

    private DataFinal data;

}
