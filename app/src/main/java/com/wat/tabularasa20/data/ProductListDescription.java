package com.wat.tabularasa20.data;

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
    public FavouriteStare favourite = FavouriteStare.HIDDEN;

    /**
     * Konstruktor struktury przechowującej opis książki
     */
    public ProductListDescription (String title, int productID, FavouriteStare favoutite, String description, String nick, String city, String author, int ownerID) {
        this.title = title;
        this.productID = productID;
        this.favourite = favoutite;
        this.description = description;
        this.nick = nick;
        this.city = city;
        this.author = author;
        this.ownerID = ownerID;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String title, int productID, FavouriteStare favoutite) {
        this.title = title;
        this.productID = productID;
        this.favourite = favoutite;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String title, int productID) {
        this.title = title;
        this.productID = productID;
    }

    /**
     * Przaciążony konstruktor
     */
    public ProductListDescription (String title) {
        this.title = title;
    }
}
