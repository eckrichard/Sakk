package com.sakk.gui;

import com.sakk.alap.elozmeny.ElozmenyekData;
import com.sakk.alap.tabla.Lepes;
import com.sakk.alap.tabla.Tabla;
import com.sakk.gui.Ablak.Lepesek;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mutatja, hogy milyen lépések történtek.
 */
public class LepesElozmenyek extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private final JButton visszavon;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 40);

    LepesElozmenyek() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        visszavon = new JButton("Visszavon");
        visszavon.addActionListener(e -> {
            if(Ablak.get().getLepesek().size() > 0) {
                Ablak.get().visszavonUtolsoLepes();
            }
        });
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(visszavon, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    /**
     * Kiírja a lépéseket amik történnek.
     * @param tabla
     * @param lepesek
     */
    void hasznal(final Tabla tabla, final Lepesek lepesek) {
        int jelenlegiSor = 0;
        this.model.clear();
        for (final Lepes lepes : lepesek.getLepesek()) {
            final String lepesKiir = lepes.toString();
            if (lepes.getMozgoBabu().getCsapatBabu().Fehere()) {
                this.model.setValueAt(lepesKiir, jelenlegiSor, 0);
            }
            else if (lepes.getMozgoBabu().getCsapatBabu().Feketee()) {
                this.model.setValueAt(lepesKiir, jelenlegiSor, 1);
                jelenlegiSor++;
            }
        }
        if(lepesek.getLepesek().size() > 0) {
            final Lepes utolsoLepes = lepesek.getLepesek().get(lepesek.size() - 1);
            final String lepesKiir = utolsoLepes.toString();

            if (utolsoLepes.getMozgoBabu().getCsapatBabu().Fehere()) {
                this.model.setValueAt(lepesKiir+ " " + szamoltSakkEsSakkMatt(tabla), jelenlegiSor, 0);

            }
            else if (utolsoLepes.getMozgoBabu().getCsapatBabu().Feketee()) {
                this.model.setValueAt(lepesKiir + " " + szamoltSakkEsSakkMatt(tabla), jelenlegiSor - 1, 1);
            }
        }

        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

    }

    /**
     * Ha sakk vagy sakk matt van akkor mit írjon ki a lépés helyére
     * @param tabla
     * @return
     */
    private String szamoltSakkEsSakkMatt(final Tabla tabla) {
        if(tabla.getJelenlegiJatekos().ezSakkMatt()) {
            Ablak.get().getElozmenyekPanel().kiir(Ablak.get().getTabla().getJelenlegiJatekos(), "sakkmatt");
            JOptionPane.showMessageDialog(Ablak.get().getTablaPanel(), "Játék vége: " + Ablak.get().getTabla().getJelenlegiJatekos() +
                    " sakkmattot kapott!", "Játék vége", JOptionPane.INFORMATION_MESSAGE);
            return "SM";
        } else if(tabla.getJelenlegiJatekos().ezSakk()) {
            return "S";
        } else if(tabla.getJelenlegiJatekos().ezPatt()) {
            Ablak.get().getElozmenyekPanel().kiir(Ablak.get().getTabla().getJelenlegiJatekos(), "patt");
            JOptionPane.showMessageDialog(Ablak.get().getTablaPanel(), "Játék vége: " + Ablak.get().getTabla().getJelenlegiJatekos() +
                            " pattot kapott!", "Játék vége", JOptionPane.INFORMATION_MESSAGE);
            return "P";
        }
        return "";
    }

    private static class Sor {

        private String feherLepes;
        private String feketeLepes;

        Sor() {
        }

        public String getFeherLepes() {
            return this.feherLepes;
        }

        public String getFeketeLepes() {
            return this.feketeLepes;
        }

        public void setFeherLepes(final String lepes) {
            this.feherLepes = lepes;
        }

        public void setFeketeLepes(final String lepes) {
            this.feketeLepes = lepes;
        }

    }

    private static class DataModel extends DefaultTableModel {

        private final List<Sor> ertekek;
        private static final String[] NEV = {"Fehér", "Fekete"};

        DataModel() {
            this.ertekek = new ArrayList<>();
        }

        public void clear() {
            this.ertekek.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.ertekek == null) {
                return 0;
            }
            return this.ertekek.size();
        }

        @Override
        public int getColumnCount() {
            return NEV.length;
        }

        @Override
        public Object getValueAt(final int sor, final int oszlop) {
            final Sor jelenlegiSor = this.ertekek.get(sor);
            if(oszlop == 0) {
                return jelenlegiSor.getFeherLepes();
            } else if (oszlop == 1) {
                return jelenlegiSor.getFeketeLepes();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object aValue,
                               final int sor,
                               final int oszlop) {
            final Sor jelenlegiSor;
            if(this.ertekek.size() <= sor) {
                jelenlegiSor = new Sor();
                this.ertekek.add(jelenlegiSor);
            } else {
                jelenlegiSor = this.ertekek.get(sor);
            }
            if(oszlop == 0) {
                jelenlegiSor.setFeherLepes((String) aValue);
                fireTableRowsInserted(sor, sor);
            } else  if(oszlop == 1) {
                jelenlegiSor.setFeketeLepes((String)aValue);
                fireTableCellUpdated(sor, oszlop);
            }
        }

        @Override
        public Class<?> getColumnClass(final int oszlop) {
            return Lepes.class;
        }

        @Override
        public String getColumnName(final int oszlop) {
            return NEV[oszlop];
        }
    }
}
