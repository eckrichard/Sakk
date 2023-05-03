package com.sakk.alap;

import com.sakk.alap.jatekos.FeherJatekos;
import com.sakk.alap.jatekos.FeketeJatekos;
import com.sakk.alap.jatekos.Jatekos;
import com.sakk.alap.tabla.TablaSeged;

/**
 * Megadja az egyes csapatokat
 * Azért white és black, hogy a kép betöltésnél ne legyen gond, mert a képek neve pl.:BB (fekete bástya)
 */
public enum Csapat {
    WHITE {
        public int getIrany(){
            return -1;
        }

        @Override
        public boolean Fehere() {
            return true;
        }

        @Override
        public boolean Feketee() {
            return false;
        }

        @Override
        public boolean ezAtalakulasHely(int x, int y) {
            return y == 0 ? true : false;
        }

        @Override
        public Jatekos jatekosValasztas(final FeherJatekos feherJatekos,final FeketeJatekos feketeJatekos) {
            return feherJatekos;
        }

        @Override
        public int getMasikIrany() {
            return 1;
        }
    },
    BLACK {
        public int getIrany(){
            return 1;
        }

        @Override
        public boolean Fehere() {
            return false;
        }

        @Override
        public boolean Feketee() {
            return true;
        }

        @Override
        public boolean ezAtalakulasHely(int x, int y) {
            return y == 7 ? true : false;
        }

        @Override
        public Jatekos jatekosValasztas(final FeherJatekos feherJatekos,final FeketeJatekos feketeJatekos) {
            return feketeJatekos;
        }

        @Override
        public int getMasikIrany() {
            return -1;
        }
    };

    public abstract int getIrany();
    public abstract boolean Fehere();
    public abstract boolean Feketee();
    public abstract boolean ezAtalakulasHely(int x, int y);
    public abstract Jatekos jatekosValasztas(FeherJatekos feherJatekos, FeketeJatekos feketeJatekos);
    public abstract int getMasikIrany();
}
