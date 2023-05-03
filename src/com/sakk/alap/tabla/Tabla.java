package com.sakk.alap.tabla;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.sakk.alap.Csapat;
import com.sakk.alap.babu.*;
import com.sakk.alap.jatekos.FeherJatekos;
import com.sakk.alap.jatekos.FeketeJatekos;
import com.sakk.alap.jatekos.Jatekos;

import java.util.*;

public class Tabla {

    private final List<Mezo> jatekTabla;
    private final Collection<Babu> feherBabuk;
    private final Collection<Babu> feketeBabuk;
    private final FeherJatekos feherJatekos;
    private final FeketeJatekos feketeJatekos;
    private final Jatekos jelenlegiJatekos;
    private final Gyalog enPassantGyalog;


    private Tabla(final Epito epito) {
        this.jatekTabla = keszitJatekTabla(epito);
        this.feherBabuk = szamitAktivBabu(this.jatekTabla, Csapat.WHITE);
        this.feketeBabuk = szamitAktivBabu(this.jatekTabla, Csapat.BLACK);
        this.enPassantGyalog = epito.enPassantGyalog;
        final Collection<Lepes> feheralapErvenyesLepes = szamoltErvenyesLepes(this.feherBabuk);
        final Collection<Lepes> feketealapErvenyesLepes = szamoltErvenyesLepes(this.feketeBabuk);
        this.feherJatekos = new FeherJatekos(this, feheralapErvenyesLepes, feketealapErvenyesLepes);
        this.feketeJatekos = new FeketeJatekos(this, feheralapErvenyesLepes, feketealapErvenyesLepes);
        this.jelenlegiJatekos = epito.kovetkezoLepesKeszito.jatekosValasztas(this.feherJatekos, this.feketeJatekos);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TablaSeged.MEZOK_SZAMA; i++) {
            final String mezoText = this.jatekTabla.get(i).toString();
            sb.append(String.format("%3s", mezoText));
            if ((i + 1) % 8 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Megadja az összes bábura az érvényes lépéseket.
     */
    private Collection<Lepes> szamoltErvenyesLepes(final Collection<Babu> babuk) {
        final List<Lepes> ervenyesLepes = new ArrayList<>();
        for(final Babu babu: babuk){
            ervenyesLepes.addAll(babu.szamoltErvenyesLepes(this));
        }
        return ImmutableList.copyOf(ervenyesLepes);
    }

    /**
     * Megmondja, hogy melyek a még táblán lévő bábui egy csapatnak.
     */
    private Collection<Babu> szamitAktivBabu(final List<Mezo> jatekTabla, final Csapat csapat) {
        final List<Babu> aktivBabuk = new ArrayList<>();
        for(final Mezo mezo : jatekTabla){
            if(mezo.foglalte()){
                final Babu babu = mezo.getBabu();
                if(babu.getCsapatBabu() == csapat){
                    aktivBabuk.add(babu);
                }
            }
        }
        return ImmutableList.copyOf(aktivBabuk);
    }

    /**
     * Létrehozza a táblát.
     */
    private List<Mezo> keszitJatekTabla(Epito epito) {
        final Mezo[] mezok = new Mezo[TablaSeged.MEZOK_SZAMA];
        for(int x = 0; x < TablaSeged.OSZLOPOK_SZAMA; x++) {
            for(int y = 0; y < TablaSeged.MEZOK_EGY_SORBAN; y++) {
                mezok[x+(y*8)] = Mezo.Mezokeszites(x, y, epito.tablaAlkotas.get(x+(y*8)));
            }
        }
        return ImmutableList.copyOf(mezok);
    }

    /**
     * Létrehoz egy táblát, ahol beállítja a kezdő felállást.
     */
    public static Tabla alapTablaKeszites(){
        final Epito epito = new Epito();
        // Fekete felállása
        epito.setBabu(new Bastya(Csapat.BLACK, 0, 0));
        epito.setBabu(new Huszar(Csapat.BLACK, 1, 0));
        epito.setBabu(new Futo(Csapat.BLACK, 2, 0));
        epito.setBabu(new Kiralyno(Csapat.BLACK, 3, 0));
        epito.setBabu(new Kiraly(Csapat.BLACK, 4, 0));
        epito.setBabu(new Futo(Csapat.BLACK, 5, 0));
        epito.setBabu(new Huszar(Csapat.BLACK, 6, 0));
        epito.setBabu(new Bastya(Csapat.BLACK, 7, 0));
        epito.setBabu(new Gyalog(Csapat.BLACK, 0, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 1, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 2, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 3, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 4, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 5, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 6, 1));
        epito.setBabu(new Gyalog(Csapat.BLACK, 7, 1));
        // Feher felállása
        epito.setBabu(new Gyalog(Csapat.WHITE, 0, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 1, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 2, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 3, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 4, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 5, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 6, 6));
        epito.setBabu(new Gyalog(Csapat.WHITE, 7, 6));
        epito.setBabu(new Bastya(Csapat.WHITE, 0, 7));
        epito.setBabu(new Huszar(Csapat.WHITE, 1, 7));
        epito.setBabu(new Futo(Csapat.WHITE, 2, 7));
        epito.setBabu(new Kiralyno(Csapat.WHITE, 3, 7));
        epito.setBabu(new Kiraly(Csapat.WHITE, 4, 7));
        epito.setBabu(new Futo(Csapat.WHITE, 5, 7));
        epito.setBabu(new Huszar(Csapat.WHITE, 6, 7));
        epito.setBabu(new Bastya(Csapat.WHITE, 7, 7));

        epito.setLepesKeszito(Csapat.WHITE);

        return epito.epit();
    }

    public Mezo getMezo(final int x, final int y){
        int test = x+(8 * y);
        return jatekTabla.get(test);
    }

    public Collection<Babu> getFeketeBabu() {
        return this.feketeBabuk;
    }
    public Collection<Babu> getFeherBabuk() {
        return this.feherBabuk;
    }
    public Jatekos getJelenlegiJatekos() { return  this.jelenlegiJatekos;}

    public Jatekos feketeJatekos() {
        return this.feketeJatekos;
    }

    public Jatekos feherJatekos() {
        return this.feherJatekos;
    }

    public Iterable<Lepes> getOsszesErvenyesLepes() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.feherJatekos.getErvenyesLepes(), this.feketeJatekos.getErvenyesLepes()));
    }

    public Gyalog getEnPassantGyalog() {
        return this.enPassantGyalog;
    }

    /**
     * A tábla létrehuzásában segítő osztály.
     */
    public static class Epito {
        Map<Integer, Babu> tablaAlkotas;
        Csapat kovetkezoLepesKeszito;
        Gyalog enPassantGyalog;

        /**
         * Új hashmap-et hoz létre, amiben tároljuk majd a bábukat.
         */
        public Epito() {
            this.tablaAlkotas = new HashMap<>();
        }
        public Epito setBabu(final Babu babu){
            this.tablaAlkotas.put(babu.getX()+(8 * babu.getY()),babu);
            return this;
        }
        public Epito setLepesKeszito(final Csapat kovetkezoLepesKeszito){
            this.kovetkezoLepesKeszito = kovetkezoLepesKeszito;
            return this;
        }

        /**
         * Létrehoz egy új táblát.
         */
        public Tabla epit(){
            return new Tabla(this);
        }

        public void setEnPassantGyalog(Gyalog enPassantGyalog) {
            this.enPassantGyalog = enPassantGyalog;
        }
    }
}
