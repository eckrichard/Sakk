package com.sakk.alap.jatekos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.sakk.alap.Csapat;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.babu.Kiraly;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Jatekos {
    protected final Tabla tabla;
    protected final Kiraly jatekosKiraly;
    protected final Collection<Lepes> ervenyesLepes;
    private final boolean ezSakk;

    /**
     * A Játékos abstract osztály konstruktora.
     * Ezt valósítja meg a fehér és a fekete játékos osztály.
     * @param tabla
     * @param jatekosErvenyes
     * @param ellenfelErvenyes
     */
    Jatekos(final Tabla tabla,final Collection<Lepes> jatekosErvenyes,final Collection<Lepes> ellenfelErvenyes){
        this.tabla = tabla;
        this.jatekosKiraly = kirajtletrehoz();
        this.ezSakk = !Jatekos.szamoltTamadasAMezon(this.jatekosKiraly.getX(), this.jatekosKiraly.getY(), ellenfelErvenyes).isEmpty();
        this.ervenyesLepes = ImmutableList.copyOf(Iterables.concat(jatekosErvenyes, szamoltSancol(jatekosErvenyes, ellenfelErvenyes)));
    }

    /**
     * Végigmegy a kapott lépéseken és, ha valamelyik támadás, akkor  hozzáadja a támadás listához.
     * Azt, hogy támadaás-e azt úgy határozza meg, hogy ha a lépés célkoordinátája a bábu poziciója,akkor támadó lépés.
     * @param lepesek
     * @return ImmutableListet add vissza, mert nem szeretnénk ha módosulna ez a lista, miben a támadások vannak
     */
    protected static Collection<Lepes> szamoltTamadasAMezon(int x, int y, Collection<Lepes> lepesek) {
        final List<Lepes> tamadas = new ArrayList<>();
        for(final Lepes lepes : lepesek){
            if(x == lepes.getCelX() && y == lepes.getCelY()){
                tamadas.add(lepes);
            }
        }
        return ImmutableList.copyOf(tamadas);
    }

    /**
     * Léterhozza a játékos királyát.
     * @return
     */
    private Kiraly kirajtletrehoz() {
        for(final Babu babu : getAktivBabu()) {
            if(babu.getBabuTipus().Kiraly()){
                return (Kiraly) babu;
            }
        }
        throw new RuntimeException("Nem lehet elérni!Nem érvényes a tábla!");
    }

    /**
     * Megvizsgálja, hogy egy lépés érvényes vagy sem.
     * @param lepes
     * @return
     */
    public boolean ezErvenyesLepes(final Lepes lepes){
        return this.ervenyesLepes.contains(lepes);
    }

    public boolean ezSakk(){
        return this.ezSakk;
    }

    /**
     * Megmondja, hogy egy szituáció sakk matt-e.
     * Ezt az alapján állapítja meg, hogy sakkban van-e és hogy létezik-e menekülő lépés.
     * @return
     */
    public boolean ezSakkMatt(){
        return this.ezSakk && !vanMenekuloLepes();
    }

    /**
     * Megnézi, hogy a lépések között ven-e olyan amivel tudna szabadulni a sakkból.
     * @return
     */
    public boolean vanMenekuloLepes() {
        for(final Lepes lepes : this.ervenyesLepes){
            final Athelyez athelyez = lep(lepes);
            if(athelyez.getLepesAllapot().Kesz()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Megmondja, hogy egy szituáció patt-e.
     * Ezt az alapján állapítja meg, hogy sakkban van-e és hogy létezik-e menekülő lépés.
     * @return
     */
    public boolean ezPatt(){
        return !this.ezSakk && !vanMenekuloLepes();
    }

    /**
     * Végig megy a lépéseken és megnézi, hogy melyik lépés sáncolás.
     * @return
     */
    public boolean ezSancolas(){
        for( Lepes lepes : ervenyesLepes) {
            if(lepes.ezSancolas()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Létrehoz egy Athelyez osztályt, amhol tároljuk azt, hogy melyik lépés segítségével csinálta,
     * a táblát ami a lépés elött volt és az új táblát, valamint a lépés állapotát.
     * Ha egy lépés érvénytelen vagy sakkban van és a lépés nem menekülő lépés, akkor az áthelyez osztályba lépés utáni táblaként a lépés elötti tábla kerül.
     * @param lepes
     * @return A megfelelő áthelyez osztállyal tér vissza.
     */
    public Athelyez lep(final Lepes lepes){
        if(!ezErvenyesLepes(lepes)){
            return new Athelyez(this.tabla, this.tabla, lepes,LepesAllapot.ERVENYTELEN_LEPES);
        }
        final Tabla atmentiTabla = lepes.teljesit();
        return atmentiTabla.getJelenlegiJatekos().getEllenfel().ezSakk() ?
                new Athelyez(this.tabla, this.tabla, lepes, LepesAllapot.SAKKBAN_MARAD) :
                new Athelyez(this.tabla, atmentiTabla, lepes, LepesAllapot.KESZ);
    }

    public Kiraly getJatekosKiraly(){
        return this.jatekosKiraly;
    }

    public Collection<Lepes> getErvenyesLepes(){
        return this.ervenyesLepes;
    }

    /**
     * Visszavonást valósítja meg,
     * ahol az áthelyez osztálynak a jelenlegi mezője a lépéssel létrehozot tábla lesz,
     * az új pedig az ami a lépés elött volt.
     * @param lepes
     * @return A megfelelő áthelyez osztállyal tér vissza.
     */
    public Athelyez visszavonLepesKeszites(final Lepes lepes) {
        return new Athelyez(this.tabla, lepes.visszavon(), lepes, LepesAllapot.KESZ);
    }

    public abstract Collection<Babu> getAktivBabu();
    public abstract Csapat getCsapat();
    public abstract Jatekos getEllenfel();
    protected abstract Collection<Lepes> szamoltSancol(Collection<Lepes> jatekosErvenyesLepesei, Collection<Lepes> ellenfelErvenyesLepesei);
    public abstract  String toString();
}
