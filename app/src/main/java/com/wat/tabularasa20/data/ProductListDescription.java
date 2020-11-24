package com.wat.tabularasa20.data;

public class ProductListDescription {

    public String name;
    public String description;
    public boolean favourite;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String name, boolean favoutite, String description) {
        this.name = name;
        this.favourite = favoutite;
        this.description = description;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, boolean favoutite) {
        this.name = name;
        this.favourite = favoutite;
        this.description = null;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name) {
        this.name = name;
        this.favourite = false;
        this.description = null;
    }
}
