package com.tests;

import com.sakk.alap.jatekos.Jatekos;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.sakk.alap.tabla.Tabla.*;
import static org.junit.Assert.*;

public class TablaTest {
    Tabla alap;

    @Before
    public void init() {
        this.alap = Tabla.alapTablaKeszites();
    }

    @Test
    /**
     * Teszteljük, hogy jó-e az alaptábla létrehozása.
     */
    public void alapTablaTest(){
        String kezdet = "  b  h  f  n  k  f  h  b\n" +
                        "  g  g  g  g  g  g  g  g\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  G  G  G  G  G  G  G  G\n" +
                        "  B  H  F  N  K  F  H  B\n";
        Assert.assertEquals(kezdet, alap.toString());
    }

    @Test
    /**
     * Teszteljük, hogy jól csináljuk az az ervényes lépés számítását.
     */
    public void szamoltErvenyesTest() {
        Tabla alap = Tabla.alapTablaKeszites();
        int lepesekszama = 0;
        for(final Lepes lepes : alap.getOsszesErvenyesLepes()) {
            lepesekszama++;
        }
        Assert.assertEquals(lepesekszama, 40);
    }

    @Test
    /**
     * Teszteljük a geteket.
     */
    public void getTest() {
        Assert.assertEquals(alap.getJelenlegiJatekos().toString(), "Fehér");
        Assert.assertEquals(alap.feherJatekos().toString(), "Fehér");
        Assert.assertEquals(alap.feketeJatekos().toString(), "Fekete");
    }
}