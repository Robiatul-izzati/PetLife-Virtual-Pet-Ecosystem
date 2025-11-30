import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        // 1. Inisialisasi Database
        Database.initialize();
        
        // 2. Siapkan Objek-objek utama
        PetDAO petDao = new PetDAO();
        // Placeholder assets
        AnimationPack dinoPack = new AnimationPack("placeholder/normal.gif", "placeholder/eat.gif", "placeholder/sleep.gif", "placeholder/play.gif");
        DinoPet myPet = new DinoPet("Rex", dinoPack);
        
        // 3. Setup Thread
        PetThread petThread = new PetThread(myPet);
        
        // 4. Inisialisasi dan Tampilkan GUI (MainWindow)
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(myPet, petDao, petThread);
            mainWindow.setVisible(true);
            
            // 5. Mulai Thread setelah GUI siap
            petThread.start();
        });
        
        // Pastikan Thread dihentikan ketika aplikasi ditutup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            petThread.stopThread();
            System.out.println("Aplikasi ditutup. PetThread dihentikan.");
        }));
    }
}
