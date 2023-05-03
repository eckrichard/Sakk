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

public class FeherJatekos extends Jatekos{

    public FeherJatekos(final Tabla tabla, final Collection<Lepes> feheralapErvenyesLepes, final Collection<Lepes> feketealapErvenyesLepes) {
        super(tabla, feheralapErvenyesLepes, feketealapErvenyesLepes);
    }

    @Override
    public Collection<Babu> getAktivBabu() {
        return this.tabla.getFeherBabuk();
    }

    @Override
    public Csapat getCsapat() {
        return Csapat.WHITE;
    }

    @Override
    public Jatekos getEllenfel() {
        return this.tabla.feketeJatekos();
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
            if(!this.tabla.getMezo(5, 7).foglalte() && !this.tabla.getMezo(6, 7).foglalte()) {
                final Mezo bastyaMezoje = this.tabla.getMezo(7, 7);
                if(bastyaMezoje.foglalte() && bastyaMezoje.getBabu().elsoLepes()) {
                    if(Jatekos.szamoltTamadasAMezon(5, 7, ellenfelErvenyesLepesei).isEmpty() &&
                            Jatekos.szamoltTamadasAMezon(6, 7, ellenfelErvenyesLepesei).isEmpty() &&
                            bastyaMezoje.getBabu().getBabuTipus().Bastya()) {
                        Sancol.add(new jobbraSancol(this.tabla, this.jatekosKiraly, 6, 7,
                                    (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getX(), bastyaMezoje.getY(),5, 7));
                    }
                }
            }
            if(!this.tabla.getMezo(3, 7).foglalte() && !this.tabla.getMezo(2, 7).foglalte() &&
                    !this.tabla.getMezo(1, 7).foglalte()) {
                final Mezo bastyaMezoje = this.tabla.getMezo(0, 7);
                if(bastyaMezoje.foglalte() && bastyaMezoje.getBabu().elsoLepes() &&
                        Jatekos.szamoltTamadasAMezon(2, 7, ellenfelErvenyesLepesei).isEmpty() &&
                        Jatekos.szamoltTamadasAMezon(3, 7, ellenfelErvenyesLepesei).isEmpty() &&
                        bastyaMezoje.getBabu().getBabuTipus().Bastya()) {
                    Sancol.add(new balraSancol(this.tabla, this.jatekosKiraly, 2, 7,
                                (Bastya) bastyaMezoje.getBabu(), bastyaMezoje.getX(), bastyaMezoje.getY(), 3, 7));
                }
            }
        }

        return ImmutableList.copyOf(Sancol);
    }

    @Override
    public String toString() {
        return "Fehér";
    }
}
