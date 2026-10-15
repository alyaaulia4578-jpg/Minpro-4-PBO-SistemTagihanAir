CREATE DATABASE IF NOT EXISTS db_tagihan_air;

USE db_tagihan_air;

CREATE TABLE IF NOT EXISTS warga (
id_warga VARCHAR(10) PRIMARY KEY,
nama VARCHAR(100) NOT NULL,
alamat VARCHAR(150) NOT NULL,
nomor_rumah VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS petugas (
id_petugas VARCHAR(10) PRIMARY KEY,
nama VARCHAR(100) NOT NULL,
jabatan VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tagihan_air (
id_tagihan VARCHAR(10) PRIMARY KEY,
id_warga VARCHAR(10) NOT NULL,
pemakaian INT NOT NULL,
total_tagihan INT NOT NULL,
status VARCHAR(20) NOT NULL,
tarif_air INT NOT NULL,
CONSTRAINT fk_tagihan_warga
FOREIGN KEY (id_warga) REFERENCES warga(id_warga)
);

CREATE TABLE IF NOT EXISTS pembayaran (
id_pembayaran VARCHAR(10) PRIMARY KEY,
id_tagihan VARCHAR(10) NOT NULL,
tanggal_bayar DATE NOT NULL,
jumlah_bayar INT NOT NULL,
CONSTRAINT fk_pembayaran_tagihan
FOREIGN KEY (id_tagihan) REFERENCES tagihan_air(id_tagihan)
);

INSERT INTO warga (id_warga, nama, alamat, nomor_rumah) VALUES
('W1', 'Ahmad Fauzi', 'Perumahan Blok A', '12'),
('W2', 'Alya Aulia', 'Jl.Perjuangan 3', '15');

INSERT INTO petugas (id_petugas, nama, jabatan) VALUES
('P1', 'Budi Santoso', 'Admin Perumahan'),
('P2', 'Siti Rahma', 'Petugas Administrasi');

INSERT INTO tagihan_air (id_tagihan, id_warga, pemakaian, total_tagihan, status, tarif_air) VALUES
('T1', 'W1', 15, 45000, 'Lunas', 3000),
('T2', 'W2', 30, 90000, 'Belum Lunas', 3000);

INSERT INTO pembayaran (id_pembayaran, id_tagihan, tanggal_bayar, jumlah_bayar) VALUES
('PBY1', 'T1', '2026-10-07', 45000);