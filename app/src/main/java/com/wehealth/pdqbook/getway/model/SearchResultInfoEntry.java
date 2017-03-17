package com.wehealth.pdqbook.getway.model;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2017/3/16.
 */

public class SearchResultInfoEntry {
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContentEN() {
        return ContentEN;
    }

    public void setContentEN(String contentEN) {
        ContentEN = contentEN;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionEN() {
        return DescriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        DescriptionEN = descriptionEN;
    }

    public String getReadName() {
        return ReadName;
    }

    public void setReadName(String readName) {
        ReadName = readName;
    }

    public String getReadPath() {
        return ReadPath;
    }

    public void setReadPath(String readPath) {
        ReadPath = readPath;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitleEN() {
        return TitleEN;
    }

    public void setTitleEN(String titleEN) {
        TitleEN = titleEN;
    }

    public String getTitleZm() {
        return TitleZm;
    }

    public void setTitleZm(String titleZm) {
        TitleZm = titleZm;
    }

    public String getTstamp() {
        return Tstamp;
    }

    public void setTstamp(String tstamp) {
        Tstamp = tstamp;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTypeCN() {
        return TypeCN;
    }

    public void setTypeCN(String typeCN) {
        TypeCN = typeCN;
    }

    public ArrayList<com.wehealth.pdqbook.getway.model.DescList> getDescList() {
        return DescList;
    }

    public void setDescList(ArrayList<com.wehealth.pdqbook.getway.model.DescList> descList) {
        DescList = descList;
    }

    private String Content;
    private String ContentEN;
    private String Description;
    private String DescriptionEN;
    private String ReadName;
    private String ReadPath;
    private String Sid;
    private String Title;
    private String TitleEN;
    private String TitleZm;
    private String Tstamp;
    private String Type;
    private String TypeCN;

    private ArrayList<DescList> DescList;
}
