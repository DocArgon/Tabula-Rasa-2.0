package com.wat.tabularasa20.data;

import android.graphics.Color;

public class ProductListDescription {

    /**
     * Możliwy stan przycisku ulubionych
     */
    public enum FavouriteStare { ON, OFF, HIDDEN }

    /**
     * Domyślna wartość identyfikatora produktu
     */
    public static final int DEFAULT_PRODUCT_ID = -1;
    public static final int DEFAULT_OWNER_ID = -1;

    public int productID = DEFAULT_PRODUCT_ID;
    public int ownerID = DEFAULT_OWNER_ID;
    public String title = "";
    public String description = "";
    public String nick = "";
    public String city = "";
    public String author = "";
    public String helper = "";
    public FavouriteStare favourite = FavouriteStare.HIDDEN;
    public int background = Color.TRANSPARENT;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String title, int productID, FavouriteStare favoutite, String description, String nick, String city, String author, int ownerID, String helper) {
        this.title = title;
        this.productID = productID;
        this.favourite = favoutite;
        this.description = description;
        this.nick = nick;
        this.city = city;
        this.author = author;
        this.ownerID = ownerID;
        this.helper = helper;
    }

    /**
     * Konstruktor ulubionych
     */
    public ProductListDescription (String title, int productID, FavouriteStare favoutite) {
        this.title = title;
        this.productID = productID;
        this.favourite = favoutite;
    }

    /**
     * Konstruktor wiadomości
     */
    public ProductListDescription (String title, String helper) {
        if (title != null)
            this.title = title;
        if (helper != null)
            this.helper = helper;

        if (this.title.isEmpty())
            background = Color.argb(120, 0, 200, 0);
        else
            background = Color.argb(120, 0, 0, 200);
    }
}
