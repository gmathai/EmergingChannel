package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

@Data
public class Events {

    private String name;

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+"]";
    }
}
