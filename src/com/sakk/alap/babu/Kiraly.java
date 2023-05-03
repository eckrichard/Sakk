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

public class Kiraly extends Babu{
    /**
     * Azok a lehetséges számok amelyekkel megkaphatjuk, hogy hova léphet a király.
     */
    private final static int[] LEHETSEGES_LEPES_KORDINATA_KIRALY = {-1, 1};

    public Kiraly(final Csapat csapatBabu, final int x,final int y) {
        super(BabuTipus.KIRALY,x, y, csapatBabu, true);
    }

    public Kiraly(final Csapat csapatBabu, final int x,final int y, final boolean elsoLepes) {
        super(BabuTipus.KIRALY,x, y, csapatBabu, elsoLepes);
    }

    /**
     * Kiszámolja, hogy a bástya egy adott tábla esetén hova léphet.
     * Majd létrehozza a lépést az alapján, hogy milyen a típusa és hozzáadja az érvényes lépésekhez.
     */
    public Collection<Lepes> szamoltErvenyesLepes(Tabla tabla) {
        final List<Lepes> ervenyesLepes = new ArrayList<>();
        for (final int lehetosegek : LEHETSEGES_LEPES_KORDINATA_KIRALY){
            int lehetsegesLepesHelyX = this.x + lehetosegek;
            int lehetsegesLepesHelyY = this.y;
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
                }
            }
            lehetsegesLepesHelyX = this.x;
            lehetsegesLepesHelyY = this.y + lehetosegek;
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
                }
            }
            lehetsegesLepesHelyX = this.x + lehetosegek;
            lehetsegesLepesHelyY = this.y + lehetosegek;
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
                }
            }
            lehetsegesLepesHelyX = this.x + lehetosegek;
            lehetsegesLepesHelyY = this.y - lehetosegek;
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
                }
            }
        }
        if(TablaSeged.ErvenyesMezo(this.x - 1, this.y) && TablaSeged.ErvenyesMezo(this.x - 2, this.y) &&
                TablaSeged.ErvenyesMezo(this.x - 3, this.y) && TablaSeged.ErvenyesMezo(this.x - 4, this.y)){
            final Mezo aM = tabla.getMezo(this.x-4, this.y);
            final Mezo bM = tabla.getMezo(this.x-3, this.y);
            final Mezo cM = tabla.getMezo(this.x-2, this.y);
            final Mezo dM = tabla.getMezo(this.x-1, this.y);
            if (aM.foglalte() && !(bM.foglalte()) && !(cM.foglalte()) && !(dM.foglalte())) {
                if(aM.getBabu().babuTipus.Bastya()){
                    ervenyesLepes.add(new Lepes.balraSancol(tabla, this, this.x - 2, this.y,(Bastya) aM.getBabu(), this.x-4, this.y, this.x-1, this.y));
                }
            }
        }
        if(TablaSeged.ErvenyesMezo(this.x + 3, this.y) && TablaSeged.ErvenyesMezo(this.x + 2, this.y) && TablaSeged.ErvenyesMezo(this.x + 1, this.y)){
            final Mezo fM = tabla.getMezo(this.x+1, this.y);
            final Mezo gM = tabla.getMezo(this.x+2, this.y);
            final Mezo hM = tabla.getMezo(this.x+3, this.y);
            if (hM.foglalte() && !(gM.foglalte()) && !(fM.foglalte())) {
                if(hM.getBabu().babuTipus.Bastya() && hM.getBabu().elsoLepes()){
                    ervenyesLepes.add(new Lepes.jobbraSancol(tabla, this, this.x + 2, this.y, (Bastya) hM.getBabu(), this.x+3, this.y, this.x+1, this.y));
                }
            }
        }
        return ImmutableList.copyOf(ervenyesLepes);
    }

    @Override
    public Kiraly mozgoBabu(Lepes lepes) {
        return new Kiraly(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), false);
    }

    @Override
    public Kiraly mozgoBabu(Lepes lepes, boolean elsoLepes) {
        return new Kiraly(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), elsoLepes);
    }

    public String toString(){ return BabuTipus.KIRALY.toString(); }
}
