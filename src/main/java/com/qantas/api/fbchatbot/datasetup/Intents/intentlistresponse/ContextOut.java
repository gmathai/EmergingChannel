package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

@Data
public class ContextOut {

    private String name;

    private String parameters;

    private int lifespan;

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", parameters = "+parameters+", lifespan = "+lifespan+"]";
    }
}
