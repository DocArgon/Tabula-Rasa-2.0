package com.wat.tabularasa20.data;

public class ProductListDescription {

    public String desctiption;
    public boolean favourite;

    public ProductListDescription (String desctiption) {
        this.desctiption = desctiption;
        this.favourite = false;
    }

    public ProductListDescription (String desctiption, boolean favoutite) {
        this.desctiption = desctiption;
        this.favourite = favoutite;
    }
}
