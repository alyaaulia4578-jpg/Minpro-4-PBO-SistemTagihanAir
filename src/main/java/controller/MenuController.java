/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import model.Warga;
import model.TagihanAir;
import model.Pembayaran;
import model.Petugas;
import service.PengelolaanService;
import view.MenuView;
/**
 *
 * @author MSI MODERN
 */

public class MenuController {
    private PengelolaanService service;
    private MenuView view;
    private Scanner input;
    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public MenuController() {
        service = new PengelolaanService();
        view = new MenuView();
        input = new Scanner(System.in);
    }

    public void jalankanProgram() {
        int pilihan;
        do {
            System.out.println();
            view.tampilkanJudul();
            view.tampilkanMenu();

            pilihan = inputPilihanMenu();
            switch (pilihan) {
                case 1:
                    tambahWarga();
                    break;
                case 2:
                    tampilWarga();
                    break;
                case 3:
                    ubahWarga();
                    break;
                case 4:
                    hapusWarga();
                    break;
                case 5:
                    tambahTagihan();
                    break;
                case 6:
                    tampilTagihan();
                    break;
                case 7:
                    ubahTagihan();
                    break;
                case 8:
                    hapusTagihan();
                    break;
                case 9:
                    tambahPembayaran();
                    break;
                case 10:
                    tampilPembayaran();
                    break;
                case 11:
                    tampilPetugas();
                    break;
                case 0:
                    System.out.println("Program selesai");
                    break;
                default:
                    System.out.println("Menu tidak tersedia");
                    tekanEnter();
            }
        } while (pilihan != 0);
    }

