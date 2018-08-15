package com.qantas.api.fbchatbot.datasetup.Intents.intentlistresponse;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Parameters {

    private String id;

    private boolean isList;

    private String dataType;

    private String name;

    private String value;

    private boolean required;

    private ArrayList<String> prompts;


    /*@Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", isList = "+isList+", dataType = "+dataType+", name = "+name+", value = "+value+", required = "+required+"]";
    }*/
}
