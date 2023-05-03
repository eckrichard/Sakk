package com.tests;

import com.sakk.alap.Csapat;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.babu.Gyalog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BabuTest {
    Babu babu;

    @Before
    /**
     * Létrehoz egy bábut.
     */
    public void init() {
        babu = new Gyalog(Csapat.WHITE, 0, 1, true);
    }

    @Test
    /**
     * Teszteli a hash-t.
     */
    public void hashTest() {
        int eredmeny = Babu.BabuTipus.GYALOG.hashCode();
        eredmeny = 31 * eredmeny + Csapat.WHITE.hashCode();
        eredmeny = 31 * eredmeny + 8;
        eredmeny = 31 * eredmeny + 1;
        Assert.assertEquals(eredmeny, babu.hashCode());
    }

    @Test
    /**
     * Teszteli, hogy mőködik-e a pozició átállítás.
     */
    public void pozicioTest() {
        babu.setX(1);
        babu.setY(1);
        Assert.assertEquals(babu.getX(), (Integer) 1);
        Assert.assertEquals(babu.getY(), (Integer) 1);
    }

    @Test
    /**
     * Megnézi, hogy megfelelő értéket kapjuk-e a bábuhoz.
     */
    public void ertekTest() {
        Assert.assertEquals(babu.getBabuErteke(), 100);
    }

}