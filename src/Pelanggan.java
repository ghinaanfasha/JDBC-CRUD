import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pelanggan implements DataPelanggan {
    String namaPelanggan;
    String noHp;
    String alamat;
    public static Connection con;
    public static Statement stm;

    Scanner input = new Scanner(System.in);

    @Override
    public void inputDataPelanggan() {
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

            System.out.println("DATA PELANGGAN");
            System.out.println("--------------");
            System.out.print("Nama Pelanggan\t: ");
            namaPelanggan = input.nextLine();

            System.out.print("No. HP\t\t: ");
            noHp = input.nextLine();

            System.out.print("Alamat\t\t: ");
            alamat = input.nextLine();
            System.out.println("++++++++++++++++++++++++++++++++++++++");
            System.out.println("");

            // CREATE, INSERT data pelanggan ke database (CRUD)
            String sqlInsert = "INSERT INTO pelanggan (nama_pelanggan, no_hp, alamat) VALUES ('" + namaPelanggan
                    + "', '" + noHp
                    + "', '"
                    + alamat + "')";

            int rowsAffected = stm.executeUpdate(sqlInsert);

            if (rowsAffected > 0) {
                System.out.println("Data Pelanggan berhasil dimasukkan ke database.");
                System.out.println("");

            } else {
                System.out.println("Data Pelanggan gagal dimasukkan ke database.");
                System.out.println("");
            }
        }

        catch (Exception e) {
            System.err.println("KONEKSI DATABASE GAGAL " + e.getMessage());
        }
    }

    // READ, Method untuk menampilakan data barang (CRUD)
    public void tampilkanDataPelanggan() {
        try {
            String sqlSelect = "SELECT * FROM pelanggan";
            ResultSet rs = stm.executeQuery(sqlSelect);

            System.out.println("DATA PELANGGAN");
            System.out.println("+-----+-----------------+-----------------+-----------------+");
            System.out.printf("| %-3s | %-15s | %-15s | %-15s |\n", "id", "nama_pelanggan", "no_hp", "alamat");
            System.out.println("+-----+-----------------+-----------------+-----------------+");

            while (rs.next()) {
                String namaPelanggan = rs.getString("nama_pelanggan");
                String noHp = rs.getString("no_hp");
                String alamat = rs.getString("alamat");
                int id = rs.getInt("id");

                System.out.printf("| %-3s | %-15s | %-15s | %-15s |\n", id, namaPelanggan, noHp, alamat);
            }

            System.out.println("+-----+-----------------+-----------------+-----------------+");
            System.out.println("");

        }

        catch (SQLException ex) {
            System.err.println("Gagal menampilkan data pelanggan " + ex.getMessage());
        }
    }

    // UPDATE, Method untuk memperbarui data pelanggan (CRUD)
    public void updateDataPelanggan() {
        try {
            System.out.println("");
            System.out.println("UPDATE Data Pelanggan");
            System.out.print("Masukkan ID Pelanggan yang akan diperbarui: ");
            int id = input.nextInt();
            input.nextLine(); // Mengkonsumsi baris baru

            System.out.println("");
            System.out.println("Masukkan data baru pelanggan");
            System.out.print("Nama Pelanggan\t: ");
            String namaPelanggan = input.nextLine();

            System.out.print("No. HP\t\t: ");
            String noHp = input.nextLine();

            System.out.print("Alamat\t\t: ");
            String alamat = input.nextLine();
            System.out.println("");

            // UPDATE, Kode untuk memperbarui data pelanggan
            String sql = "UPDATE pelanggan SET nama_pelanggan = ?, no_hp = ?, alamat = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, namaPelanggan);
            statement.setString(2, noHp);
            statement.setString(3, alamat);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data Pelanggan berhasil diperbarui di database.");
            } else {
                System.out.println("Data Pelanggan gagal diperbarui di database.");
            }
            System.out.println("");
        }

        catch (SQLException ex) {
            System.err.println("Gagal memperbarui data pelanggan " + ex.getMessage());
        }
    }

    // DELETE, Method untuk menghapus data pelanggan
    public void deleteDataPelanggan() {
        System.out.println("");
        System.out.println("DELETE Data Pelanggan");
        System.out.print("Masukkan ID pelanggan yang ingin dihapus:");
        int id = input.nextInt();

        try {
            // DELETE, Kode untuk menghapus data pelanggan
            String sql = "DELETE FROM pelanggan WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data Pelanggan berhasil dihapus dari database.");
            } else {
                System.out.println("Data Pelanggan gagal dihapus dari database.");
            }
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println("Gagal menghapus data pelanggan " + ex.getMessage());
        }
    }
}
