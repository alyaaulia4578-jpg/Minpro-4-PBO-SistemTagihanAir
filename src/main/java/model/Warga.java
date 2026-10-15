/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI MODERN
 */
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "warga")
@AttributeOverride(
    name = "idUser",
    column = @Column(name = "id_warga")
)
public class Warga extends User {
    private String alamat;
    private String nomorRumah;
    protected Warga() {
    }
    public Warga(String idUser, String nama, String alamat, String nomorRumah) {
        super(idUser, nama);
        this.alamat = alamat;
        this.nomorRumah = nomorRumah;
    }

    @Override
    public String getIdUser() {
        return super.getIdUser();
    }

    @Override
    public String getNama() {
        return super.getNama();
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    @Column(name = "nomor_rumah")
    public String getNomorRumah() {
        return nomorRumah;
    }
    public void setNomorRumah(String nomorRumah) {
        this.nomorRumah = nomorRumah;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("ID Warga     : " + getIdUser());
        System.out.println("Nama         : " + getNama());
        System.out.println("Alamat       : " + alamat);
        System.out.println("Nomor Rumah  : " + nomorRumah);
    }
}