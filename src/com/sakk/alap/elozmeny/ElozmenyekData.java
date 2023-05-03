package com.sakk.alap.elozmeny;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály segít abban, hogy kiírjuk az előzményeket egy JTable-be.
 */
public class ElozmenyekData extends AbstractTableModel {
    /**
     * Ebben tároljuk az előzményeket, ez segít ezeknek a kiírásában.
     */
    public List<Elozmeny> elozmenyek = new ArrayList<>();

    public Object getValueAt(int rowIndex, int columnIndex) {
        Elozmeny elozmeny = elozmenyek.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> elozmeny.getIdo();
            case 1 -> elozmeny.getElozmenyekJatekos();
            default -> elozmeny.getHogyan();
        };
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return elozmenyek.size();
    }

    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Mikor";
            case 1 -> "Ki";
            default -> "Hogyan";
        };
    }

    public Class<?> getColumnClass(int columnIndex){
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue,int rowIndex, int columnIndex) {
        Elozmeny elozmeny = elozmenyek.get(rowIndex);
        switch(columnIndex) {
            case 0: elozmeny.setIdo((String) aValue);
            case 1: elozmeny.setElozmenyekJatekos((String) aValue);
            default: elozmeny.setHogyan((String) aValue);
        }
    }

    /**
     * A listához add egy új előzményt.
     * @param ido
     * @param ki
     * @param hogyan
     */
    public void addElozmeny(String ido,String ki, String hogyan) {
        Elozmeny elozmeny = new Elozmeny(ido,ki,hogyan);
        elozmenyek.add(elozmeny);
    }
}
