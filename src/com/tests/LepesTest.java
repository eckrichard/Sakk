package com.tests;

import com.sakk.alap.Csapat;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.babu.Gyalog;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LepesTest {
    Lepes sima;
    Lepes tamado;
    Babu babu;
    Babu tamadt;
    Tabla alap;
    Lepes lepes;

    @Before
    public void init() {
        alap = Tabla.alapTablaKeszites();
        babu = new Gyalog(Csapat.WHITE, 0, 1, true);
        tamadt = new Gyalog(Csapat.WHITE, 1, 2, false);
        sima = new Lepes.SimaLepes(alap, babu, 0, 2);
        tamado = new Lepes.GyalogTamadoLepes(alap, babu, 1, 2, tamadt);
    }

    @Test
    /**
     * Teszteli a hash-t.
     */
    public void hashTest() {
        int eredmeny = 1;
        eredmeny = 31 * eredmeny + 16;
        eredmeny = 31 * eredmeny + babu.hashCode();
        eredmeny = 31 * eredmeny + 8;
        eredmeny = 31 * eredmeny + 1;
        Assert.assertEquals(eredmeny, sima.hashCode());
    }

    @Test
    /**
     * Teszteli a geteket.
     */
    public void getTest() {
        Assert.assertEquals(sima.getJelenlegiX(), 0);
        Assert.assertEquals(sima.getJelenlegiY(), 1);
        Assert.assertEquals(sima.getCelX(), 0);
        Assert.assertEquals(sima.getCelY(), 2);
        Assert.assertEquals(sima.getMozgoBabu(), babu);
        Assert.assertEquals(sima.getTabla(), alap);
        Assert.assertEquals(sima.getTamadotBabu(), null);
        Assert.assertEquals(sima.ezTamadas(), false);
        Assert.assertEquals(sima.ezSancolas(), false);
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals(sima.toString(), "a7->a6");
    }

    @Test
    /**
     * Teszteli a támadást.
     */
    public void tamadasTest() {
        Assert.assertEquals(tamado.ezTamadas(), true);
        Assert.assertEquals(tamado.getTamadotBabu(), tamadt);
    }

    @Test
    /**
     * Teszteli, hogy tudja-e teljesíteni az ugrást.
     */
    public void teljesitUgrasTest() {
        lepes = new Lepes.GyalogUgras(alap, alap.getMezo(0, 8).getBabu(), 0,4);
        Tabla tabla = lepes.teljesit();
        String jelenlegi = "  b  h  f  n  k  f  h  b\n" +
                "  g  g  g  g  g  g  g  g\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  G  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  G  G  G  G  G  G  G\n" +
                "  B  H  F  N  K  F  H  B\n";
        Assert.assertEquals(jelenlegi, tabla.toString());
    }

    @Test
    /**
     * Teszteli, hogy tudja-e teljesíteni az sima lépést.
     */
    public void teljesitTest() {
        lepes = new Lepes.SimaLepes(alap, alap.getMezo(6,7).getBabu(), 7,5);
        Tabla tabla = lepes.teljesit();
        String jelenlegi = "  b  h  f  n  k  f  h  b\n" +
                "  g  g  g  g  g  g  g  g\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  -\n" +
                "  -  -  -  -  -  -  -  H\n" +
                "  G  G  G  G  G  G  G  G\n" +
                "  B  H  F  N  K  F  -  B\n";
        Assert.assertEquals(jelenlegi, tabla.toString());
    }
}