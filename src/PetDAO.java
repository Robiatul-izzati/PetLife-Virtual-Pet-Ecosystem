import java.sql.*;

public class PetDAO {
    private final Connection conn;

    public PetDAO(Connection c) { this.conn = c; }

    public void savePetStats(VirtualPet pet) throws Exception {
        String sql = "INSERT INTO pet_stats(action, hunger, energy, mood, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, pet.getState().name());
            st.setInt(2, pet.getHunger());
            st.setInt(3, pet.getEnergy());
            st.setInt(4, pet.getMood());
            st.executeUpdate();
        }
    }
}
