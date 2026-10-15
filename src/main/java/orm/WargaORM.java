/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orm;

/**
 *
 * @author MSI MODERN
 */
import java.util.List;
import model.Warga;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class WargaORM {
    public List<Warga> getAll() {
        SessionFactory sessionFactory =
                HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createSelectionQuery(
                            "from Warga",
                            Warga.class
                    )
                    .getResultList();
        }
    }
}
