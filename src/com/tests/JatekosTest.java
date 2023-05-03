package com.tests;

import com.google.common.collect.Iterables;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.jatekos.Athelyez;
import com.sakk.alap.jatekos.Jatekos;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JatekosTest {
    Lepes lepes;
    Lepes sajat;
    Lepes rossz;
    Jatekos feherJatekos;
    Tabla alap;
    Babu babu;

    @Before
    public void init() {
        alap = Tabla.alapTablaKeszites();
        feherJatekos = alap.feherJatekos();
        babu = alap.getMezo(0, 6).getBabu();
        sajat = new Lepes.GyalogLepes(alap, babu, 0,5);
        rossz = new Lepes.SimaLepes(alap,babu, 0,5);
        lepes = Iterables.get(feherJatekos.getErvenyesLepes(), 0);
    }

    @Test
    /**
     * Teszteli, hogy a lépés után jó lesz a tábla.
     * Teszteli még a lépések összehasonlítását, valamint hogy van-e sak és menkülő lépés.
     */
    public void  lepTest() {
        Athelyez athelyez = feherJatekos.lep(lepes);
        String tabla = "  b  h  f  n  k  f  h  b\n" +
                        "  g  g  g  g  g  g  g  g\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  -  -  -  -  -  -  -  -\n" +
                        "  G  -  -  -  -  -  -  -\n" +
                        "  -  G  G  G  G  G  G  G\n" +
                        "  B  H  F  N  K  F  H  B\n";
        Assert.assertEquals(sajat, lepes);
        Assert.assertEquals(athelyez.getLepes(), lepes);
        Assert.assertEquals(athelyez.getAtmenetiTabla().toString(), tabla);
        Assert.assertNotEquals(lepes, rossz);
        Assert.assertEquals(feherJatekos.ezSakkMatt(), false);
        Assert.assertEquals(feherJatekos.vanMenekuloLepes(), true);
    }

}