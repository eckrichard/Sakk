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

public class Gyalog extends Babu{
    /**
     * Azok a lehetséges számok amelyekkel megkaphatjuk, hogy hova léphet a gyalog.
     */
    private final static int[] LEHETSEGES_LEPES_KORDINATA_GYALOG_Y = {1,2};

    public Gyalog(final Csapat csapatBabu, final int x,final int y) {
        super(BabuTipus.GYALOG,x, y, csapatBabu, true);
    }

    public Gyalog(final Csapat csapatBabu, final int x,final int y, final boolean elsolepes) {
        super(BabuTipus.GYALOG,x, y, csapatBabu, elsolepes);
    }

    /**
     * Kiszámolja, hogy a bástya egy adott tábla esetén hova léphet.
     * Majd létrehozza a lépést az alapján, hogy milyen a típusa és hozzáadja az érvényes lépésekhez.
     */
    public Collection<Lepes> szamoltErvenyesLepes(final Tabla tabla) {
        final List<Lepes> ervenyesLepes = new ArrayList<>();
        for(final int lehetosegek_y : LEHETSEGES_LEPES_KORDINATA_GYALOG_Y){
            int lehetsegesLepesHelyY = this.y + (this.csapatBabu.getIrany() * lehetosegek_y);
            int lehetsegesLepesHelyX = this.x;
            if(TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY)) {
                if(lehetosegek_y == 1 && !tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY).foglalte()){
                    if(this.csapatBabu.ezAtalakulasHely(lehetsegesLepesHelyX, lehetsegesLepesHelyY)){
                        ervenyesLepes.add(new GyalogAtalakul(new GyalogLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY)));
                    } else {
                        ervenyesLepes.add(new GyalogLepes(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY));
                    }
                } else if(lehetosegek_y == 2 && this.elsoLepes() &&
                        !tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY+(this.csapatBabu.getIrany())).foglalte() &&
                        !tabla.getMezo(lehetsegesLepesHelyX, lehetsegesLepesHelyY).foglalte()){
                    ervenyesLepes.add(new GyalogUgras(tabla, this, lehetsegesLepesHelyX, lehetsegesLepesHelyY));
                }
                lehetsegesLepesHelyY = this.y + (this.csapatBabu.getIrany() * lehetosegek_y);
                if(lehetosegek_y == 1 && TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY)) {
                    if(tabla.getMezo(lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY).foglalte()){
                        final Babu babuaMezon = tabla.getMezo(lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY).getBabu();
                        if(this.csapatBabu != babuaMezon.getCsapatBabu()) {
                            if(this.csapatBabu.ezAtalakulasHely(lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY)) {
                                ervenyesLepes.add(new GyalogAtalakul(new GyalogLepes(tabla, this, lehetsegesLepesHelyX + 1 , lehetsegesLepesHelyY)));
                            } else {
                                ervenyesLepes.add(new GyalogTamadoLepes(tabla,this, lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY, babuaMezon));
                            }
                        }
                    } else if(tabla.getEnPassantGyalog() != null && tabla.getEnPassantGyalog().getY() == this.y && tabla.getEnPassantGyalog().getX() == this.x + 1) {
                        final Babu babuaMezon = tabla.getEnPassantGyalog();
                        if(this.csapatBabu != babuaMezon.getCsapatBabu()) {
                            ervenyesLepes.add(new GyalogEnPassantTamadoLepes(tabla, this, lehetsegesLepesHelyX + 1, lehetsegesLepesHelyY, babuaMezon));
                        }
                    }
                }
                lehetsegesLepesHelyY = this.y + (this.csapatBabu.getIrany() * lehetosegek_y);
                if(lehetosegek_y == 1 && TablaSeged.ErvenyesMezo(lehetsegesLepesHelyX - 1, lehetosegek_y)) {
                    if(tabla.getMezo(lehetsegesLepesHelyX - 1, lehetsegesLepesHelyY).foglalte()){
                        final Babu babuaMezon = tabla.getMezo(lehetsegesLepesHelyX - 1, lehetsegesLepesHelyY).getBabu();
                        if(this.csapatBabu != babuaMezon.getCsapatBabu()) {
                            if(this.csapatBabu.ezAtalakulasHely(lehetsegesLepesHelyX - 1, lehetsegesLepesHelyY)) {
                                ervenyesLepes.add(new GyalogAtalakul(new GyalogLepes(tabla, this, lehetsegesLepesHelyX - 1 , lehetsegesLepesHelyY)));
                            } else {
                                ervenyesLepes.add(new GyalogTamadoLepes(tabla,this, lehetsegesLepesHelyX - 1, lehetsegesLepesHelyY, babuaMezon));
                            }
                        }
                    }
                    if(tabla.getEnPassantGyalog() != null && tabla.getEnPassantGyalog().getY() == this.y && tabla.getEnPassantGyalog().getX() == this.x - 1) {
                        final Babu babuaMezon = tabla.getEnPassantGyalog();
                        if(this.csapatBabu != babuaMezon.getCsapatBabu()) {
                            ervenyesLepes.add(new GyalogEnPassantTamadoLepes(tabla, this, lehetsegesLepesHelyX - 1, lehetsegesLepesHelyY, babuaMezon));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(ervenyesLepes);
    }

    @Override
    public Gyalog mozgoBabu(Lepes lepes) {
        return new Gyalog(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), false);
    }

    @Override
    public Gyalog mozgoBabu(Lepes lepes, boolean elsoLepes) {
        return new Gyalog(lepes.getMozgoBabu().getCsapatBabu(), lepes.getCelX(), lepes.getCelY(), elsoLepes);
    }

    public String toString(){ return BabuTipus.GYALOG.toString(); }

    /**
     * Egy számot kap ami alapján eldönti, hogy mi lesz majd a gyalogból.
     * @param babu (királynő: 1, huszár: 2, bástya: 3, futó: 4)
     * @return Olyan bábuval tér vissza amivé a gyalog átalakul.
     */
    public Babu getAtalakuloGyalog(final int babu) {
        if(babu == 1) {
            return new Kiralyno(this.csapatBabu, x, y, false);
        } else if(babu == 2) {
            return new Huszar(this.csapatBabu, x, y, false);
        } else  if(babu == 3) {
            return new Bastya(this.csapatBabu, x, y, false);
        } else {
            return new Futo(this.csapatBabu, x, y, false);
        }
    }
}
