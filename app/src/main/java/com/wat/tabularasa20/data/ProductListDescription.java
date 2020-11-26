package com.wat.tabularasa20.data;

public class ProductListDescription {

    /**
     * Możliwy stan przycisku ulubionych
     */
    public enum FavouriteStare { ON, OFF, HIDDEN }

    /**
     * Domyślna wartość identyfikatora produktu
     */
    public static final int DEFAULT_PRODUCY_ID = -1;

    public int productID;
    public String name;
    public String description;
    public FavouriteStare favourite;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String name, int productID, FavouriteStare favoutite, String description) {
        this.name = name;
        this.productID = productID;
        this.favourite = favoutite;
        this.description = description;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, int productID, FavouriteStare favoutite) {
        this.name = name;
        this.productID = productID;

        this.favourite = favoutite;
        this.description = "";
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, int productID) {
        this.name = name;
        this.productID = productID;

        this.favourite = FavouriteStare.HIDDEN;
        this.description = "";
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name) {
        this.name = name;

        this.productID = DEFAULT_PRODUCY_ID;
        this.favourite = FavouriteStare.HIDDEN;
        this.description = "";
    }
}
