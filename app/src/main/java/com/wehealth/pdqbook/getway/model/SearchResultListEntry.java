package com.wehealth.pdqbook.getway.model;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2017/3/16.
 */

public class SearchResultListEntry {
    private String CategoryName;
    private ArrayList<SearchResultInfoEntry> List;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public ArrayList<SearchResultInfoEntry> getList() {
        return List;
    }

    public void setList(ArrayList<SearchResultInfoEntry> list) {
        List = list;
    }
}
