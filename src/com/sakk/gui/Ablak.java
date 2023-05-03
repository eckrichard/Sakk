package com.sakk.gui;

import com.google.common.collect.Lists;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.jatekos.Athelyez;
import com.sakk.alap.jatekos.ai.LepesStrategia;
import com.sakk.alap.jatekos.ai.MiniMax;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Mezo;
import com.sakk.alap.tabla.Tabla;
import com.sakk.alap.tabla.TablaSeged;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Ablak extends Observable {
    private final JFrame jatekFrame;
    private final TablaPanel tablaPanel;
    private final Lepesek lepesek;
    private final LepesElozmenyek lepesElozmenyek;
    private final LevettBabuk levettBabuk;
    private final Beallitas beallitas;
    private Tabla sakkTabla;
    private Mezo jelenlegiMezo;
    private Mezo celMezo;
    private Babu emberMozgoBabuja;
    private TablaIranya tablaIranya;
    private Lepes AILepes;
    private final AtalakuloBabu atalakuloBabu;
    private ElozmenyekPanel elozmenyekPanel;

    private Color vilagosMezoSzin = Color.decode("#FFFACD");
    private Color sotetMezoSzin = Color.decode("#593E1A");

    private final static Dimension ABLAK_MERETE = new Dimension(725,600);
    private final static Dimension TABLA_MERETE = new Dimension(400,350);
    private final static Dimension MEZO_MEREZE = new Dimension(10,10);
    private final static String defaultBabuKepekHelye = "kepek/icons/";

    private static final Ablak ABLAK = new Ablak();

    public Ablak() {
        this.jatekFrame = new JFrame("Java Sakk");
        this.jatekFrame.setLayout(new BorderLayout());
        final JMenuBar jMenuBar = menubar();
        this.jatekFrame.setJMenuBar(jMenuBar);
        this.jatekFrame.setSize(ABLAK_MERETE);
        this.sakkTabla = Tabla.alapTablaKeszites();
        this.lepesElozmenyek = new LepesElozmenyek();
        this.levettBabuk = new LevettBabuk();
        this.tablaPanel = new TablaPanel();
        this.lepesek = new Lepesek();
        this.elozmenyekPanel = new ElozmenyekPanel(this.jatekFrame, true);
        this.addObserver(new AINezo());
        this.beallitas = new Beallitas(this.jatekFrame, true);
        this.atalakuloBabu = new AtalakuloBabu(this.jatekFrame, true);
        this.tablaIranya = TablaIranya.NORMALIS;
        this.jatekFrame.add(this.tablaPanel, BorderLayout.CENTER);
        this.jatekFrame.add(lepesElozmenyek, BorderLayout.EAST);
        this.jatekFrame.add(levettBabuk, BorderLayout.WEST);
        this.jatekFrame.setVisible(true);
        this.jatekFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static Ablak get(){
        return ABLAK;
    }

    protected Lepesek getLepesek(){
        return this.lepesek;
    }

    private LepesElozmenyek getLepesElozmenyek() {
        return this.lepesElozmenyek;
    }

    private LevettBabuk getLevettBabuk(){
        return this.levettBabuk;
    }

    private Beallitas getBeallitas() {
        return this.beallitas;
    }

    private JFrame getJatekFrame() {
        return this.jatekFrame;
    }

    TablaPanel getTablaPanel() {
        return this.tablaPanel;
    }

    public ElozmenyekPanel getElozmenyekPanel() {return this.elozmenyekPanel;}

    Tabla getTabla() {
        return sakkTabla;
    }

    public AtalakuloBabu getAtalakulas() {
        return atalakuloBabu;
    }

    private ElozmenyekPanel getElozmenyek() { return elozmenyekPanel;}

    /**
     * Frissíti, hogy lépés történt (AI használja)
     * @param jatekosTipus
     */
    private void frissitLepes(JatekosTipus jatekosTipus) {
        setChanged();
        notifyObservers(jatekosTipus);
    }

    /**
     * AI lépését frissíti
     * @param lepes
     */
    private void frissitAILepes(final Lepes lepes) {
        this.AILepes = lepes;
    }

    /**
     * Frissíti a táblát
     * @param tabla
     */
    private void frissitTabla(final Tabla tabla) {
        this.sakkTabla = tabla;
    }

    /**
     * Képernyőre teszi az ablak framet.
     */
    public void mutat() {
        Ablak.get().getLepesek().clear();
        Ablak.get().getLepesElozmenyek().hasznal(sakkTabla,Ablak.get().getLepesek());
        Ablak.get().getLevettBabuk().hasznal(Ablak.get().getLepesek());
    }

    /**
     * Menübár létrehozása
     * @return
     */
    private JMenuBar menubar() {
        final JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(JatekMenu());
        jMenuBar.add(TablaMenu());
        jMenuBar.add(ElozmenyekMenu());
        return jMenuBar;
    }

    /**
     * Játék menüt hozza létre
     * @return
     */
    private JMenu JatekMenu() {
        final JMenu jatekMenu = new JMenu("Játék");
        final JMenuItem uj_jatek = new JMenuItem("Új játék", KeyEvent.VK_P);
        uj_jatek.addActionListener(e -> {
            visszavonMindenLepes();
            elozmenyekPanel.beolvas();
        });
        jatekMenu.add(uj_jatek);
        final JMenu beallitasokSubMenu = new JMenu("Beállítások");
        beallitasokSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem beallitasok = new JMenuItem("Beállítások");
        beallitasokSubMenu.add(beallitasok);

        final JMenuItem atalakulas = new JMenuItem("Átalakulás beállítása");
        beallitasokSubMenu.add(atalakulas);

        beallitasok.addActionListener(e -> {
            Ablak.get().getBeallitas().hasznal();
            Ablak.get().beallitasFrissitese(Ablak.get().getBeallitas());
        });

        atalakulas.addActionListener(e -> Ablak.get().getAtalakulas().hasznal());

        jatekMenu.add(beallitasokSubMenu);
        return jatekMenu;
    }

    /**
     * Frissíti a beálllításokat
     * @param beallitas
     */
    private void beallitasFrissitese(final Beallitas beallitas) {
        setChanged();
        notifyObservers(beallitas);
    }

    /**
     * Visszavonja, az össze lépést ami történt a játékban
     */
    private void visszavonMindenLepes() {
        for(int i = Ablak.get().getLepesek().size() - 1; i >= 0; i--) {
            final Lepes utolsoLepes = Ablak.get().getLepesek().removeLepes(Ablak.get().getLepesek().size() - 1);
            this.sakkTabla = this.sakkTabla.getJelenlegiJatekos().visszavonLepesKeszites(utolsoLepes).getAtmenetiTabla();
        }
        this.AILepes = null;
        Ablak.get().getLepesek().clear();
        Ablak.get().getLepesElozmenyek().hasznal(sakkTabla, Ablak.get().getLepesek());
        Ablak.get().getLevettBabuk().hasznal(Ablak.get().getLepesek());
        Ablak.get().getTablaPanel().tablaRajzol(sakkTabla);
    }

    /**
     * Visszavonja az utolsó lépést
     */
    protected void visszavonUtolsoLepes() {
        final Lepes utolsoLepes = Ablak.get().getLepesek().removeLepes(Ablak.get().getLepesek().size() - 1);
        this.sakkTabla = this.sakkTabla.getJelenlegiJatekos().visszavonLepesKeszites(utolsoLepes).getAtmenetiTabla();
        this.AILepes = null;
        Ablak.get().getLepesek().removeLepes(utolsoLepes);
        Ablak.get().getLepesElozmenyek().hasznal(sakkTabla, Ablak.get().getLepesek());
        Ablak.get().getLevettBabuk().hasznal(Ablak.get().getLepesek());
        Ablak.get().getTablaPanel().tablaRajzol(sakkTabla);
    }

    /**
     * Figyeli, hogy az AI léphet-e.
     */
    private static class AINezo implements Observer {

        @Override
        public void update(final Observable o, final Object arg) {
            if(Ablak.get().getBeallitas().ezAIJatekos(Ablak.get().getTabla().getJelenlegiJatekos()) &&
                    !Ablak.get().getTabla().getJelenlegiJatekos().ezSakkMatt() &&
                    !Ablak.get().getTabla().getJelenlegiJatekos().ezPatt()) {
                final AIHasznalata hasznal = new AIHasznalata();
                hasznal.execute();
            }

            if(Ablak.get().getTabla().getJelenlegiJatekos().ezSakkMatt()) {
                ElozmenyekPanel elozmenyek = new ElozmenyekPanel();
                elozmenyek.kiir(Ablak.get().getTabla().getJelenlegiJatekos(), "sakkmatt");
                JOptionPane.showMessageDialog(Ablak.get().getTablaPanel(), "Játék vége: " + Ablak.get().getTabla().getJelenlegiJatekos() +
                        " sakkmattot kapott!", "Játék vége", JOptionPane.INFORMATION_MESSAGE);
            }

            if(Ablak.get().getTabla().getJelenlegiJatekos().ezPatt()) {
                ElozmenyekPanel elozmenyek = new ElozmenyekPanel();
                elozmenyek.kiir(Ablak.get().getTabla().getJelenlegiJatekos(), "patt");
                JOptionPane.showMessageDialog(Ablak.get().getTablaPanel(), "Játék vége: " + Ablak.get().getTabla().getJelenlegiJatekos() +
                        " pattot kapott!", "Játék vége", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Használja az AI-t
     */
    private static class AIHasznalata extends SwingWorker<Lepes, String> {
        private AIHasznalata() {}
        @Override
        protected Lepes doInBackground() {
            final LepesStrategia minimax = new MiniMax(Ablak.get().getBeallitas().getKeresesiMelyseg());
            return minimax.kereses(Ablak.get().getTabla());
        }
        @Override
        public void done() {
            try {
                final Lepes legjobbLepes = get();
                Ablak.get().frissitAILepes(legjobbLepes);
                Ablak.get().frissitTabla(Ablak.get().getTabla().getJelenlegiJatekos().lep(legjobbLepes).getAtmenetiTabla());
                Ablak.get().getLepesek().addLepes(legjobbLepes);
                Ablak.get().getLepesElozmenyek().hasznal(Ablak.get().getTabla(), Ablak.get().getLepesek());
                Ablak.get().getLevettBabuk().hasznal(Ablak.get().getLepesek());
                Ablak.get().getTablaPanel().tablaRajzol(Ablak.get().getTabla());
                Ablak.get().frissitLepes(JatekosTipus.AI);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tábla menü létrehozása
     * @return
     */
    private JMenu TablaMenu() {
        final JMenu tablaMenu = new JMenu("Tábla");
        final JMenuItem megforditMenuItem = new JMenuItem("Tábla megfordítása");
        megforditMenuItem.addActionListener(e -> {
            tablaIranya = tablaIranya.megfordit();
            tablaPanel.tablaRajzol(sakkTabla);
        });
        tablaMenu.add(megforditMenuItem);
        final JMenu szinekSubMenu = new JMenu("Mező színének beállítása");
        szinekSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem sotetMezoSzinValasztas = new JMenuItem("Sötét mező színének beállítása");
        szinekSubMenu.add(sotetMezoSzinValasztas);

        final JMenuItem vilagosMezoSzinValasztas = new JMenuItem("Világos mező színének beállítása");
        szinekSubMenu.add(vilagosMezoSzinValasztas);

        sotetMezoSzinValasztas.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Ablak.get().getJatekFrame(), "Sötét mező színének beállítása",
                    Ablak.get().getJatekFrame().getBackground());
            if (colorChoice != null) {
                Ablak.get().getTablaPanel().setMezoSotetSzin(sakkTabla, colorChoice);
            }
        });

        vilagosMezoSzinValasztas.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(Ablak.get().getJatekFrame(), "Világos mező színének beállítása",
                    Ablak.get().getJatekFrame().getBackground());
            if (colorChoice != null) {
                Ablak.get().getTablaPanel().setMezoVilagosSzin(sakkTabla, colorChoice);
            }
        });

        tablaMenu.add(szinekSubMenu);
        return tablaMenu;
    }

    /**
     * Előzmények menü létrehozása.
     * @return
     */
    private JMenu ElozmenyekMenu() {
        final JMenu elozmenyekMenu = new JMenu("Előzmények");
        final JMenuItem elozmenyek = new JMenuItem("Előzmények");
        elozmenyekMenu.add(elozmenyek);
        elozmenyek.addActionListener(e -> Ablak.get().getElozmenyek().hasznal());
        return elozmenyekMenu;
    }

    /**
     * Létrehozza azt a panelt ami majd a mező paneleket tartalmazza majd.
     */
    private class TablaPanel extends JPanel {
        final List<MezoPanel> tablaMezoi;

        TablaPanel() {
            super(new GridLayout(8,8));
            this.tablaMezoi = new ArrayList<>();
            for (int y = 0; y < TablaSeged.OSZLOPOK_SZAMA; y++) {
                for(int x = 0; x < TablaSeged.MEZOK_EGY_SORBAN; x++) {
                    final MezoPanel mezoPanel = new MezoPanel(this, x, y);
                    this.tablaMezoi.add(mezoPanel);
                    add(mezoPanel);
                }
            }
            setPreferredSize(TABLA_MERETE);
            validate();
        }

        public void tablaRajzol(final Tabla tabla) {
            removeAll();
            for (final MezoPanel mezoPanel : tablaIranya.irany(tablaMezoi)){
                mezoPanel.mezoRajzol(tabla);
                add(mezoPanel);
            }
            validate();
            repaint();
        }

        void setMezoSotetSzin(final Tabla tabla, final Color sotetSzin) {
            for (final MezoPanel mezoPanel : tablaMezoi) {
                mezoPanel.setSotetMezoSzin(sotetSzin);
            }
            tablaRajzol(tabla);
        }

        void setMezoVilagosSzin(final Tabla tabla, final Color vilagosSzin) {
            for (final MezoPanel mezoPanel : tablaMezoi) {
                mezoPanel.setVilagosMezoSzin(vilagosSzin);
            }
            tablaRajzol(tabla);
        }
    }

    /**
     * Létrehozza az egy mezőt tartalmazó panelt.
     */
    private class MezoPanel extends JPanel {
        private final int x;
        private final int y;

        MezoPanel(final TablaPanel tablaPanel, final int x, final int y) {
            super(new GridBagLayout());
            this.x = x;
            this.y = y;
            setPreferredSize(MEZO_MEREZE);
            mezoSzine();
            babuIkonjaAMezon(sakkTabla);
            aktivMezoSzele(sakkTabla);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)){
                        jelenlegiMezo = null;
                        celMezo = null;
                        emberMozgoBabuja = null;
                    } else if(isLeftMouseButton(e)) {
                        if(jelenlegiMezo==null) {
                            jelenlegiMezo = sakkTabla.getMezo(x,y);
                            emberMozgoBabuja = jelenlegiMezo.getBabu();
                            if (emberMozgoBabuja == null) {
                                jelenlegiMezo = null;
                            }
                        } else {
                            celMezo = sakkTabla.getMezo(x,y);
                            final Lepes lepes = Lepes.LepesKeszito.lepestKeszit(sakkTabla, jelenlegiMezo.getX(), jelenlegiMezo.getY(), celMezo.getX(), celMezo.getY());
                            final Athelyez athelyez = sakkTabla.getJelenlegiJatekos().lep(lepes);
                            if(athelyez.getLepesAllapot().Kesz()){
                                sakkTabla = athelyez.getAtmenetiTabla();
                                lepesek.addLepes(lepes);
                            }
                            jelenlegiMezo = null;
                            celMezo = null;
                            emberMozgoBabuja = null;
                        }
                        SwingUtilities.invokeLater(() -> {
                            lepesElozmenyek.hasznal(sakkTabla, lepesek);
                            levettBabuk.hasznal(lepesek);
                            if(beallitas.ezAIJatekos(sakkTabla.getJelenlegiJatekos())) {
                                Ablak.get().frissitLepes(JatekosTipus.JATEKOS);
                            }
                            tablaPanel.tablaRajzol(sakkTabla);
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        void setVilagosMezoSzin(final Color szin) {
            vilagosMezoSzin = szin;
        }

        void setSotetMezoSzin(final Color szin) {
            sotetMezoSzin = szin;
        }

        public void mezoRajzol(final Tabla tabla) {
            mezoSzine();
            babuIkonjaAMezon(tabla);
            erevenyesLepesJeloles(tabla);
            aktivMezoSzele(tabla);
            validate();
            repaint();
        }

        private void erevenyesLepesJeloles(final Tabla tabla) {
            for (final Lepes lepes : babuErvenyesLepes(tabla)) {
                if(lepes.getCelX() == this.x && lepes.getCelY() == this.y) {
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("kepek/lepeshely/lehetoseg.png")))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private Collection<Lepes> babuErvenyesLepes(final Tabla tabla) {
            if(emberMozgoBabuja != null && emberMozgoBabuja.getCsapatBabu() == tabla.getJelenlegiJatekos().getCsapat()) {
                return emberMozgoBabuja.szamoltErvenyesLepes(tabla);
            }
            return Collections.emptyList();
        }

        private void babuIkonjaAMezon(final Tabla tabla){
            this.removeAll();
            if(tabla.getMezo(x,y).foglalte()){
                try {
                    final BufferedImage kep =
                            ImageIO.read(new File(defaultBabuKepekHelye + tabla.getMezo(x,y).getBabu().getCsapatBabu().toString().charAt(0) +
                                    tabla.getMezo(x,y).getBabu().toString() + ".png"));
                    add(new JLabel(new ImageIcon(kep)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void mezoSzine() {
            if(y % 2 == 1){
                setBackground(x % 2 == 1 ? vilagosMezoSzin : sotetMezoSzin);
            } else if(y % 2 == 0){
                setBackground(x % 2 == 0 ? vilagosMezoSzin : sotetMezoSzin);
            }
        }

        private void aktivMezoSzele(final Tabla tabla) {
            if(emberMozgoBabuja != null && emberMozgoBabuja.getCsapatBabu() == tabla.getJelenlegiJatekos().getCsapat() &&
            emberMozgoBabuja.getX() == this.x && emberMozgoBabuja.getY() == this.y) {
                setBorder(BorderFactory.createLineBorder(Color.CYAN));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }
    }

    /**
     * Tárolja, hogy milyen lépések történtek.
     */
    public static class Lepesek {

        private final List<Lepes> lepesek;

        Lepesek() {
            this.lepesek = new ArrayList<>();
        }

        public List<Lepes> getLepesek() {
            return this.lepesek;
        }

        void addLepes(final Lepes lepes) {
            this.lepesek.add(lepes);
        }

        public int size() {
            return this.lepesek.size();
        }

        void clear() {
            this.lepesek.clear();
        }

        Lepes removeLepes(final int index) {
            return this.lepesek.remove(index);
        }

        boolean removeLepes(final Lepes lepes) {
            return this.lepesek.remove(lepes);
        }

    }

    enum JatekosTipus {
        JATEKOS,
        AI
    }
    
    public enum TablaIranya {
        NORMALIS {
            @Override
            List<MezoPanel> irany (List<MezoPanel> tablaMezoi) {
                return tablaMezoi;
            }

            @Override
            TablaIranya megfordit() {
                return FORDITOT;
            }
        },
        FORDITOT{
            @Override
            List<MezoPanel> irany (List<MezoPanel> tablaMezoi) {
                return Lists.reverse(tablaMezoi);
            }

            @Override
            TablaIranya megfordit() {
                return NORMALIS;
            }
        };
        
        abstract List<MezoPanel> irany (final List<MezoPanel> tablaMezoi);
        abstract TablaIranya megfordit();
    }
}