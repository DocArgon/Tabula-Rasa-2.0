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
    public String nick;
    public String city;
    public FavouriteStare favourite;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String name, int productID, FavouriteStare favoutite, String description, String nick, String city) {
        this.name = name;
        this.productID = productID;
        this.favourite = favoutite;
        this.description = description;
        this.nick = nick;
        this.city = city;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, int productID, FavouriteStare favoutite) {
        this.name = name;
        this.productID = productID;
        this.favourite = favoutite;
        this.description = "";
        this.nick = "";
        this.city = "";
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String name, int productID) {
        this.name = name;
        this.productID = productID;
        this.favourite = FavouriteStare.HIDDEN;
        this.description = "";
        this.nick = "";
        this.city = "";
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
