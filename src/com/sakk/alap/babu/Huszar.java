package com.sakk.alap.babu;

import com.google.common.collect.ImmutableList;
import com.sakk.alap.Csapat;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Mezo;
import com.sakk.alap.tabla.Tabla;
import com.sakk.alap.tabla.TablaSeged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sakk.alap.tabla.Lepes.*;

public class Huszar extends Babu {
    /**
     * Azok a lehetséges számok amelyekkel megkaphatjuk, hogy hova léphet a huszár.
     */
    private final static int[] LEHETSEGES_LEPES_KORDINATA_HUSZAR_X = {-2,-1,1,2};
    private final static int[] LEHETSEGES_LEPES_KORDINATA_HUSZAR_Y = {-2,-1,1,2};

    public Huszar(final Csapat csapatBabu, final int x,final int y) {
        super(BabuTipus.HUSZAR,x, y, csapatBabu, true);
    }

    public Huszar(final Csapat csapatBabu, final int x,final int y, final boolean elsoLepes) {
        super(BabuTipus.HUSZAR,x, y, csapatBabu, elsoLepes);
    }

    /**
     * Kiszámolja, hogy a bástya egy adott tábla esetén hova léphet.
     * Majd létrehozza a lépést az alapján, hogy milyen a típusa és hozzáadja az érvényes lépésekhez.
     */
    public Collection<Lepes> szamoltErvenyesLepes(Tabla tabla) {

        final List<Lepes> ervenyesLepes = new ArrayList<>();

        for (final int lehetosegek_x : LEHETSEGES_LEPES_KORDINATA_HUSZAR_X) {
            for(final int lehetosegek_y : LEHETSEGES_LEPES_KORDINATA_HUSZAR_Y) {
                if(((lehetosegek_x == -2 || lehetosegek_x == 2) && (lehetosegek_y == -2 || lehetosegek_y == 2)) ||
                        ((lehetosegek_x == -1 || lehetosegek_x == 1) && (lehetosegek_y == -1 || lehetosegek_y == 1))){
                    continue;
                }
                int lehetsegesLepesHelyX = this.x + lehetosegek_x;
                int lehetsegesLepesHelyY = this.y + lehetosegek_y;
                if(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                    final Mezo lehetsegesLepesMezo = tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY);
                    if(!lehetsegesLepesMezo.foglalte()){
                        ervenyesLepes.add(new SimaLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY));
                    } else{
                        final Babu babuVanOtt = lehetsegesLepesMezo.getBabu();
                        final Csapat babuCsapat = babuVanOtt.getCsapatBabu();
                        if(this.csapatBabu != babuCsapat){
                            ervenyesLepes.add(new TamadoLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY, babuVanOtt));
                        }
                    }
                }
            }
        }

        return ImmutableList.copyOf(ervenyesLepes);
    }

    @Override
    public Huszar mozgoBabu(Lepes lepes) {
        return new Huszar(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), false);
    }

    @Override
    public Huszar mozgoBabu(Lepes lepes, boolean elsoLepes) {
        return new Huszar(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), elsoLepes);
    }

    public String toString(){ return BabuTipus.HUSZAR.toString(); }
}
