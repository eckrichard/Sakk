package com.sakk.alap.tabla;

import com.sakk.alap.babu.Babu;
import com.sakk.alap.babu.Bastya;
import com.sakk.alap.babu.Gyalog;
import com.sakk.gui.Ablak;

import static com.sakk.alap.tabla.Tabla.*;

public abstract class Lepes {

    protected final Tabla tabla;
    protected final Babu mozgoBabu;
    protected int celX;
    protected int celY;
    protected final boolean elsoLepes;

    private Lepes(final Tabla tabla, final Babu mozgoBabu, final int x, final int y){
        this.tabla = tabla;
        this.celX = x;
        this.celY = y;
        this.mozgoBabu = mozgoBabu;
        this.elsoLepes = mozgoBabu.elsoLepes();
    }

    private Lepes(final Tabla tabla, final int x, final int y){
        this.tabla = tabla;
        this.celX = x;
        this.celY = y;
        this.mozgoBabu = null;
        this.elsoLepes = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final Lepes masikLepes)) return false;
        return celX == masikLepes.getCelX() &&
                celY == masikLepes.getCelY() &&
                getMozgoBabu().equals(masikLepes.getMozgoBabu()) &&
                getJelenlegiX() == masikLepes.getJelenlegiX() &&
                getJelenlegiY() == masikLepes.getJelenlegiY();
    }

    @Override
    public int hashCode() {
        int eredmeny = 1;
        eredmeny = 31 * eredmeny + this.getCelX() + (8 * this.getCelY());
        eredmeny = 31 * eredmeny + this.mozgoBabu.hashCode();
        eredmeny = 31 * eredmeny + (this.mozgoBabu.getX() + (8 * this.mozgoBabu.getY()));
        eredmeny = 31 * eredmeny + (elsoLepes ? 1 : 0);
        return eredmeny;
    }

    public int getJelenlegiX() {
        return this.mozgoBabu.getX();
    }

    public int getJelenlegiY() {
        return this.mozgoBabu.getY();
    }

    public int getCelX() {
        return this.celX;
    }

    public int getCelY() {
        return this.celY;
    }

    public Babu getMozgoBabu() {
        return mozgoBabu;
    }

    public Tabla getTabla() {
        return tabla;
    }

    public boolean ezTamadas(){
        return false;
    }

    public boolean ezSancolas(){
        return false;
    }

    public Babu getTamadotBabu() {
        return null;
    }

    /**
     * Létrehoz egy új tábla építőt, majd beállítja a léppéssel keletkező új felállást.
     * Ha megvan a felállás, akkor az jelenlegi játékost átállítja.
     * Végül az építő segítségével létrehozza az új táblát.
     * @return az új tábla
     */
    public Tabla teljesit() {
        final Epito epito = new Epito();
        for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
            if(!this.mozgoBabu.equals(babu)){
                epito.setBabu(babu);
            }
        }
        for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()){
            epito.setBabu(babu);
        }
        epito.setBabu(this.mozgoBabu.mozgoBabu(this));
        epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getEllenfel().getCsapat());
        return epito.epit();
    }

    /**
     * Visszavonja a lépést.
     * Létrehoz egy új tábla építőt és
     * a lépés cél koordinátáját a lépés létrehozásakor lévő jelenlegi koordinátát állítja,
     * majd beállítja a léppéssel keletkező új felállást.
     * Ha megvan a felállás, akkor az jelenlegi játékost átállítja.
     * Végül az építő segítségével létrehozza az új táblát.
     * @return
     */
    public Tabla visszavon() {
        final Epito epito = new Epito();
        celX = getJelenlegiX();
        celY = getJelenlegiY();
        for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
            if(!this.mozgoBabu.equals(babu)){
                epito.setBabu(babu);
            }
        }
        for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()){
            epito.setBabu(babu);
        }
        epito.setBabu(this.mozgoBabu.mozgoBabu(this, elsoLepes));
        epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getCsapat());
        return epito.epit();
    }

    public String toString() {
        return tabla.getMezo(getJelenlegiX(),getJelenlegiY()).mezoneve(mozgoBabu.getCsapatBabu().getIrany()) + "->" + tabla.getMezo(getCelX(),getCelY()).mezoneve(mozgoBabu.getCsapatBabu().getIrany());
    }

    public static class SimaLepes extends Lepes {
        public SimaLepes(final Tabla tabla,final Babu mozgoBabu,final int x, final int y) {
            super(tabla, mozgoBabu, x, y);
        }

        @Override
        public boolean equals(Object o) {
            return this == o || o instanceof SimaLepes && super.equals(o);
        }
    }

    public static class TamadoLepes extends Lepes {
        final Babu tamadotBabu;

        public TamadoLepes(final Tabla tabla,final Babu mozgoBabu,final int x, final int y, final Babu tamadotBabu) {
            super(tabla, mozgoBabu, x, y);
            this.tamadotBabu = tamadotBabu;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof final TamadoLepes masikTamadoLepes)) return false;
            if (!super.equals(o)) return false;
            return super.equals(masikTamadoLepes) && getTamadotBabu().equals(masikTamadoLepes.getTamadotBabu());
        }

        @Override
        public int hashCode() {
            return this.tamadotBabu.hashCode() + super.hashCode();
        }

        @Override
        public boolean ezTamadas() {
            return true;
        }

        @Override
        public Babu getTamadotBabu() {
            return this.tamadotBabu;
        }
    }

    public static final class GyalogLepes extends Lepes {
        public GyalogLepes(final Tabla tabla,final Babu mozgoBabu,final int x, final int y) {
            super(tabla, mozgoBabu, x, y);
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || o instanceof GyalogLepes && super.equals(o);
        }
    }

    public static class GyalogTamadoLepes extends TamadoLepes {
        public GyalogTamadoLepes(final Tabla tabla,final Babu mozgoBabu,final int x, final int y,  final Babu tamadtBabu) {
            super(tabla, mozgoBabu, x, y, tamadtBabu);
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || o instanceof GyalogTamadoLepes && super.equals(o);
        }
    }

    //En Passant különleges lépés a sakkban, olyan ütés, amelyre akkor kerülhet sor, ha az egyik fél gyalogja kettőt
    // előrelépve kikerülné az ellenfél gyalogjának átlós ütését. Az egyetlen eset a sakkban, amikor egy gyalog nem az
    // átlósan előtte álló, hanem a mellé lépő figurát ütheti ki.
    public static final class GyalogEnPassantTamadoLepes extends GyalogTamadoLepes {
        public GyalogEnPassantTamadoLepes(final Tabla tabla,final Babu mozgoBabu,final int x, final int y,  final Babu tamadtBabu) {
            super(tabla, mozgoBabu, x, y, tamadtBabu);
        }

        @Override
        public Tabla teljesit() {
            final Epito epito = new Epito();
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
                if(!(this.mozgoBabu.equals(babu))){
                    epito.setBabu(babu);
                }
            }
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()) {
                if(!(this.tamadotBabu.equals(babu))){
                    epito.setBabu(babu);
                }
            }
            final Gyalog mozgoGyalog = (Gyalog)this.mozgoBabu.mozgoBabu(this);
            tamadotBabu.setX(celX);
            tamadotBabu.setY(celY);
            epito.setBabu(mozgoGyalog);
            epito.setEnPassantGyalog(mozgoGyalog);
            epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getEllenfel().getCsapat());
            return epito.epit();
        }

        @Override
        public Tabla visszavon() {
            final Epito epito = new Epito();
            celX = getJelenlegiX();
            celY = getJelenlegiY();
            this.getTamadotBabu().setY(this.getTamadotBabu().getY() + this.getTamadotBabu().getCsapatBabu().getIrany());
            this.getTamadotBabu().setX(this.getTamadotBabu().getX());
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
                if(!this.mozgoBabu.equals(babu)){
                    epito.setBabu(babu);
                }
            }
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()){
                epito.setBabu(babu);
            }
            epito.setBabu(this.mozgoBabu.mozgoBabu(this));
            epito.setEnPassantGyalog((Gyalog) this.getTamadotBabu());
            epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getCsapat());
            return epito.epit();
        }
    }

    public static class GyalogAtalakul extends Lepes {

        final Lepes lepes;
        final Gyalog atalakuloGyalog;

        public GyalogAtalakul(final Lepes lepes){
            super(lepes.getTabla(), lepes.getMozgoBabu(), lepes.celX, lepes.celY);
            this.lepes = lepes;
            this.atalakuloGyalog = (Gyalog) lepes.getMozgoBabu();
        }

        @Override
        public Tabla teljesit() {
            final Tabla ujTabla = this.lepes.teljesit();
            final Epito epito = new Epito();
            for(final Babu babu : ujTabla.getJelenlegiJatekos().getAktivBabu()) {
                if(!this.atalakuloGyalog.equals(babu)) {
                    epito.setBabu(babu);
                }
            }
            for(final Babu babu: ujTabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()) {
                epito.setBabu(babu);
            }
            epito.setBabu(this.atalakuloGyalog.getAtalakuloGyalog(Ablak.get().getAtalakulas().getBabu()).mozgoBabu(this));
            epito.setLepesKeszito(ujTabla.getJelenlegiJatekos().getCsapat());
            return epito.epit();
        }

        @Override
        public boolean ezTamadas() {
            return this.lepes.ezTamadas();
        }

        @Override
        public Babu getTamadotBabu() {
            return this.lepes.getTamadotBabu();
        }

        @Override
        public String toString() {
            return atalakuloGyalog.toString() + "->" + atalakuloGyalog.getAtalakuloGyalog(Ablak.get().getAtalakulas().getBabu()).mozgoBabu(this).toString();
        }

        @Override
        public boolean equals(Object o) {
            return this == o || o instanceof GyalogAtalakul && (this.lepes.equals(o));
        }

        @Override
        public int hashCode() {
            return lepes.hashCode() + (31 * atalakuloGyalog.hashCode());
        }
    }

    public static final class GyalogUgras extends Lepes {
        public GyalogUgras(final Tabla tabla,final Babu mozgoBabu,final int x, final int y) {
            super(tabla, mozgoBabu, x, y);
        }

        @Override
        public Tabla teljesit() {
            final Epito epito = new Epito();
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
                if(!(this.mozgoBabu.equals(babu))){
                    epito.setBabu(babu);
                }
            }
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()) {
                epito.setBabu(babu);
            }
            final Gyalog mozgoGyalog = (Gyalog)this.mozgoBabu.mozgoBabu(this);
            epito.setBabu(mozgoGyalog);
            epito.setEnPassantGyalog(mozgoGyalog);
            epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getEllenfel().getCsapat());
            return epito.epit();
        }
    }

    static abstract class Sancolas extends Lepes {
        protected final Bastya sancoloBastya;
        protected final int sancoloBastyaHelyeX;
        protected final int sancoloBastyaHelyeY;
        protected final int sancoloBastyaCelX;
        protected final int sancoloBastyaCelY;
        public Sancolas(final Tabla tabla,final Babu mozgoBabu,final int x, final int y, final Bastya sancoloBastya,
                        final int bastyaJelenlegiX, final int bastyaJelenlegiY, final int bastyaCelX, final int bastyaCelY) {
            super(tabla, mozgoBabu, x, y);
            this.sancoloBastya = sancoloBastya;
            this.sancoloBastyaHelyeX = bastyaJelenlegiX;
            this.sancoloBastyaHelyeY = bastyaJelenlegiY;
            this.sancoloBastyaCelX = bastyaCelX;
            this.sancoloBastyaCelY = bastyaCelY;
        }
        public Bastya getSancoloBastya() {
            return this.sancoloBastya;
        }

        @Override
        public boolean ezSancolas() {
            return true;
        }

        @Override
        public Tabla teljesit() {
            final Epito epito = new Epito();
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getAktivBabu()){
                if(!(this.mozgoBabu.equals(babu)) && !this.sancoloBastya.equals(babu)){
                    epito.setBabu(babu);
                }
            }
            for(final Babu babu : this.tabla.getJelenlegiJatekos().getEllenfel().getAktivBabu()) {
                epito.setBabu(babu);
            }
            epito.setBabu(this.mozgoBabu.mozgoBabu(this));
            epito.setBabu(new Bastya(this.sancoloBastya.getCsapatBabu(), this.sancoloBastyaCelX, this.sancoloBastyaCelY));
            epito.setLepesKeszito(this.tabla.getJelenlegiJatekos().getEllenfel().getCsapat());
            return epito.epit();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if(!(o instanceof final Sancolas masik)) {
                return false;
            }
            return super.equals(masik) && this.sancoloBastya.equals(masik.getSancoloBastya());
        }

        @Override
        public int hashCode() {
            int eredmeny = super.hashCode();
            eredmeny = 31 * eredmeny + this.sancoloBastya.hashCode();
            eredmeny = 31 * eredmeny + this.sancoloBastyaHelyeX + (this.sancoloBastyaHelyeY * 8);
            return eredmeny;
        }
    }

    public static final class jobbraSancol extends Sancolas {
        public jobbraSancol(final Tabla tabla,final Babu mozgoBabu,final int x, final int y, final Bastya sancoloBastya,
                            final int bastyaJelenlegiX, final int bastyaJelenlegiY, final int bastyaCelX, final int bastyaCelY) {
            super(tabla, mozgoBabu, x, y, sancoloBastya, bastyaJelenlegiX, bastyaJelenlegiY, bastyaCelX, bastyaCelY);
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || o instanceof jobbraSancol && super.equals(o);
        }

        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class balraSancol extends Sancolas {
        public balraSancol(final Tabla tabla,final Babu mozgoBabu,final int x, final int y, final Bastya sancoloBastya,
                           final int bastyaJelenlegiX, final int bastyaJelenlegiY, final int bastyaCelX, final int bastyaCelY) {
            super(tabla, mozgoBabu, x, y, sancoloBastya, bastyaJelenlegiX, bastyaJelenlegiY, bastyaCelX, bastyaCelY);
        }

        @Override
        public boolean equals(final Object o) {
            return this == o || o instanceof balraSancol && super.equals(o);
        }

        @Override
        public String toString() {
            return "0-0-0";
        }
    }

    private static final class NullLepes extends Lepes {
        public NullLepes() {
            super(null, -1, -1);
        }

        public Tabla teljesit() {
            throw new RuntimeException("Nem teljesíthető a null lépés!");
        }
    }

    public static class LepesKeszito {
        private static final Lepes NULL_MOVE = new NullLepes();

        private LepesKeszito() {
            throw new RuntimeException("Hiba!");
        }

        /**
         * Megvizsgálja, hogy a paraméterben kapott attributumokkal rendelkező lépés benne van-e az érvényes lépésekben.
         * @return az a lépés amire teljseül
         */
        public static Lepes lepestKeszit(final Tabla tabla, final int jelenlegiX, final int jelenlegiY, final int celX, final int celY){
            for(final Lepes lepes : tabla.getOsszesErvenyesLepes()) {
                if(lepes.getJelenlegiX() == jelenlegiX && lepes.getJelenlegiY() == jelenlegiY &&
                        lepes.getCelX() == celX && lepes.getCelY() == celY){
                    return lepes;
                }
            }
            return NULL_MOVE;
        }
    }
}