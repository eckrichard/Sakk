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

public class Bastya extends Babu {
    /**
     * Azok a lehetséges számok amelyekkel megkaphatjuk, hogy hova léphet a bástya.
     * Ebben az esetben ezeknek a számoknak a többszörösei is lehetnek.
     */
    private final static int[] LEHETSEGES_LEPES_KORDINATA_BASTYA = {-1,1};

    public Bastya(final Csapat csapatBabu,final int x,final int y) {
        super(BabuTipus.BASTYA,x, y, csapatBabu, true);
    }

    public Bastya(final Csapat csapatBabu,final int x,final int y, final boolean elsoLepes) {
        super(BabuTipus.BASTYA,x, y, csapatBabu, elsoLepes);
    }

    /**
     * Kiszámolja, hogy a bástya egy adott tábla esetén hova léphet.
     * Majd létrehozza a lépést az alapján, hogy milyen a típusa és hozzáadja az érvényes lépésekhez.
     */
    public Collection<Lepes> szamoltErvenyesLepes(final Tabla tabla) {
        final List<Lepes> ervenyesLepes = new ArrayList<>();
        for (final int lehetosegek : LEHETSEGES_LEPES_KORDINATA_BASTYA){
            int lehetsegesLepesHelyX = this.x;
            int lehetsegesLepesHelyY = this.y;
            while(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                lehetsegesLepesHelyX += lehetosegek;
                if(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                    final Mezo lehetsegesLepesMezo = tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY);
                    if(!lehetsegesLepesMezo.foglalte()){
                        ervenyesLepes.add(new Lepes.SimaLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY));
                    } else{
                        final Babu babuVanOtt = lehetsegesLepesMezo.getBabu();
                        final Csapat babuCsapat = babuVanOtt.getCsapatBabu();
                        if(this.csapatBabu != babuCsapat){
                            ervenyesLepes.add(new Lepes.TamadoLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY, babuVanOtt));
                        }
                        break;
                    }
                }
            }
            while(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                lehetsegesLepesHelyX = this.x;
                lehetsegesLepesHelyY += lehetosegek;
                if(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                    final Mezo lehetsegesLepesMezo = tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY);
                    if(!lehetsegesLepesMezo.foglalte()){
                        ervenyesLepes.add(new Lepes.SimaLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY));
                    } else{
                        final Babu babuVanOtt = lehetsegesLepesMezo.getBabu();
                        final Csapat babuCsapat = babuVanOtt.getCsapatBabu();
                        if(this.csapatBabu != babuCsapat){
                            ervenyesLepes.add(new Lepes.TamadoLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY, babuVanOtt));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(ervenyesLepes);
    }

    @Override
    public Bastya mozgoBabu(Lepes lepes) {
        return new Bastya(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), false);
    }

    @Override
    public Bastya mozgoBabu(Lepes lepes, boolean elsoLepes) {
        return new Bastya(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), elsoLepes);
    }

    public String toString(){ return BabuTipus.BASTYA.toString(); }
}
