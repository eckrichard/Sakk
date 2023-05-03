package com.sakk.alap.jatekos;

import com.google.common.collect.ImmutableList;
import com.sakk.alap.Csapat;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.babu.Bastya;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Mezo;
import com.sakk.alap.tabla.Tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sakk.alap.tabla.Lepes.*;

public class FeketeJatekos extends Jatekos{

    public FeketeJatekos(final Tabla tabla, final Collection<Lepes> feheralapErvenyesLepes, final Collection<Lepes> feketealapErvenyesLepes) {
        super(tabla, feketealapErvenyesLepes, feheralapErvenyesLepes);
    }

    @Override
    public Collection<Babu> getAktivBabu() {
        return this.tabla.getFeketeBabu();
    }

    @Override
    public Csapat getCsapat() {
        return Csapat.BLACK;
    }

    @Override
    public Jatekos getEllenfel() {
        return this.tabla.feherJatekos();
    }

    /**
     * Megnézi, hogy ha a királynak és a bástyának is az első lépése és a köztük lévő mezők üresek,
     * valamint a király nincsen sakkban, akkor megnézi, hogy a lépés után sakkban lesz-e.
     * Ha nem lesz sakkban akkor egy új sáncolást ad a sáncol listához.
     * @param jatekosErvenyesLepesei
     * @param ellenfelErvenyesLepesei
     * @return Egy ImmutableListtel tér vissza, amiben a lehetséges sáncolás van.
     */
    @Override
    protected Collection<Lepes> szamoltSancol(final Collection<Lepes> jatekosErvenyesLepesei, final Collection<Lepes> ellenfelErvenyesLepesei) {
        final List<Lepes> Sancol = new ArrayList<>();
        if(this.jatekosKiraly.elsoLepes() && !this.ezSakk()) {
            //fehér királya sáncol
            if(!this.tabla.getMezo(5, 0).foglalte() && !this.tabla.getMezo(6, 0).foglalte()) {
                final Mezo bastyaMezoje = this.tabla.getMezo(7, 0);
                if(bastyaMezoje.foglalte() && bastyaMezoje.getBabu().elsoLepes()) {
                    if(Jatekos.szamoltTamadasAMezon(5, 0, ellenfelErvenyesLepesei).isEmpty() &&
                            Jatekos.szamoltTamadasAMezon(6, 0, ellenfelErvenyesLepesei).isEmpty() && bastyaMezoje.getBabu().getBabuTipus().Bastya()) {
                        Sancol.add(new jobbraSancol(this.tabla, this.jatekosKiraly, 6, 0,
                                (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getX(), bastyaMezoje.getY(),
                                5, 0));
                    }
                }
            }
            if(!this.tabla.getMezo(1, 0).foglalte() && !this.tabla.getMezo(2, 0).foglalte() && !this.tabla.getMezo(3, 0).foglalte()) {
                final Mezo bastyaMezoje = this.tabla.getMezo(0, 0);
                if(bastyaMezoje.foglalte() && bastyaMezoje.getBabu().elsoLepes() &&
                        Jatekos.szamoltTamadasAMezon(2, 0, ellenfelErvenyesLepesei).isEmpty() &&
                        Jatekos.szamoltTamadasAMezon(3, 0, ellenfelErvenyesLepesei).isEmpty() &&
                        bastyaMezoje.getBabu().getBabuTipus().Bastya()) {
                    Sancol.add(new balraSancol(this.tabla, this.jatekosKiraly, 2, 0,
                            (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getX(), bastyaMezoje.getY(), 3, 0));
                }
            }
        }

        return ImmutableList.copyOf(Sancol);
    }

    @Override
    public String toString() {
        return "Fekete";
    }
}
