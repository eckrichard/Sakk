package com.sakk.alap.jatekos.ai;

import com.sakk.alap.tabla.Tabla;

public interface TablaErtekelo {

    /**
     * Kiértékeli, hogy az egyes lépésekre mennyi pont jár.
     * @param tabla
     * @param melyseg
     * @return
     */
    int ertekel(Tabla tabla, int melyseg);

}
