package com.sqindia.avcdemo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ramya on 04-02-2017.
 */
public class ItemDetailsWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> itemDetails;

    public ItemDetailsWrapper(ArrayList<String> items) {
        this.itemDetails = items;
    }

    public ArrayList<String> getItemDetails() {
        return itemDetails;
    }
}
