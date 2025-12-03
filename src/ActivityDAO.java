import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    private final Connection conn;

    public ActivityDAO(Connection c) {
        this.conn = c;
    }

    public void logActivity(String text) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO activity(text, created_at) VALUES (?, NOW())")) {
            st.setString(1, text);
            st.executeUpdate();
        }
    }

    public List<String> loadAll() throws Exception {
        List<String> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT text, created_at FROM activity ORDER BY created_at DESC")) {

            while (rs.next()) {
                list.add(rs.getString("created_at") + " — " + rs.getString("text"));
            }
        }
        return list;
    }
}
