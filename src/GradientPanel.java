import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {

    public GradientPanel() {
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color colorStart = new Color(173, 216, 230);
        Color colorEnd = new Color(200, 190, 255);
        
        // Buat objek GradientPaint
        GradientPaint gp = new GradientPaint(
            0, 0, colorStart, 
            getWidth(), getHeight(), colorEnd);
            
        // Terapkan Gradient Paint ke seluruh panel
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}