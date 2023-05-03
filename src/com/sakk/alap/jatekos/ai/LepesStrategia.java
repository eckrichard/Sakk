package com.sakk.alap.jatekos.ai;

import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;

public interface LepesStrategia {

    /**
     * Megkeresi azt a lépést amire a legjobb pontszámot kapja.
     * @param tabla
     * @return a legjobb pontszámmal rendelkező lépés
     */
    Lepes kereses(Tabla tabla);

}
