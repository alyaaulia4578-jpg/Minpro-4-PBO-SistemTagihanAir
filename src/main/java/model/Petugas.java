/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI MODERN
 */

public class Petugas extends User {
    private String jabatan;

    public Petugas(String idUser, String nama, String jabatan) {
        super(idUser, nama);
        this.jabatan = jabatan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("ID Petugas : " + getIdUser());
        System.out.println("Nama       : " + getNama());
        System.out.println("Jabatan    : " + jabatan);
    }
}