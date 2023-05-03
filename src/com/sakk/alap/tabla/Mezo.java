package com.sakk.alap.tabla;

import com.sakk.alap.babu.Babu;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Mezo {
    protected final int x;
    protected final int y;

    private static final Map<Integer, uresMezo> URES_MEZO_CACHE = createOsszLehetsegesMezo();

    /**
     * Létrehoz egy HashMap-et, amibe belehelyezi az összes mezőt.
     * @return mezőket tartalmazőó HashMap
     */
    private static Map<Integer, uresMezo> createOsszLehetsegesMezo() {
        final Map<Integer, uresMezo> uresMezoMap = new HashMap<>();
        for(int x = 0; x < TablaSeged.OSZLOPOK_SZAMA; x++) {
            for(int y = 0; y < TablaSeged.MEZOK_EGY_SORBAN; y++) {
                uresMezoMap.put(x+(y*8), new uresMezo(x,y));
            }
        }
        return ImmutableMap.copyOf(uresMezoMap);
    }

    /**
     * Létrehoz egy foglalt mezőt vagy pedig egy üres mezőt ad vissza, ami benne URES_MEZO_CACHE-ben.
     */
    public static Mezo Mezokeszites(final int x, final int y, final Babu babu) {
        return babu != null ? new foglaltMezo(x,y, babu) : URES_MEZO_CACHE.get(x+(y*8));
    }

    private Mezo(final int x, final int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Megadjuk a mezők neveit.
     */
    public String mezoneve(final int irany) {
        if(irany == 1) {
            switch (this.x) {
                case 0: return "a"+(y+1);
                case 1: return "b"+(y+1);
                case 2: return "c"+(y+1);
                case 3: return "d"+(y+1);
                case 4: return "e"+(y+1);
                case 5: return "f"+(y+1);
                case 6: return "g"+(y+1);
                case 7: return "h"+(y+1);
                default: return "Nincs ilyen mező";
            }
        }
        else {
            switch (this.x) {
                case 0: return "a"+(-1*y+8);
                case 1: return "b"+(-1*y+8);
                case 2: return "c"+(-1*y+8);
                case 3: return "d"+(-1*y+8);
                case 4: return "e"+(-1*y+8);
                case 5: return "f"+(-1*y+8);
                case 6: return "g"+(-1*y+8);
                case 7: return "h"+(-1*y+8);
                default: return "Nincs ilyen mező";
            }
        }
    }

    public abstract boolean foglalte();

    public abstract Babu getBabu();

    public int getX() { return x; }

    public int getY() { return y; }

    public static final class uresMezo extends Mezo {
        uresMezo(final int x, final int y){
            super(x,y);
        }
        public boolean foglalte() {
            return false;
        }
        public Babu getBabu() {
            return null;
        }
        public String toString(){ return "-";}
    }

    public static final class foglaltMezo extends Mezo{
        private final Babu Babu_a_mezon;

        foglaltMezo(final int x, final int y,final Babu Babu_a_mezon){
            super(x,y);
            this.Babu_a_mezon = Babu_a_mezon;
        }
        public boolean foglalte() {
            return true;
        }
        public Babu getBabu() {
            return this.Babu_a_mezon;
        }
        public String toString(){
            return getBabu().getCsapatBabu().Feketee() ? getBabu().toString().toLowerCase() : getBabu().toString();
        }
    }
}
