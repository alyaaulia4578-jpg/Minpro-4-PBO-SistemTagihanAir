/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import jakarta.persistence.MappedSuperclass;
/**
 *
 * @author MSI MODERN
 */
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
    private String idUser;
    private String nama;
    protected User() {
    }
    public User(String idUser, String nama) {
        this.idUser = idUser;
        this.nama = nama;
    }

    @Id
    @Column(name = "id_user")
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public abstract void tampilkanInfo();
}
