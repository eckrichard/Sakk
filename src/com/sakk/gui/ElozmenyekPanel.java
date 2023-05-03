package com.sakk.gui;

import com.sakk.alap.elozmeny.Elozmeny;
import com.sakk.alap.elozmeny.ElozmenyekData;
import com.sakk.alap.jatekos.Jatekos;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A korábbi játékok kimentelét mutatja.
 */
public class ElozmenyekPanel extends JDialog {

    private ElozmenyekData data = new ElozmenyekData();

    private static final Dimension MERET = new Dimension(400, 200);

    ElozmenyekPanel(){
    }

    ElozmenyekPanel(final JFrame frame, final boolean modal){
        super(frame, modal);

        beolvas();

        final JPanel jPanel = new JPanel(new BorderLayout());
        setPreferredSize(MERET);
        getContentPane().add(jPanel);

        JTable t = new JTable(data);
        JScrollPane sp = new JScrollPane(t);
        t.setFillsViewportHeight(true);
        jPanel.add(sp, BorderLayout.CENTER);
        t.setAutoCreateRowSorter(true);

        final JButton bezarButton = new JButton("Bezár");
        bezarButton.addActionListener(e -> ElozmenyekPanel.this.setVisible(false));
        jPanel.add(bezarButton, BorderLayout.SOUTH);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    /**
     * Használja a AtalakuloBabu-t. Ezt úgy teszi, hogy láthatóvá teszi és újrarajzolja.
     */
    void hasznal() {
        setVisible(true);
        repaint();
    }

    /**
     * Megkapja azt, hogy melyik szín nyert és, hogy hogyan, majd ezt kiírja az elozmeny.dat-ba.
     * @param jatekos
     * @param hogyan
     */
    public void kiir(final Jatekos jatekos, final String hogyan) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String ido = dateFormat.format(date);
        data.addElozmeny(ido,jatekos.toString(),hogyan);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("elozmeny.dat"));
            oos.writeObject(data.elozmenyek);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Beolvassa az elozmeny.dat-ba lévő előzményeket.
     */
    public void beolvas(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("elozmeny.dat"));
            data.elozmenyek = (List<Elozmeny>)ois.readObject();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
