/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI MODERN
 */


public class TagihanAir implements DapatDihitung {
    private String idTagihan;
    private Warga warga;
    private int pemakaian;
    private int totalTagihan;
    private String status;
    private static final int TARIF_AIR = 3000;

    public TagihanAir(String idTagihan, Warga warga, int pemakaian) {
        if (idTagihan == null || idTagihan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID tagihan tidak boleh kosong");
        }

        if (warga == null) {
            throw new IllegalArgumentException("Data warga tidak boleh kosong");
        }

        if (pemakaian < 0) {
            throw new IllegalArgumentException("Pemakaian tidak boleh negatif");
        }

        if (pemakaian > Integer.MAX_VALUE / TARIF_AIR) {
            throw new IllegalArgumentException("Pemakaian terlalu besar");
        }

        this.idTagihan = idTagihan;
        this.warga = warga;
        this.pemakaian = pemakaian;

        hitungTotal();
        if (totalTagihan == 0) {
            this.status = "Lunas";
        } else {
            this.status = "Belum Lunas";
        }
    }

    @Override
    public int hitungTotal() {
        totalTagihan = hitungTotal(pemakaian);
        return totalTagihan;
    }

    public int hitungTotal(int pemakaian) {
        if (pemakaian < 0) {
            throw new IllegalArgumentException("Pemakaian tidak boleh negatif");
        }
        if (pemakaian > Integer.MAX_VALUE / TARIF_AIR) {
            throw new IllegalArgumentException("Pemakaian terlalu besar");
        }
        return pemakaian * TARIF_AIR;
    }

    public String getIdTagihan() {
        return idTagihan;
    }

    public void setIdTagihan(String idTagihan) {
        if (idTagihan == null || idTagihan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID tagihan tidak boleh kosong");
        }
        this.idTagihan = idTagihan;
    }

    public Warga getWarga() {
        return warga;
    }

    public void setWarga(Warga warga) {
        if (warga == null) {
            throw new IllegalArgumentException("Data warga tidak boleh kosong");
        }
        this.warga = warga;
    }

    public int getPemakaian() {
        return pemakaian;
    }

    public void setPemakaian(int pemakaian) {
        if (pemakaian < 0) {
            throw new IllegalArgumentException("Pemakaian tidak boleh negatif");
        }
        if (pemakaian > Integer.MAX_VALUE / TARIF_AIR) {
            throw new IllegalArgumentException("Pemakaian terlalu besar");
        }

        this.pemakaian = pemakaian;
        hitungTotal();
        if (totalTagihan == 0) {
            status = "Lunas";
        } else {
            status = "Belum Lunas";
        }
    }

    public int getTotalTagihan() {
        return totalTagihan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status tidak boleh kosong");
        }
        this.status = status;
    }

    public static int getTarifAir() {
        return TARIF_AIR;
    }

    public void tampilkanInfo() {
        System.out.println("ID Tagihan   : " + idTagihan);
        System.out.println("Nama Warga   : " + warga.getNama());
        System.out.println("Pemakaian    : " + pemakaian + " m3");
        System.out.println("Tarif Air    : Rp" + TARIF_AIR + "/m3");
        System.out.println("Total Bayar  : Rp" + totalTagihan);
        System.out.println("Status       : " + status);
    }
}
