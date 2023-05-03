package com.sakk.alap.jatekos.ai;

import com.sakk.alap.babu.Babu;
import com.sakk.alap.jatekos.Jatekos;
import com.sakk.alap.tabla.Tabla;

public final class AlapTablaErtekelo implements TablaErtekelo {

    private static final int SAKK_BONUS = 40;
    private static final int SAKKMATT_BONUS = 10000;
    private static final int MELYSEG_BONUS = 50;
    private static final int SANCOL_BONUS = 25;

    @Override
    public int ertekel(final Tabla tabla, final  int melyseg) {
        return pontok(tabla.feherJatekos(), melyseg) - pontok(tabla.feketeJatekos(), melyseg);
    }

    /**
     * Kiszámolja, hogy a kapott játékos az egy lépésre milyen pontot kap.
     * @param jatekos
     * @param melyseg
     * @return mennyi a lépésre kapott pont
     */
    private int pontok(final  Jatekos jatekos,final int melyseg) {
        return babuErtek(jatekos) + ervenyesLepesekSzama(jatekos) + sakk(jatekos) + sakkmatt(jatekos, melyseg) +
                sancol(jatekos);
    }

    /**
     * Megnézi, hogy egy adott játékos tud-e sáncolni, ha a lépés bekövetkezik.
     * A pontot ez alapján adja meg.
     * @param jatekos
     * @return pontszám
     */
    private static int sancol(Jatekos jatekos) {
        return jatekos.ezSancolas() ? SANCOL_BONUS : 0;
    }

    /**
     * Megmondja, hogy egy játékosnak mennyi érvényes lépése van.
     * @param jatekos
     * @return pontszám
     */
    private int ervenyesLepesekSzama(Jatekos jatekos) {
        return jatekos.getErvenyesLepes().size();
    }

    /**
     * Megnézi, hogy egy adott játékos sakkmattba kerül-e, ha a lépés bekövetkezik.
     * A pontot ez alapján adja meg.
     * @param jatekos
     * @param melyseg
     * @return pontszám
     */
    private static int sakkmatt(final Jatekos jatekos, final int melyseg) {
        return jatekos.getEllenfel().ezSakkMatt() ? SAKKMATT_BONUS * melysegBonus(melyseg): 0;
    }

    /**
     * Ha 0 a mélység, akkor 1 pontot ad, ha nem akkor a mélség és az erre adot bonus szorzatát adja.
     * @param melyseg
     * @return pontszám
     */
    private static int melysegBonus(final int melyseg) {
        return melyseg == 0 ? 1 : MELYSEG_BONUS * melyseg;
    }

    /**
     * Megnézi, hogy egy adott játékos sakkba kerül-e, ha a lépés bekövetkezik.
     * A pontot ez alapján adja meg.
     * @param jatekos
     * @return pontszám
     */
    private static int sakk(final Jatekos jatekos) {
        return jatekos.getEllenfel().ezSakk() ? SAKK_BONUS : 0;
    }

    /**
     * Megnézi egy bábu értékét a BabuTipus enum-ban.
     * @param jatekos
     * @return bébu értéke
     */
    private static int babuErtek(final Jatekos jatekos) {
        int babuErteke = 0;
        for(final Babu babu : jatekos.getAktivBabu()) {
            babuErteke += babu.getBabuErteke();
        }
        return babuErteke;
    }


}
