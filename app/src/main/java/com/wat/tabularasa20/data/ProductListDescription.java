package com.wat.tabularasa20.data;

public class ProductListDescription {

    public String desctiption;
    public boolean favourite;

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String desctiption) {
        this.desctiption = desctiption;
        this.favourite = false;
    }

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String desctiption, boolean favoutite) {
        this.desctiption = desctiption;
        this.favourite = favoutite;
    }
}
