import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivityPanel extends JPanel {

    private final JTextArea area = new JTextArea();
    private final ActivityDAO dao;

    public ActivityPanel(ActivityDAO d) {
        this.dao = d;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(260, 600));

        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        area.setBackground(new Color(255, 255, 255));
        area.setForeground(new Color(40, 40, 40));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(250, 140, 120), 2),
                "Aktivitas Pet",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(80, 30, 20)
        ));

        add(scroll, BorderLayout.CENTER);
        reload();
    }

    public void reload() {
        try {
            List<String> rows = dao.loadAll();
            area.setText("");

            for (String r : rows) {
                area.insert("• " + r + "\n", 0); // terbaru di atas
            }

        } catch (Exception e) {
            area.setText("Gagal memuat aktivitas.");
        }
    }
}