package com.sakk.gui;

import com.google.common.primitives.Ints;
import com.sakk.alap.babu.Babu;
import com.sakk.alap.tabla.Lepes;
import com.sakk.gui.Ablak.Lepesek;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Levett bábuk képét mutatja.
 */
public class LevettBabuk extends JPanel {
    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_SZINE = Color.decode("0xFDF5E6");
    private static final Dimension MERET = new Dimension(80, 100);
    private static final EtchedBorder PANEL_SZELE = new EtchedBorder(EtchedBorder.RAISED);

    public LevettBabuk() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(PANEL_SZELE);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_SZINE);
        this.southPanel.setBackground(PANEL_SZINE);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(MERET);
    }

    /**
     * Használja a LevettBabuk osztályt. Eltávolítja a paneljeiben lévő bábukat.
     * Majd végig megy a lépéseken és megnézi,
     * hogy melyek voltak a támadt bábuk és ezeket belehelyezi a megfelelő panelbe.
     * A bábukhoz pedig beolvassa a hozzájuk tartozó képeket.
     * @param lepesek
     */
    public void hasznal(final Lepesek lepesek) {
        southPanel.removeAll();
        northPanel.removeAll();

        final List<Babu> feherLevettBabuk = new ArrayList<>();
        final List<Babu> feketeLevettBabuk = new ArrayList<>();

        for(final Lepes lepes : lepesek.getLepesek()) {
            if(lepes.ezTamadas()) {
                final Babu levettBabu = lepes.getTamadotBabu();
                if(levettBabu.getCsapatBabu().Fehere()) {
                    feherLevettBabuk.add(levettBabu);
                } else if(levettBabu.getCsapatBabu().Feketee()){
                    feketeLevettBabuk.add(levettBabu);
                } else {
                    throw new RuntimeException("Hiba");
                }
            }
        }

        feherLevettBabuk.sort((p1, p2) -> Ints.compare(p1.getBabuErteke(), p2.getBabuErteke()));

        feketeLevettBabuk.sort((p1, p2) -> Ints.compare(p1.getBabuErteke(), p2.getBabuErteke()));

        for (final Babu levettBabu : feherLevettBabuk) {
            try {
                final BufferedImage kep = ImageIO.read(new File("kepek/icons/"
                        + levettBabu.getCsapatBabu().toString().charAt(0) + "" + levettBabu
                        + ".png"));
                final ImageIcon ic = new ImageIcon(kep);
                final JLabel jLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(jLabel);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }

        for (final Babu takenPiece : feketeLevettBabuk) {
            try {
                final BufferedImage kep = ImageIO.read(new File("kepek/icons/"
                        + takenPiece.getCsapatBabu().toString().charAt(0) + "" + takenPiece
                        + ".png"));
                final ImageIcon ic = new ImageIcon(kep);
                final JLabel jLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.northPanel.add(jLabel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        validate();
    }
}
