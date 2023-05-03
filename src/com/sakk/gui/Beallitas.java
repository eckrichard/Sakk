package com.sakk.gui;

import com.sakk.alap.Csapat;
import com.sakk.alap.jatekos.Jatekos;
import com.sakk.gui.Ablak.JatekosTipus;

import javax.swing.*;
import java.awt.*;

/**
 * Beállítások menü, ahol tudjuk állítani a beállításokat.
 */
public class Beallitas extends JDialog {
    private JatekosTipus feherJatekosTipus;
    private JatekosTipus feketeJatekosTipus;
    private int nehezseg = 2;

    private static final Dimension MERET = new Dimension(180, 400);
    private static final String JATEKOS_KIIR = "Játékos";
    private static final String AI_KIIR = "AI";

    Beallitas(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel jPanel = new JPanel(new GridLayout(0, 1));
        JPanel gombPanel = new JPanel(new GridLayout(0, 2));
        setPreferredSize(MERET);
        final JRadioButton feherEmberGomb = new JRadioButton(JATEKOS_KIIR);
        final JRadioButton feherAIGomb = new JRadioButton(AI_KIIR);
        final JRadioButton feketeEmberGomb = new JRadioButton(JATEKOS_KIIR);
        final JRadioButton feketeAIGomb = new JRadioButton(AI_KIIR);
        feherEmberGomb.setActionCommand(JATEKOS_KIIR);
        final ButtonGroup feherCsoport = new ButtonGroup();
        feherCsoport.add(feherEmberGomb);
        feherCsoport.add(feherAIGomb);
        feherEmberGomb.setSelected(true);

        final ButtonGroup feketeCsoport = new ButtonGroup();
        feketeCsoport.add(feketeEmberGomb);
        feketeCsoport.add(feketeAIGomb);
        feketeEmberGomb.setSelected(true);

        getContentPane().add(jPanel);
        jPanel.add(new JLabel("Fehér"));
        jPanel.add(feherEmberGomb);
        jPanel.add(feherAIGomb);
        jPanel.add(new JLabel("Fekete"));
        jPanel.add(feketeEmberGomb);
        jPanel.add(feketeAIGomb);


        final ButtonGroup nehezsegCsoport = new ButtonGroup();
        final JRadioButton konnyuGomb = new JRadioButton("Könnyű");
        final JRadioButton kozepesGomb = new JRadioButton("Közepes");
        final JRadioButton nehezGomb = new JRadioButton("Nehéz");
        nehezsegCsoport.add(konnyuGomb);
        nehezsegCsoport.add(kozepesGomb);
        nehezsegCsoport.add(nehezGomb);
        kozepesGomb.setSelected(true);
        jPanel.add(new JLabel("Nehézség"));
        jPanel.add(konnyuGomb);
        jPanel.add(kozepesGomb);
        jPanel.add(nehezGomb);

        final JButton megseButton = new JButton("Mégse");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            feherJatekosTipus = feherAIGomb.isSelected() ? JatekosTipus.AI : JatekosTipus.JATEKOS;
            feketeJatekosTipus = feketeAIGomb.isSelected() ? JatekosTipus.AI : JatekosTipus.JATEKOS;
            if(konnyuGomb.isSelected()) {
                nehezseg = 1;
            } else if(kozepesGomb.isSelected()){
                nehezseg = 2;
            } else {
                nehezseg = 3;
            }
            Beallitas.this.setVisible(false);
        });

        megseButton.addActionListener(e -> Beallitas.this.setVisible(false));

        gombPanel.add(okButton);
        gombPanel.add(megseButton);

        jPanel.add(gombPanel);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    void hasznal() {
        setVisible(true);
        repaint();
    }

    boolean ezAIJatekos(final Jatekos jatekos) {
        if(jatekos.getCsapat() == Csapat.WHITE) {
            return getFeherJatekosTipus() == JatekosTipus.AI;
        }
        return getFeketeJatekosTipus() == JatekosTipus.AI;
    }

    JatekosTipus getFeherJatekosTipus() {
        return this.feherJatekosTipus;
    }

    JatekosTipus getFeketeJatekosTipus() {
        return this.feketeJatekosTipus;
    }

    int getKeresesiMelyseg() {
        return nehezseg;
    }
}
