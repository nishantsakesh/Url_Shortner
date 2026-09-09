package com.production.url_shortener.util;

public class Base62Encoder {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALLOWED_CHARACTERS.length();

    // Convert database ID to Base62 string
    public static String encode(long id) {
        if (id == 0) {
            return String.valueOf(ALLOWED_CHARACTERS.charAt(0));
        }

        StringBuilder encodedString = new StringBuilder();
        while (id > 0) {
            encodedString.append(ALLOWED_CHARACTERS.charAt((int) (id % BASE)));
            id = id / BASE;
        }

        return encodedString.reverse().toString();
    }

    // Convert Base62 string back to database ID
    public static long decode(String shortUrl) {
        long id = 0;
        long multiplier = 1;

        for (int i = shortUrl.length() - 1; i >= 0; i--) {
            id += ALLOWED_CHARACTERS.indexOf(shortUrl.charAt(i)) * multiplier;
            multiplier *= BASE;
        }

        return id;
    }
}
