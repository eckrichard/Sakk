package com.sakk.alap.babu;

import com.sakk.alap.Csapat;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;

import java.util.Collection;

public abstract class Babu {

    protected final BabuTipus babuTipus;
    protected final Csapat csapatBabu;
    protected final boolean elsoLepes;
    protected int x;
    protected int y;

    private final int cachedHashCode;

    /**
     * Az abstract Babu osztály konstruktora
     */
    Babu(final BabuTipus babuTipus, final int x, final int y, final Csapat csapatBabu, final boolean elsoLepes) {
        this.babuTipus = babuTipus;
        this.csapatBabu = csapatBabu;
        this.x = x;
        this.y = y;
        this.elsoLepes = elsoLepes;
        this.cachedHashCode = szamoltHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Babu masikBabu)) {
            return false;
        }
        return x == masikBabu.getX() && y == masikBabu.getY() && elsoLepes == masikBabu.elsoLepes() && babuTipus == masikBabu.getBabuTipus() && csapatBabu == masikBabu.getCsapatBabu();
    }

    @Override
    public int hashCode() { return this.cachedHashCode; }


    /**
     * Kiszámolja a hash kódját a bábunak az alapján,
     * hogy mi a típusa, melyik csapathoz tartozik,
     * mi a pozíciója és hogy az első lépését megtette-e.
     */
    private int szamoltHashCode() {
        int eredmeny = babuTipus.hashCode();
        eredmeny = 31 * eredmeny + csapatBabu.hashCode();
        eredmeny = 31 * eredmeny + x+(8*y);
        eredmeny = 31 * eredmeny + (elsoLepes ? 1 : 0);
        return eredmeny;
    }

    public BabuTipus getBabuTipus() { return this.babuTipus; }

    public Csapat getCsapatBabu(){ return this.csapatBabu; }

    public boolean elsoLepes() { return this.elsoLepes; }

    public Integer getX() { return x; }

    public Integer getY() { return y; }

    public void setX(final int x) { this.x = x; }

    public void setY(final int y) { this.y = y; }

    public int getBabuErteke() { return this.babuTipus.getBabuErteke(); }

    public abstract Collection<Lepes> szamoltErvenyesLepes(final Tabla tabla);
    /**
     * Létrehoz egy új bábut, ami már a lépést megvalósította. A bábu koordinátája,
     * így a lépés cél koordinátája lesz. Az első lépésének értéke meg false lesz,
     * mert már biztosan lépet.
     */
    public abstract Babu mozgoBabu(Lepes lepes);

    /**
     * Ezt akkor használjuk, ha egy lépést szeretnénk visszavonni,
     * mert így átadhatjuk a lépés előtt lépet-e már. Létrehoz egy új bábut,
     * ami már a lépést megvalósította. A bábu koordinátája,
     * így a lépés cél koordinátája lesz.
     * Az első lépésének értéke meg a kapott első lépés értéke lesz.
     */
    public abstract Babu mozgoBabu(Lepes lepes, boolean elsoLepes);

    /**
     * A bábuk lehetséges típusai.
     * A bábukról tároljuk megjeleníteni kívánt nevüket,
     * valamint az értéküket.
     */
    public enum BabuTipus{

        GYALOG("G", 100){
            @Override
            public boolean Kiraly() { return false; }

            @Override
            public boolean Bastya() { return false; }
        },
        HUSZAR("H", 300){
            @Override
            public boolean Kiraly() { return false; }

            @Override
            public boolean Bastya() { return false; }
        },
        FUTO("F", 330){
            @Override
            public boolean Kiraly() { return false; }

            @Override
            public boolean Bastya() { return false; }
        },
        KIRALY("K", 10000){
            @Override
            public boolean Kiraly() { return true; }

            @Override
            public boolean Bastya() { return false; }
        },
        KIRALYNO("N", 900){
            @Override
            public boolean Kiraly() { return false; }

            @Override
            public boolean Bastya() { return false; }
        },
        BASTYA("B", 500){
            @Override
            public boolean Kiraly() { return false; }

            @Override
            public boolean Bastya() { return true; }
        };

        private final String babuNeve;
        private final int ertek;

        BabuTipus(final String babuNeve, final int ertek) {
            this.babuNeve = babuNeve;
            this.ertek = ertek;
        }

        public abstract boolean Kiraly();

        public abstract boolean Bastya();

        public int getBabuErteke(){ return ertek; }

        public String toString(){ return this.babuNeve; }
    }
}
