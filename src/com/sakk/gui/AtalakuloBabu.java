package com.sakk.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Atalakuló gyalognak állítja be, hogy mivé alakuljanak.
 */
public class AtalakuloBabu extends JDialog {
    // kiralyno: 1, huszar: 2, bastya: 3, futo: 4
    private int babu = 1;

    private static final Dimension MERET = new Dimension(400, 125);

    AtalakuloBabu(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel jPanel = new JPanel(new BorderLayout());
        final JPanel beallitasPanel = new JPanel(new GridLayout(0, 2));
        JPanel gombPanel = new JPanel(new GridLayout(0, 2));
        setPreferredSize(MERET);
        final JRadioButton kiralyno = new JRadioButton("Királynő");
        final JRadioButton huszar = new JRadioButton("Huszár");
        final JRadioButton bastya = new JRadioButton("Bástya");
        final JRadioButton futo = new JRadioButton("Futó");
        final ButtonGroup babuk = new ButtonGroup();
        babuk.add(kiralyno);
        babuk.add(huszar);
        babuk.add(bastya);
        babuk.add(futo);
        kiralyno.setSelected(true);

        getContentPane().add(jPanel);
        beallitasPanel.add(kiralyno);
        beallitasPanel.add(huszar);
        beallitasPanel.add(bastya);
        beallitasPanel.add(futo);

        final JButton megseButton = new JButton("Mégse");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            if(kiralyno.isSelected()) {
                babu = 1;
            } else if(huszar.isSelected()) {
                babu = 2;
            } else if(bastya.isSelected()) {
                babu = 3;
            } else {
                babu = 4;
            }
            AtalakuloBabu.this.setVisible(false);
        });

        megseButton.addActionListener(e -> AtalakuloBabu.this.setVisible(false));

        gombPanel.add(okButton);
        gombPanel.add(megseButton);

        jPanel.add(gombPanel, BorderLayout.SOUTH);
        jPanel.add(new JLabel("Bábu választás: "), BorderLayout.WEST);
        jPanel.add(beallitasPanel, BorderLayout.CENTER);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    /**
     * Használja a AtalakuloBabu-t.
     * Ezt úgy teszi, hogy láthatóvá teszi és újrarajzolja.
     */
    void hasznal() {
        setVisible(true);
        repaint();
    }

    public int getBabu(){
        return this.babu;
    }
}
