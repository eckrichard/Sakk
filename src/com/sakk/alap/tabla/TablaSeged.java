package com.sakk.alap.tabla;

public class TablaSeged {
    public static final int MEZOK_SZAMA = 64;
    public static final int MEZOK_EGY_SORBAN = 8;
    public static final int OSZLOPOK_SZAMA = 8;
    public static final int KEZDO_INDEX = 0;

    private TablaSeged() {
        throw new RuntimeException("Nem lehet példányosítani");
    }

    /**
     * Megadja az érvényes mezőket.
     * @return
     */
    public static boolean ErvenyesMezo(final int x, final int y) {
        return x >= KEZDO_INDEX && x < MEZOK_EGY_SORBAN && y >= KEZDO_INDEX && y < OSZLOPOK_SZAMA;
    }
}
