package com.sakk.alap.elozmeny;

import java.io.Serializable;

public class Elozmeny implements Serializable {

    private String ido;
    private String jatekos;
    private String hogyan;

    public String getIdo() {
        return ido;
    }

    public String getElozmenyekJatekos() {
        return jatekos;
    }

    public String getHogyan() {
        return hogyan;
    }

    public void setIdo(String ido) {
        this.ido = ido;
    }

    public void setElozmenyekJatekos(String jatekos) {
        this.jatekos = jatekos;
    }

    public void setHogyan(String hogyan) {
        this.hogyan = hogyan;
    }

    /**
     * Elözmény konstruktora
     * Stringként kap egy időpontot, egy csapatot és egy módot ahogyan a játékot valaki befejezte.
     * @param ido
     * @param jatekos
     * @param hogyan
     */
    public Elozmeny(final String ido, final String jatekos, final String hogyan){
        this.ido = ido;
        this.jatekos = jatekos;
        this.hogyan = hogyan;
    }
}