    private int inputPilihanMenu() {
        while (true) {
            String teks = input.nextLine().trim();
            if (teks.isEmpty()) {
                System.out.print("Pilihan tidak boleh kosong. Pilih menu : ");
                continue;
            }
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.print("Pilihan harus berupa angka. Pilih menu : ");
            }
        }
    }

    private void tambahWarga() {
        System.out.println("===== TAMBAH DATA WARGA =====");
        String id = inputId("ID Warga : ");
        if (service.cariWarga(id) != null) {
            System.out.println("ID warga sudah digunakan");
            tekanEnter();
            return;
        }

        String nama = inputNama("Nama Warga : ");
        String alamat = inputTidakKosong("Alamat : ");
        String nomorRumah = inputNomorRumah("Nomor Rumah : ");
        Warga warga = new Warga(
                id,
                nama,
                alamat,
                nomorRumah
        );
        if (service.tambahWarga(warga)) {
            System.out.println("Data warga berhasil ditambahkan");
        } else {
            System.out.println("Data warga gagal ditambahkan");
        }
        tekanEnter();
    }

    private void tampilWarga() {
        System.out.println("===== DATA WARGA =====");
        if (service.getDaftarWarga().isEmpty()) {
            System.out.println("Belum ada data warga");
        } else {
            for (Warga warga : service.getDaftarWarga()) {
                warga.tampilkanInfo();
                System.out.println();
            }
        }
        tekanEnter();
    }

    private void ubahWarga() {
        System.out.println("===== UBAH DATA WARGA =====");
        String id = inputId("Masukkan ID Warga : ");
        Warga warga = service.cariWarga(id);
        if (warga == null) {
            System.out.println("Data warga tidak ditemukan");
            tekanEnter();
            return;
        }

        System.out.println("Data saat ini:");
        warga.tampilkanInfo();
        System.out.println();
        String nama = inputNamaOpsional(
                "Nama Baru [" + warga.getNama() + "] : ",
                warga.getNama()
        );

        String alamat = inputTidakKosongOpsional(
                "Alamat Baru [" + warga.getAlamat() + "] : ",
                warga.getAlamat()
        );

        String nomorRumah = inputNomorRumahOpsional(
                "Nomor Rumah Baru [" + warga.getNomorRumah() + "] : ",
                warga.getNomorRumah()
        );

        warga.setNama(nama);
        warga.setAlamat(alamat);
        warga.setNomorRumah(nomorRumah);

        if (service.ubahWarga(warga)) {
            System.out.println("Data warga berhasil diubah");
        } else {
            System.out.println("Data warga gagal diubah");
        }
        tekanEnter();
    }

    private void hapusWarga() {
        System.out.println("===== HAPUS DATA WARGA =====");
        String id = inputId("Masukkan ID Warga : ");
        Warga warga = service.cariWarga(id);
        if (warga == null) {
            System.out.println("Data warga tidak ditemukan");
        } else if (service.punyaTagihan(warga)) {
            System.out.println(
                    "Warga masih memiliki tagihan dan tidak dapat dihapus"
            );
        } else if (service.hapusWarga(id)) {
            System.out.println("Data warga berhasil dihapus");
        } else {
            System.out.println("Data warga gagal dihapus");
        }
        tekanEnter();
    }

    private void tambahTagihan() {
        System.out.println("===== TAMBAH TAGIHAN AIR =====");
        String idTagihan = inputId("ID Tagihan : ");
        if (service.cariTagihan(idTagihan) != null) {
            System.out.println("ID tagihan sudah digunakan");
            tekanEnter();
            return;
        }

        tampilDaftarWargaTanpaEnter();
        String idWarga = inputId("ID Warga : ");
        Warga warga = service.cariWarga(idWarga);
        if (warga == null) {
            System.out.println("Data warga tidak ditemukan");
            tekanEnter();
            return;
        }
        int pemakaian = inputPemakaian(
                "Pemakaian air (m3) : "
        );

        TagihanAir tagihan = new TagihanAir(
                idTagihan,
                warga,
                pemakaian
        );
        System.out.println(
                "Total tagihan otomatis : Rp"
                + tagihan.getTotalTagihan()
        );

        if (service.tambahTagihan(tagihan)) {
            System.out.println("Tagihan berhasil dibuat");
        } else {
            System.out.println("Tagihan gagal dibuat");
        }
        tekanEnter();
    }

    private void tampilTagihan() {
        System.out.println("===== DATA TAGIHAN AIR =====");
        if (service.getDaftarTagihan().isEmpty()) {
            System.out.println("Belum ada data tagihan");
        } else {
            for (TagihanAir tagihan : service.getDaftarTagihan()) {
                tagihan.tampilkanInfo();
                System.out.println();
            }
        }
        tekanEnter();
    }

    private void ubahTagihan() {
        System.out.println("===== UBAH TAGIHAN AIR =====");
        String id = inputId("Masukkan ID Tagihan : ");
        TagihanAir tagihan = service.cariTagihan(id);
        if (tagihan == null) {
            System.out.println("Data tagihan tidak ditemukan");
            tekanEnter();
            return;
        }
        if (service.sudahAdaPembayaran(tagihan)
                || "Lunas".equalsIgnoreCase(tagihan.getStatus())) {
            System.out.println("Tagihan sudah lunas dan tidak dapat diubah");
            tekanEnter();
            return;
        }

        System.out.println("Data saat ini:");
        tagihan.tampilkanInfo();
        System.out.println();
        int pemakaian = inputPemakaianOpsional(
                "Pemakaian Baru ["
                + tagihan.getPemakaian()
                + "] m3 : ",
                tagihan.getPemakaian()
        );

        tagihan.setPemakaian(pemakaian);
        if (service.ubahTagihan(tagihan)) {
            System.out.println("Total tagihan otomatis diperbarui : Rp"
            + tagihan.getTotalTagihan()
            );
            System.out.println("Data tagihan berhasil diubah");
        } else {
            System.out.println("Data tagihan gagal diubah");
        }
        tekanEnter();
    }

    private void hapusTagihan() {
        System.out.println("===== HAPUS TAGIHAN AIR =====");
        String id = inputId("Masukkan ID Tagihan : ");
        TagihanAir tagihan = service.cariTagihan(id);
        if (tagihan == null) {
            System.out.println("Data tagihan tidak ditemukan");
        } else if (service.sudahAdaPembayaran(tagihan)) {
            System.out.println(
                    "Tagihan sudah memiliki pembayaran dan tidak dapat dihapus");
        } else if (service.hapusTagihan(id)) {
            System.out.println("Tagihan berhasil dihapus");
        } else {
            System.out.println("Tagihan gagal dihapus");
        }
        tekanEnter();
    }

    private void tambahPembayaran() {
        System.out.println("===== TAMBAH PEMBAYARAN =====");
        if (service.getDaftarTagihan().isEmpty()) {
            System.out.println("Belum ada data tagihan");
            tekanEnter();
            return;
        }
        tampilDaftarTagihanTanpaEnter();
        String idTagihan = inputId("ID Tagihan : ");
        TagihanAir tagihan = service.cariTagihan(idTagihan);
        if (tagihan == null) {
            System.out.println("Data tagihan tidak ditemukan");
            tekanEnter();
            return;
        }
        if ("Lunas".equalsIgnoreCase(tagihan.getStatus())) {
            System.out.println("Tagihan sudah lunas");
            tekanEnter();
            return;
        }

        String idPembayaran = inputId("ID Pembayaran : ");
        if (service.cariPembayaran(idPembayaran) != null) {
            System.out.println("ID pembayaran sudah digunakan");
            tekanEnter();
            return;
        }
        String tanggal = inputTanggal(
                "Tanggal Bayar (dd-MM-yyyy) : "
        );

        int jumlahBayar = inputPembayaran(
                tagihan.getTotalTagihan()
        );

        Pembayaran pembayaran = new Pembayaran(
                idPembayaran,
                tagihan,
                tanggal,
                jumlahBayar
        );

        if (service.tambahPembayaran(pembayaran)) {
            System.out.println("Pembayaran berhasil");
            System.out.println("Status tagihan : "+ tagihan.getStatus());
        } else {
            System.out.println("Pembayaran gagal disimpan");
        }
        tekanEnter();
    }

    private void tampilPembayaran() {
        System.out.println("===== DATA PEMBAYARAN =====");
        if (service.getDaftarPembayaran().isEmpty()) {
            System.out.println("Belum ada data pembayaran");
        } else {
            for (Pembayaran pembayaran: service.getDaftarPembayaran()) {
                pembayaran.tampilkanInfo();
                System.out.println();
            }
        }
        tekanEnter();
    }

    private void tampilPetugas() {
        System.out.println("===== DATA PETUGAS =====");
        if (service.getDaftarPetugas().isEmpty()) {
            System.out.println("Belum ada data petugas");
        } else {
            for (Petugas petugas: service.getDaftarPetugas()) {
                petugas.tampilkanInfo();
                System.out.println();
            }
        }
        tekanEnter();
    }

    private void tampilDaftarWargaTanpaEnter() {
        System.out.println();
        System.out.println("===== DATA WARGA =====");
        for (Warga warga : service.getDaftarWarga()) {
            warga.tampilkanInfo();
            System.out.println();
        }
    }

    private void tampilDaftarTagihanTanpaEnter() {
        System.out.println();
        System.out.println("===== DATA TAGIHAN AIR =====");
        for (TagihanAir tagihan : service.getDaftarTagihan()) {
            tagihan.tampilkanInfo();
            System.out.println();
        }
    }

    private String inputId(String pesan) {
        while (true) {
            String data = inputTidakKosong(pesan).toUpperCase();
            if (!data.matches("[A-Z0-9]+")) {
                System.out.println("ID hanya boleh berisi huruf dan angka");
                continue;
            }
            return data;
        }
    }

    private String inputNama(String pesan) {
        while (true) {
            String data = inputTidakKosong(pesan);
            if (!data.matches("[A-Za-z][A-Za-z .'-]*")) {
                System.out.println(
                        "Nama hanya boleh berisi huruf dan tanda baca yang wajar");
                continue;
            }
            return data;
        }
    }

    private String inputNamaOpsional(
            String pesan,
            String nilaiLama) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine().trim();
            if (data.isEmpty()) {
                return nilaiLama;
            }
            if (!data.matches("[A-Za-z][A-Za-z .'-]*")) {
                System.out.println("Nama hanya boleh berisi huruf dan tanda baca yang wajar");
                continue;
            }
            return data;
        }
    }

    private String inputTidakKosong(String pesan) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine().trim();
            if (data.isEmpty()) {
                System.out.println("Input tidak boleh kosong");
                continue;
            }
            return data;
        }
    }

    private String inputTidakKosongOpsional(
            String pesan,
            String nilaiLama) {
        System.out.print(pesan);
        String data = input.nextLine().trim();
        if (data.isEmpty()) {
            return nilaiLama;
        }
        return data;
    }

    private String inputNomorRumah(String pesan) {
        while (true) {
            String data = inputTidakKosong(pesan);
            if (!data.matches("[A-Za-z0-9][A-Za-z0-9/-]*")) {
                System.out.println("Nomor rumah hanya boleh berisi huruf, angka, - atau /");
                continue;
            }
            return data;
        }
    }

    private String inputNomorRumahOpsional(
            String pesan,
            String nilaiLama) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine().trim();
            if (data.isEmpty()) {
                return nilaiLama;
            }
            if (!data.matches("[A-Za-z0-9][A-Za-z0-9/-]*")) {
                System.out.println("Nomor rumah hanya boleh berisi huruf, angka, - atau /");
                continue;
            }
            return data;
        }
    }

    private int inputPemakaian(String pesan) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine().trim();
            if (data.isEmpty()) {
                System.out.println("Input tidak boleh kosong");
                continue;
            }
            try {
                int nilai = Integer.parseInt(data);
                if (nilai < 0) {
                    System.out.println("Pemakaian tidak boleh negatif");
                    continue;
                }
                if (nilai
                        > Integer.MAX_VALUE
                        / TagihanAir.getTarifAir()) {
                    System.out.println("Pemakaian terlalu besar");
                    continue;
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka");
            }
        }
    }

    private int inputPemakaianOpsional(
            String pesan,
            int nilaiLama) {
        while (true) {
            System.out.print(pesan);

            String data = input.nextLine().trim();

            if (data.isEmpty()) {
                return nilaiLama;
            }
            try {
                int nilai = Integer.parseInt(data);
                if (nilai < 0) {
                    System.out.println("Pemakaian tidak boleh negatif");
                    continue;
                }
                if (nilai
                        > Integer.MAX_VALUE
                        / TagihanAir.getTarifAir()) {
                    System.out.println("Pemakaian terlalu besar");
                    continue;
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka");
            }
        }
    }

    private int inputPembayaran(int totalTagihan) {
        while (true) {
            System.out.print("Jumlah Bayar [Rp" + totalTagihan + "] : ");
            String data = input.nextLine().trim();
            if (data.isEmpty()) {
                System.out.println("Input tidak boleh kosong");
                continue;
            }
            try {
                int jumlah = Integer.parseInt(data);
                if (jumlah <= 0) {
                    System.out.println("Jumlah pembayaran harus lebih dari 0");
                    continue;
                }
                if (jumlah < totalTagihan) {
                    System.out.println("Jumlah pembayaran kurang dari total tagihan");
                    continue;
                }
                if (jumlah > totalTagihan) {
                    System.out.println("Jumlah pembayaran tidak boleh melebihi total tagihan");
                    continue;
                }
                return jumlah;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka");
            }
        }
    }

    private String inputTanggal(String pesan) {
        while (true) {
            String data = inputTidakKosong(pesan);
            try {
                LocalDate tanggal = LocalDate.parse(data, FORMAT_TANGGAL);
                if (tanggal.isAfter(LocalDate.now())) {
                    System.out.println("Tanggal pembayaran tidak boleh melebihi tanggal hari ini");
                    continue;
                }
                return data;
            } catch (DateTimeParseException e) {
                System.out.println("Format atau tanggal tidak valid. Gunakan dd-MM-yyyy");
            }
        }
    }

    private void tekanEnter() {
        System.out.println();
        System.out.println("Tekan ENTER untuk kembali ke menu...");
        input.nextLine();
    }
}
