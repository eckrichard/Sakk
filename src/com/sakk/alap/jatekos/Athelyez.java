package com.sakk.alap.jatekos;

import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;

/**
 * Ez egy olyan osztály, amelyben tároljuk az előző és a lépés utáni táblát is.
 * (Ez az osztály főként a visszavonásnál jön jól.)
 * Tároljuk még a lépést, amivel létrejön az új tábla, valamint, hogy milyen állapotban van a lépés.
 */
public class Athelyez {
    private final Tabla jelenlegiTabla;
    private final Tabla atmentiTabla;
    private final Lepes lepes;
    private final LepesAllapot lepesAllapot;

    /**
     * Athelyez osztály kontruktora.
     * @param jelenlegiTabla
     * @param atmentiTabla
     * @param lepes
     * @param lepesAllapot
     */
    public Athelyez(final Tabla jelenlegiTabla,final Tabla atmentiTabla, final Lepes lepes,final LepesAllapot lepesAllapot){
        this.jelenlegiTabla = jelenlegiTabla;
        this.atmentiTabla = atmentiTabla;
        this.lepes = lepes;
        this.lepesAllapot = lepesAllapot;
    }

    public LepesAllapot getLepesAllapot() {
        return this.lepesAllapot;
    }

    public Tabla getAtmenetiTabla() {
        return this.atmentiTabla;
    }

    public Tabla getJelenlegiTabla(){
        return this.jelenlegiTabla;
    }

    public Lepes getLepes() {
        return this.lepes;
    }
}
