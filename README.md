# Sistem Pengelolaan Tagihan Air Perumahan Warga

## Deskripsi Singkat

Sistem Pengelolaan Tagihan Air Perumahan Warga adalah aplikasi Java untuk mengelola data warga, tagihan air, pembayaran, dan petugas. Program menggunakan konsep Pemrograman Berorientasi Objek dengan struktur MVC, JDBC, DAO, PreparedStatement, serta Hibernate ORM.

## Struktur Package

Program dibagi menjadi beberapa package berdasarkan fungsi masing-masing.

```text
SistemTagihanAir
├── com.mycompany.sistemtagihanair
│   └── Main.java
├── config
│   ├── DatabaseConnection.java
│   └── TestDatabaseConnection.java
├── controller
│   └── MenuController.java
├── dao
│   ├── PembayaranDAO.java
│   ├── PetugasDAO.java
│   ├── TagihanAirDAO.java
│   └── WargaDAO.java
├── model
│   ├── DapatDihitung.java
│   ├── Pembayaran.java
│   ├── Petugas.java
│   ├── TagihanAir.java
│   ├── User.java
│   └── Warga.java
├── orm
│   ├── HibernateUtil.java
│   └── WargaORM.java
├── service
│   └── PengelolaanService.java
└── view
    └── MenuView.java
```

Konfigurasi Hibernate terdapat pada:

```text
src/main/resources/hibernate.properties
```

Dokumentasi gambar program disimpan pada folder `docs`.

![Struktur Project](docs/01-struktur-project.png)

## Alur Program

Program dijalankan melalui `Main.java`, kemudian pengguna berinteraksi dengan menu yang ditampilkan oleh bagian View.

Alur utama program adalah:

```text
Main
↓
View
↓
Controller
↓
Service
↓
DAO
↓
Database MySQL
```

View menampilkan menu dan informasi kepada pengguna. Controller menerima pilihan pengguna dan meneruskan proses. Service menangani proses pengelolaan data, sedangkan DAO menangani komunikasi dengan database.

Program menyediakan pengelolaan data warga, tagihan air, pembayaran, dan petugas.

![Tampilan Program](docs/02-program-utama.png)

## Database

Program menggunakan database MySQL dengan nama:

```text
db_tagihan_air
```

Database terdiri dari tabel:

- `warga`
- `tagihan_air`
- `pembayaran`
- `petugas`

Database telah memiliki data awal yang disediakan melalui file SQL:

```text
database/db_tagihan_air.sql
```

Dengan adanya data awal tersebut, data dapat langsung dibaca ketika program dijalankan.

![Database](docs/03-data-database.png)

## Encapsulation

Encapsulation diterapkan dengan menggunakan atribut `private` pada class.

Contohnya pada class `User`:

```java
private String idUser;
private String nama;
```

Atribut tersebut diakses melalui getter dan setter, seperti `getIdUser()`, `setIdUser()`, `getNama()`, dan `setNama()`.

Dengan demikian, data pada object tidak diakses secara langsung dari luar class.

![Encapsulation dan Abstraction](docs/06-encapsulation-abstraction.png)

## Inheritance

Inheritance diterapkan dengan menggunakan class `User` sebagai class induk.

Class `Warga` dan `Petugas` merupakan class turunan dari `User`:

```java
public class Warga extends User
```

```java
public class Petugas extends User
```

Class turunan dapat menggunakan atribut dan method yang diwariskan dari class `User` serta memiliki atribut tambahan sesuai kebutuhannya.

![Inheritance](docs/07-inheritance.png)

## Polymorphism

Polymorphism diterapkan melalui overriding dan overloading.

### Overriding

Overriding ditunjukkan dengan penggunaan anotasi:

```java
@Override
```

Method pada class turunan atau implementasi class dapat memiliki perilaku yang sesuai dengan kebutuhan class tersebut.

### Overloading

Overloading diterapkan pada method `hitungTotal()` pada class `TagihanAir`.

Terdapat dua method dengan nama yang sama tetapi parameter berbeda:

```java
public int hitungTotal()
```

dan:

```java
public int hitungTotal(int pemakaian)
```

Method tersebut memiliki fungsi yang berbeda berdasarkan parameter yang digunakan.

