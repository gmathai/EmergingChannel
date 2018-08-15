package com.qantas.api.fbchatbot.datasetup.Entity.entitydetail;

@lombok.Data
public class EntityDetail2 {

    private String id;
    private boolean isOverridable;
    private String name;
    private boolean isEnum;
    private boolean automatedExpansion;

    private Entries2 entries;


}
