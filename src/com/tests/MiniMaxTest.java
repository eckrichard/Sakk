package com.tests;

import com.sakk.alap.jatekos.ai.MiniMax;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MiniMaxTest {
    MiniMax miniMax;
    Tabla alap;

    @Before
    public void init() {
        miniMax = new MiniMax(2);
        alap = Tabla.alapTablaKeszites();
    }

    @Test
    /**
     * Teszteljük a minimaxot.
     */
    public void minmaxTest() {
        Assert.assertEquals(miniMax.toString(), "Minimax");
        Lepes lepes = miniMax.kereses(alap);
        //Azért ez, mert ezt találtam a legjobbnak az interneten
        Lepes sajat = new Lepes.GyalogUgras(alap, alap.getMezo(4, 6).getBabu(), 4, 4);
        Assert.assertEquals(lepes, sajat);
    }
}