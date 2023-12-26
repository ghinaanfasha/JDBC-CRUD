import java.util.Date;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

//Pengaplikasian Inheritance
public class Kasir extends Pelanggan {
    String namaKasir;
    public static Connection con;
    public static Statement stm;

    Scanner scanner = new Scanner(System.in);

    // Construktor
    public Kasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    // READ, Method untuk menampilakan data barang (CRUD)
    public void cetakStruk(Barang barang) {
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

            Date HariSekarang = new Date();
            SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

            // Format tanggal database
            String tanggalStr = day.format(HariSekarang);
            String waktuStr = time.format(HariSekarang);

            java.sql.Date tanggalSql = java.sql.Date.valueOf(tanggalStr);
            java.sql.Time waktuSql = java.sql.Time.valueOf(waktuStr);

            System.out.println("DATA PEMBELIAN BARANG");
            System.out.println("---------------------");
            System.out.println("Kode Barang\t: " + barang.kodeBarang);
            System.out.println("Nama Barang\t: " + barang.namaBarang);
            System.out.println("Harga Barang\t: Rp" + barang.hargaBarang);

            System.out.print("Jumlah Beli\t: ");
            barang.jumlahBeli = scanner.nextInt();
            barang.totalBayar = barang.hargaBarang * barang.jumlahBeli;

            System.out.println("TOTAL BAYAR\t: Rp" + barang.totalBayar);
            System.out.println("++++++++++++++++++++++++++++++++++++++");
            System.out.println("Kasir\t\t: " + namaKasir);
            System.out.println("");

            // CREATE, INSERT data penjualan ke database (CRUD)
            String sql = "INSERT INTO penjualan (tanggal, waktu, kode_barang, nama_barang, harga_barang, jumlah_beli, total_bayar, kasir) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // VALUES yang akan di INSERT ke database
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setDate(1, tanggalSql);
            statement.setTime(2, waktuSql);
            statement.setString(3, barang.kodeBarang);
            statement.setString(4, barang.namaBarang);
            statement.setDouble(5, barang.hargaBarang);
            statement.setInt(6, barang.jumlahBeli);
            statement.setDouble(7, barang.totalBayar);
            statement.setString(8, namaKasir);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data Penjualan berhasil dimasukkan ke database.");
                System.out.println("");

            } else {
                System.out.println("Data Penjualan gagal dimasukkan ke database.");
                System.out.println("");
            }
        }

        catch (Exception e) {
            System.err.println("KONEKSI DATABASE GAGAL " + e.getMessage());
        }

    }

    // READ, Method untuk menampilkan data dari database
    public void tampilkanDataPenjualan() {
        try {
            String sqlSelect = "SELECT * FROM penjualan";
            ResultSet rs = stm.executeQuery(sqlSelect);

            System.out.println("DATA PENJUALAN");
            System.out.println(
                    "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            System.out.printf("| %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |\n", "tanggal", "waktu",
                    "kode_barang", "nama_barang", "harga_barang", "jumlah_beli", "total_bayar", "kasir");
            System.out.println(
                    "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");

            while (rs.next()) {
                java.sql.Date tanggalSql = rs.getDate("tanggal");
                java.sql.Time waktuSql = rs.getTime("waktu");
                String kodeBarang = rs.getString("kode_barang");
                String namaBarang = rs.getString("nama_barang");
                int hargaBarang = rs.getInt("harga_barang");
                int jumlahBeli = rs.getInt("jumlah_beli");
                int totalBayar = rs.getInt("total_bayar");
                String namaKasir = rs.getString("kasir");

                System.out.printf("| %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |\n", tanggalSql,
                        waktuSql, kodeBarang, namaBarang, hargaBarang, jumlahBeli, totalBayar, namaKasir);
            }

            System.out.println(
                    "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            System.out.println("");

            // Koneksi database ditutup
            con.close();
        }

        catch (SQLException ex) {
            System.err.println("Gagal menampilkan data penjualan " + ex.getMessage());
        }
    }
}
