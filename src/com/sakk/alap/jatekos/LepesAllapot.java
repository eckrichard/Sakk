package com.sakk.alap.jatekos;

/**
 * Lépések állapotát tároljuk, amik lehetnek késze, érvénytelenek és olyan lépések, ahol a király sakkban marad.
 */
public enum LepesAllapot {
    KESZ{
        @Override
        public boolean Kesz() {
            return true;
        }
    },
    ERVENYTELEN_LEPES {
        @Override
        public boolean Kesz() {
            return false;
        }
    },
    SAKKBAN_MARAD {
        @Override
        public boolean Kesz() {
            return false;
        }
    };

    /**
     * Azt mondja meg, hogy egy lépés kész-e, azaz teljesíthető-e.
     * @return
     */
    public abstract boolean Kesz();
}
