package com.wat.tabularasa20.data;

public class ProductListDescription {

    /**
     * Możliwy stan przycisku ulubionych
     */
    public enum FavouriteStare { ON, OFF, HIDDEN }

    public String name;
    public String description;
    public FavouriteStare favourite;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String name, FavouriteStare favoutite, String description) {
        this.name = name;
        this.favourite = favoutite;
        this.description = description;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, FavouriteStare favoutite) {
        this.name = name;
        this.favourite = favoutite;
        this.description = "";
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name) {
        this.name = name;
        this.favourite = FavouriteStare.OFF;
        this.description = "";
    }
}