![Polymorphism dan Overloading](docs/08-polymorphism-interface.png)

## Abstraction

Abstraction diterapkan menggunakan abstract class `User`.

```java
public abstract class User
```

Class tersebut memiliki abstract method:

```java
public abstract void tampilkanInfo();
```

Abstract method tersebut kemudian diimplementasikan oleh class turunannya sesuai dengan kebutuhan masing-masing.

![Abstract Class dan Abstract Method](docs/06-encapsulation-abstraction.png)

## Interface

Program menggunakan interface `DapatDihitung`.

```java
public interface DapatDihitung {
    int hitungTotal();
}
```

Interface tersebut kemudian diterapkan oleh class `TagihanAir`:

```java
public class TagihanAir implements DapatDihitung
```

Method `hitungTotal()` kemudian diimplementasikan pada class tersebut.

![Interface](docs/12-interface-dapat-dihitung.png)

![Implementasi Interface](docs/13-interface-implementasi.png)

## JDBC

Koneksi program dengan database dilakukan menggunakan JDBC melalui class `DatabaseConnection`.

Koneksi dibuat menggunakan `DriverManager`:

```java
DriverManager.getConnection(URL, USER, PASSWORD);
```

Koneksi tersebut digunakan oleh DAO untuk menjalankan operasi database.

![JDBC Connection](docs/04-jdbc-connection.png)

### PreparedStatement

Operasi database menggunakan `PreparedStatement`.

Contohnya pada proses penyimpanan data:

```java
PreparedStatement statement = connection.prepareStatement(sql);
```

Parameter query kemudian diberikan menggunakan method seperti:

```java
statement.setString(...);
```

dan query dijalankan menggunakan:

```java
statement.executeUpdate();
```

Penggunaan PreparedStatement membuat proses pengiriman nilai ke query dilakukan melalui parameter yang telah disediakan.

![PreparedStatement dan DAO](docs/05-preparedstatement-dao.png)

### DAO

DAO digunakan untuk memisahkan proses akses database dari bagian program lainnya.

DAO yang digunakan adalah:

```text
WargaDAO
TagihanAirDAO
PembayaranDAO
PetugasDAO
```

Dengan struktur tersebut, proses pengelolaan data database berada pada class yang khusus menangani akses data.

## Hibernate ORM

Hibernate ORM digunakan sebagai framework Object-Relational Mapping untuk menghubungkan object Java dengan tabel pada database.

Konfigurasi Hibernate menggunakan file:

```text
src/main/resources/hibernate.properties
```

`HibernateUtil` digunakan untuk membangun `SessionFactory` dan mendaftarkan entity yang digunakan.

![Hibernate Util](docs/09-hibernate-util.png)

Class `WargaORM` digunakan untuk mengambil data warga menggunakan Hibernate.

```java
createSelectionQuery("from Warga", Warga.class)
```

Hasil query kemudian diambil menggunakan:

```java
getResultList()
```

![Hibernate ORM](docs/10-hibernate-orm.png)

Pemetaan class `Warga` ke tabel `warga` dilakukan menggunakan anotasi JPA seperti:

```java
@Entity
@Table(name = "warga")
```

serta pemetaan atribut menggunakan anotasi seperti `@Column` dan `@AttributeOverride`.

![Hibernate Entity](docs/11-hibernate-entity.png)

## Nilai Tambah

Nilai tambah program terdapat pada penggunaan **Hibernate ORM** sebagai framework Object-Relational Mapping.

Hibernate digunakan untuk melakukan pemetaan antara object Java dengan tabel database. Implementasinya terdapat pada package:

```text
orm
├── HibernateUtil.java
└── WargaORM.java
```

Penggunaan ORM melengkapi akses database yang sebelumnya dilakukan menggunakan JDBC dan DAO.

## Kesimpulan

Program Sistem Pengelolaan Tagihan Air Perumahan Warga menerapkan konsep Pemrograman Berorientasi Objek dan pengelolaan database secara terstruktur.

Konsep yang diterapkan meliputi encapsulation, inheritance, polymorphism melalui overriding dan overloading, abstraction melalui abstract class dan abstract method, interface, MVC, JDBC dengan PreparedStatement dan DAO, serta Hibernate ORM.