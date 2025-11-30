// File: Database.java (UPDATE)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/virtual_pet_db?serverTimezone=UTC"; 
    private static final String USER = "root"; 
    private static final String PASS = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            // 1. Memuat Driver MySQL
            Class.forName(DRIVER); 
            // 2. Membuat Koneksi ke MySQL
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("LOG: Koneksi ke database berhasil.");
        } catch (SQLException e) {
            System.err.println("Database Error (SQLException): Gagal koneksi ke MySQL. Pastikan server berjalan dan URL/USER/PASS benar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Database Error (ClassNotFoundException): Driver MySQL ('" + DRIVER + "') tidak ditemukan. Pastikan JAR berada di classpath: " + e.getMessage());
        }
        return conn;
    }
    
    public static void initialize() {
        // SQL CREATE TABLE yang disesuaikan untuk MySQL (berdasarkan ERD)
        String sqlPet = "CREATE TABLE IF NOT EXISTS Pet (\n"
                + " id_pet INT PRIMARY KEY,\n"
                + " species VARCHAR(50),\n"
                + " hunger INT,\n"
                + " energy INT,\n"
                + " mood INT,\n"
                + " state VARCHAR(20),\n"
                + " created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" // Sesuai ERD
                + " last_updated DATETIME\n"
                + ");";
        
        String sqlHistory = "CREATE TABLE IF NOT EXISTS pet_history (\n"
                + " id_history INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " id_pet INT,\n"
                + " action_type VARCHAR(50),\n"
                + " value_before INT,\n"
                + " value_after INT,\n"
                + " timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP\n"
                + ");";
        
        String sqlAnimation = "CREATE TABLE IF NOT EXISTS animation (\n"
                + " id_animation INT PRIMARY KEY AUTO_INCREMENT,\n"
                + " id_pet INT,\n"
                + " species VARCHAR(50),\n"
                + " state VARCHAR(20),\n"
                + " image_path VARCHAR(255)\n"
                + ");";


        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement()) {
            
            if (conn != null) {
                stmt.execute(sqlPet);
                stmt.execute(sqlHistory);
                stmt.execute(sqlAnimation);
                System.out.println("LOG: Tabel MySQL berhasil diinisialisasi/diverifikasi.");
            } else {
                System.err.println("LOG: Gagal melakukan inisialisasi tabel karena koneksi NULL.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menjalankan SQL inisialisasi: " + e.getMessage());
        }
    }
}