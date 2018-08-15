package com.qantas.api.fbchatbot.datasetup.Entity.entitydetail;

import java.util.ArrayList;

@lombok.Data
public class EntityDetail {

    private String id;
    private boolean isOverridable;
    private String name;
    private boolean isEnum;
    private ArrayList<Entries> entries;
    private boolean automatedExpansion;


}
