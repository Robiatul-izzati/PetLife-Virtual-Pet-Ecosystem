import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedButton extends JButton {

    private Color baseColor = new Color(60, 130, 200);

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(120, 45));
    }

    public void setBaseColor(Color color) {
        this.baseColor = color;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color currentColor;
        
        // Logika Hover dan Press
        if (getModel().isPressed()) {
            currentColor = baseColor.darker();
        } else if (getModel().isRollover()) {
            currentColor = new Color(90, 160, 230);
        } else {
            currentColor = baseColor;
        }
        
        g2.setColor(currentColor);
        
        int width = getWidth();
        int height = getHeight();
        int arc = 20; 
        g2.fillRoundRect(0, 0, width, height, arc, arc);

        // Gambar teks tombol
        super.paintComponent(g);
        
        g2.dispose();
    }
}