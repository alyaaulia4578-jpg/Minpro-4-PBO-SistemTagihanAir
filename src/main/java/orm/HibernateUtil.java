/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orm;

/**
 *
 * @author MSI MODERN
 */
import model.Warga;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY =
            buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Warga.class)
                .buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
