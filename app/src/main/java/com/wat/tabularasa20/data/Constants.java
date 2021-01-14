package com.wat.tabularasa20.data;

/**
 * Klasa na wartości stałe
 */
public final class Constants {

    // ?login=BabackiA&haslo=Babacki1
    public static final String LOGIN_CHECK_URL = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/logowanie";

    // ?id_klienta=1&id_konta=-1
    public static final String ACCOUNT_GET_URL = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/klient";

    //  brak
    public static final String BOOKS_GET_URL   = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ksiazki";

    //  przyjmuje JSON
    public static final String BOOK_ADD_URL    = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ksiazki/dodaj";

    // ?id_konta=1
    public static final String FAVOURITES_URL  = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ulubione";

    // ?id_konta=21&id_ksiazki=37
    public static final String FAVOURITES_ADD  = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ulubione/dodaj";

    // ?id_ksiazki=80&id_konta=85
    public static final String FAVOURITES_REM  = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ulubione/usun";

    //  przyjmuje JSON
    public static final String REGISTER_URL    = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/rejestracja";

    // ?id_ksiazki=3
    public static final String COPIES_URL      = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/egzemplarze";

    // ?id_ksiazki=4&id_konta=20
    public static final String DETAILS_URL     = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/egzemplarze/szczegoly";

    // ?id_konta=31
    public static final String SHARED_URL      = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ksiazki/udostepnione";

    // ?id_konta=12&id_ksiazki=21
    public static final String SHARED_REM      = "https://082c4syy0k.execute-api.eu-west-1.amazonaws.com/v1/ksiazki/udostepnione/usun";

    // adres z kąd pobrano tło
    private static final String BG_URL         = "https://unsplash.com/photos/5LOhydOtTKU";

}
