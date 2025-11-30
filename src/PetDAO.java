import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PetDAO {

    public boolean savePetState(VirtualPet pet) {
        
        // Update data Pet yang sudah ada (asumsi ID=1)
        String updateSql = "UPDATE Pet SET hunger = ?, energy = ?, mood = ?, state = ?, last_updated = ? WHERE id_pet = 1";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            if (conn == null) return false; 

            pstmt.setInt(1, pet.getHunger());
            pstmt.setInt(2, pet.getEnergy());
            pstmt.setInt(3, pet.getMood());
            pstmt.setString(4, pet.getState().name());
            // Gunakan ISO_LOCAL_DATE_TIME untuk kompatibilitas DATETIME MySQL
            pstmt.setString(5, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                return insertPet(pet);
            }
            
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("DAO Error (savePetState): " + e.getMessage());
            return false;
        }
    }

    private boolean insertPet(VirtualPet pet) {
        String speciesName = (pet instanceof DinoPet) ? ((DinoPet) pet).getSpecies() : "Unknown";
        String insertSql = "INSERT INTO Pet (id_pet, species, hunger, energy, mood, state, last_updated) VALUES(1, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            
            if (conn == null) return false; // Tambahan safety check

            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            pstmt.setString(1, speciesName);
            pstmt.setInt(2, pet.getHunger());
            pstmt.setInt(3, pet.getEnergy());
            pstmt.setInt(4, pet.getMood());
            pstmt.setString(5, pet.getState().name());
            pstmt.setString(6, currentTime);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DAO Error (insertPet): " + e.getMessage());
            return false;
        }
    }
    
    // Sesuai UML: + logHistory(action, before, after)
    public void logHistory(String action, int valueBefore, int valueAfter) {
        // [TODO: Implementasi log riwayat ke tabel pet_history]
    }
}
