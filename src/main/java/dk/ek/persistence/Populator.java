package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populator {

    EntityManagerFactory emf;
    public Populator(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void populate(){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Employee e1, e2, e3, e4, e5, e6, e7, e8;
            e1 = new Employee("Albert", "Andersen", "ae@mail.com", 20000, "HR");
            e2 = new Employee("Bertha", "Bendixen", "be@mail.com", 30000, "Sales");
            e3 = new Employee("Carl", "Christensen", "cc@mail.com", 25000, "IT");
            e4 = new Employee("Dorte", "Dahl", "dd@mail.com", 28000, "Marketing");
            e5 = new Employee("Erik", "Eriksen", "ee@mail.com", 32000, "Finance");
            e6 = new Employee("Freja", "Frederiksen", "ff@mail.com", 27000, "HR");
            e7 = new Employee("Gunnar", "Gregersen", "gg@mail.com", 35000, "IT");
            e8 = new Employee("Hanne", "Hansen", "hh@mail.com", 29000, "Sales");
            em.persist(e1);
            em.persist(e2);
            em.persist(e3);
            em.persist(e4);
            em.persist(e5);
            em.persist(e6);
            em.persist(e7);
            em.persist(e8);
            em.getTransaction().commit();
        }

    }
    public static void main(String[] args) {
        new Populator(HibernateConfig.getEntityManagerFactory()).populate();

    }
}
