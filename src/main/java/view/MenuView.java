/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author MSI MODERN
 */

public class MenuView {
    public void tampilkanJudul() {
        System.out.println("==============================");
        System.out.println(" SISTEM PENGELOLAAN TAGIHAN AIR");
        System.out.println("        PERUMAHAN WARGA");
        System.out.println("==============================");
    }

    public void tampilkanMenu() {
        System.out.println("1. Tambah Data Warga");
        System.out.println("2. Lihat Data Warga");
        System.out.println("3. Ubah Data Warga");
        System.out.println("4. Hapus Data Warga");
        System.out.println();
        System.out.println("5. Tambah Tagihan Air");
        System.out.println("6. Lihat Tagihan Air");
        System.out.println("7. Ubah Tagihan Air");
        System.out.println("8. Hapus Tagihan Air");
        System.out.println();
        System.out.println("9. Tambah Pembayaran");
        System.out.println("10. Lihat Data Pembayaran");
        System.out.println();
        System.out.println("11. Lihat Data Petugas");
        System.out.println("0. Keluar");
        System.out.println("==============================");
        System.out.print("Pilih menu : ");
    }
}
