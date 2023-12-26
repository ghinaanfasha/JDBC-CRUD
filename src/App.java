import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class App {
    public static Connection con;
    public static Statement stm;

    public static void main(String[] args) {
        try {
            // Memuat kelas Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");

            // Koneksi ke Database
            String url = "jdbc:mysql://localhost/pbo_jdbc";
            String user = "root";
            String pass = "";
            con = DriverManager.getConnection(url, user, pass);

            // Membuat statement
            stm = con.createStatement();

            // Apabila koneksi berhasil
            System.out.println("");
            System.out.println("KONEKSI DATABASE BERHASIL");

            // Membuat format Date
            Date HariSekarang = new Date();
            SimpleDateFormat day = new SimpleDateFormat("EEEE',' dd/MM/yyyy");
            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss z");

            // Menampilkan Date
            System.out.println("");
            System.out.println("ANFASHA SUPERMARKET");
            System.out.println("Tanggal\t: " + day.format(HariSekarang));
            System.out.println("Waktu\t: " + time.format(HariSekarang));
            System.out.println("======================================");

            try {
                // Membuat objek pelanggan dan menampilkan data pelanggan
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.inputDataPelanggan();

                // Membuat objek barang dan menampilkan struk data barang
                Barang barang = new Barang("M004", "Indomie", 3000);
                Kasir kasir = new Kasir("Ghina");
                kasir.cetakStruk(barang);

                // Menampilkan data dari database READ
                pelanggan.tampilkanDataPelanggan();
                kasir.tampilkanDataPenjualan();

                Scanner scanner = new Scanner(System.in);

                System.out.println("Ingin merubah data pelanggan?");
                System.out.println("1. Update");
                System.out.println("2. Delete");
                System.out.println("3. Tidak");
                System.out.print("Pilihan Anda: ");
                int pilihan = scanner.nextInt();

                switch (pilihan) {
                    case 1:
                        pelanggan.updateDataPelanggan();
                        pelanggan.tampilkanDataPelanggan();
                        break;

                    case 2:
                        pelanggan.deleteDataPelanggan();
                        pelanggan.tampilkanDataPelanggan();
                        break;
                    case 3:
                        System.out.println("Tidak ada perubahan data.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
                scanner.close();
            }

            // Apabila input jumlah barang tidak berupa angka
            catch (InputMismatchException e) {
                System.out.println("\n====================================");
                System.out.println("\nInput Jumlah Barang tidak valid");
                System.out.println("");
            }

            // Apabila input jumlah barang melebihi kapasitas
            catch (Exception e) {
                System.out.println("\n====================================");
                System.out.println("\nInput Jumlah Barang tidak valid");
                System.out.println("");
            }

            // Koneksi database ditutup
            con.close();
        }

        // Apabila koneksi gagal
        catch (Exception e) {
            System.err.println("KONEKSI DATABASE GAGAL " + e.getMessage());
        }
    }
}
