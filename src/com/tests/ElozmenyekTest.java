package com.tests;

import com.sakk.alap.elozmeny.Elozmeny;
import com.sakk.alap.elozmeny.ElozmenyekData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElozmenyekTest {
    ElozmenyekData elozmenyekData;

    @Before
    public void init() {
        elozmenyekData = new ElozmenyekData();
    }

    @Test
    /**
     * Teszteljük, hogy hozzá tudjuk- e adni egy új előzmény, valamint, hogy jól tudjuk-e módosítani.
     */
    public void addTest() {
        elozmenyekData.addElozmeny("2021-11-29 14:23:32", "Fehér", "sakkmatt");
        Assert.assertEquals(elozmenyekData.getValueAt(0,0), "2021-11-29 14:23:32");
        Assert.assertEquals(elozmenyekData.getValueAt(0,1), "Fehér");
        Assert.assertEquals(elozmenyekData.getValueAt(0,2), "sakkmatt");
        Assert.assertEquals(elozmenyekData.getColumnCount(), 3);
        Assert.assertEquals(elozmenyekData.getRowCount(), 1);
        Assert.assertEquals(elozmenyekData.getColumnName(0), "Mikor");
        Assert.assertEquals(elozmenyekData.getColumnName(1), "Ki");
        Assert.assertEquals(elozmenyekData.getColumnName(2), "Hogyan");
        Assert.assertEquals(elozmenyekData.getColumnClass(0), String.class);
        Assert.assertEquals(elozmenyekData.isCellEditable(0,0), false);
        elozmenyekData.setValueAt("2021-11-29 14:30:10",0,0);
        Assert.assertEquals(elozmenyekData.getValueAt(0,0), "2021-11-29 14:30:10");
        elozmenyekData.setValueAt("Fekete",0,1);
        Assert.assertEquals(elozmenyekData.getValueAt(0,1), "Fekete");
        elozmenyekData.setValueAt("patt",0,2);
        Assert.assertEquals(elozmenyekData.getValueAt(0,2), "patt");
    }

    @Test
    /**
     * Teszteli, hogy jól hozzuk-e létre az előzményeket.
     */
    public void elozmenyekTest() {
        Elozmeny elozmenyek = new Elozmeny("2021-11-29 14:23:32", "Fehér", "sakkmatt");
        Assert.assertEquals(elozmenyek.toString(),"2021-11-29 14:23:32;Fehér;sakkmatt\n");
    }
}