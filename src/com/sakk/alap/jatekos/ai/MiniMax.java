package com.sakk.alap.jatekos.ai;

import com.sakk.alap.jatekos.Athelyez;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;

public class MiniMax implements LepesStrategia {

    private final TablaErtekelo tablaErtekelo;
    private final int melyseg;

    public MiniMax(final int melyseg) {
        this.tablaErtekelo = new AlapTablaErtekelo();
        this.melyseg = melyseg;
    }

    @Override
    public String toString() {
        return "Minimax";
    }

    /**
     * Létrehoz 2 értéket az egyiknek a lehető legkissebb számot a másiknak a lehető legnagyobb számot adja.
     * Majd végigmegy a tábla összes lehetséges lépésén, amit az aktív játékos léphet.
     * A lépés alapján látrehoz egy átmenti táblát,a hol értékelni fogja a lépést.
     * Majd az alapján, hogy az aktív játékos melyik csapatba tartozik beállítja a jelenlegi értéket.
     * Ha a játékos fehér csapattal van, akkor megkeresi a legnagyobb értéket, ami lépéshez tartozik.
     * Ha a játékos fekete csapattal van, akkor megkeresi a legkisebb értéket, ami lépéshez tartozik.
     * @param tabla
     * @return legjobb pontszámmal rendelkező lépés
     */
    @Override
    public Lepes kereses(Tabla tabla) {
        Lepes legjobbLepes = null;
        int legmagasabbLatotErtek = Integer.MIN_VALUE;
        int legkisebbLatotErtek = Integer.MAX_VALUE;
        int jellenlegiErtek;
        for(final Lepes lepes : tabla.getJelenlegiJatekos().getErvenyesLepes()) {
            final Athelyez athelyez = tabla.getJelenlegiJatekos().lep(lepes);
            if(athelyez.getLepesAllapot().Kesz()) {
                jellenlegiErtek = tabla.getJelenlegiJatekos().getCsapat().Fehere() ?
                        min(athelyez.getAtmenetiTabla(), melyseg - 1) :
                        max(athelyez.getAtmenetiTabla(), melyseg - 1);
                if(tabla.getJelenlegiJatekos().getCsapat().Fehere() && jellenlegiErtek >= legmagasabbLatotErtek) {
                    legmagasabbLatotErtek = jellenlegiErtek;
                    legjobbLepes = lepes;
                } else if(tabla.getJelenlegiJatekos().getCsapat().Feketee() && jellenlegiErtek <= legkisebbLatotErtek) {
                    legkisebbLatotErtek = jellenlegiErtek;
                    legjobbLepes = lepes;
                }
            }
        }
        return legjobbLepes;
    }

    /**
     * Keresi a legkisebb értéket ami tartozhat egy lépéshez.
     * Ezt addig csinálja, amíg a mélység nem lesz 0.
     * @param tabla
     * @param melyseg
     * @return
     */
    public int min(final Tabla tabla, final int melyseg) {
        if(melyseg == 0 || ezAJatekVege(tabla)) {
            return this.tablaErtekelo.ertekel(tabla, melyseg);
        }
        int legkisebbLatotErtek = Integer.MAX_VALUE;
        for(final Lepes lepes : tabla.getJelenlegiJatekos().getErvenyesLepes()) {
            final Athelyez athelyez = tabla.getJelenlegiJatekos().lep(lepes);
            if(athelyez.getLepesAllapot().Kesz()) {
                final int jellenlegiErtek = max(athelyez.getAtmenetiTabla(), melyseg -1);
                if(jellenlegiErtek <= legkisebbLatotErtek) {
                    legkisebbLatotErtek = jellenlegiErtek;
                }
            }
        }
        return legkisebbLatotErtek;
    }

    /**
     * Megnézi, hogy a játéknak vége van-e.
     * Akkor van vége, ha valaki sakkmattot vagy pattot kapott.
     * @param tabla
     * @return
     */
    private static boolean ezAJatekVege(final Tabla tabla) {
        return tabla.getJelenlegiJatekos().ezSakkMatt() || tabla.getJelenlegiJatekos().ezPatt();
    }

    /**
     * Keresi a legnagyobb értéket ami tartozhat egy lépéshez.
     * Ezt addig csinálja, amíg a mélység nem lesz 0.
     * @param tabla
     * @param melyseg
     * @return
     */
    public int max(final Tabla tabla, final int melyseg) {
        if(melyseg == 0 || ezAJatekVege(tabla)) {
            return this.tablaErtekelo.ertekel(tabla, melyseg);
        }
        int legnagyobbLatotErtek = Integer.MIN_VALUE;
        for(final Lepes lepes : tabla.getJelenlegiJatekos().getErvenyesLepes()) {
            final Athelyez athelyez = tabla.getJelenlegiJatekos().lep(lepes);
            if(athelyez.getLepesAllapot().Kesz()) {
                final int jellenlegiErtek = min(athelyez.getAtmenetiTabla(), melyseg -1);
                if(jellenlegiErtek >= legnagyobbLatotErtek) {
                    legnagyobbLatotErtek = jellenlegiErtek;
                }
            }
        }
        return legnagyobbLatotErtek;
    }


}
