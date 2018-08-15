package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

import java.util.List;

@Data
public class ContextOutFinal {

    private List<String> name;

    private List<String> parameters;

    private List<Integer> lifespan;

    /*@Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", parameters = "+parameters+", lifespan = "+lifespan+"]";
    }*/
}
