package org.oop.app;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class TestJPATranzCRUD {
    public static void main(String[] args){
        entityManagerCRUD_test();
    }
    static void CheckEntityModel(EntityManagerFactory emf){
        System.out.println("--------------------");
        emf.getProperties().forEach((p, v) -> System.out.println(p.toString() + "=" + v.toString()));
        System.out.println("--------------------");
        emf.getMetamodel().getEntities().forEach(e -> System.out.println(e.getName()));
        System.out.println("--------------------");
    }
    static void entityManagerCRUD_test(){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
    EntityManager em = emf.createEntityManager();
    CheckEntityModel(em.getEntityManagerFactory());

    Query queryDeleteUtilizator = em.createQuery("DELETE FROM Utilizator u");
    Query queryDeleteContinut = em.createQuery("DELETE FROM Continut co");
    Query queryDeleteCategorie = em.createQuery("DELETE FROM Categorie c");
    Query queryDeleteAbonament = em.createQuery("DELETE FROM Abonament a");
    Query queryDeleteServer = em.createQuery("DELETE FROM Server s");
    Query queryDeleteNotificari = em.createQuery("DELETE FROM Notificari n");
    Query queryDeleteAngajat = em.createQuery("DELETE FROM Angajat an");
    Query queryDeleteRecenzie = em.createQuery("DELETE FROM Recenzie r");
    Query queryDeleteRecomandari = em.createQuery("DELETE FROM Recomandari re");
    Query queryDeleteActor = em.createQuery("DELETE FROM Actor ac");
    Query queryDeleteDirector = em.createQuery("DELETE FROM Director d");
    Query queryDeleteProducator = em.createQuery("DELETE FROM Producator pr");
    Query queryDeleteRegizor = em.createQuery("DELETE FROM Regizor rg");
        em.getTransaction().begin();


    System.out.println("Start tranzactie: CREATE-UPDATE-DELETE ------------");

    Query queryUtilizator = em.createQuery("SELECT u FROM Utilizator u WHERE u.idUtilizator = 1", Utilizator.class);
    Query queryRecenzie = em.createQuery("SELECT r FROM Recenzie r", Recenzie.class);

    //stergere date existente
        System.out.println("DELETE DATE");
        queryDeleteNotificari.executeUpdate();
        queryDeleteAbonament.executeUpdate();
        queryDeleteUtilizator.executeUpdate();
        queryDeleteContinut.executeUpdate();
        queryDeleteCategorie.executeUpdate();
        queryDeleteRecenzie.executeUpdate();
        queryDeleteRecomandari.executeUpdate();
        queryDeleteServer.executeUpdate();
        queryDeleteAngajat.executeUpdate();
        queryDeleteActor.executeUpdate();
        queryDeleteDirector.executeUpdate();
        queryDeleteRegizor.executeUpdate();
        queryDeleteProducator.executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();

    //populare Abonament
        System.out.println("creare notificari");
        List<Notificari> notificari = new ArrayList<Notificari>();
        notificari.add(new Notificari(1, "warning", "urmeaza plata abonamentului"));
        notificari.add(new Notificari(2, "eroare", "nu s-a putut incarca continutul"));
        notificari.add(new Notificari(3, "eroare", "nu s-au putut incarca categoriile"));
        notificari.add(new Notificari(4, "succes", "plata abonamentului a fost realizata cu succes"));
        notificari.add(new Notificari(5, "warning", "film recomandat copiiilor cu varsta de peste 16 ani"));
        for(Notificari notificare: notificari)
            em.persist(notificare);

        System.out.println("creare angajat");
        List<Angajat> angajat = new ArrayList<Angajat>();
        angajat.add(new Angajat(1,"ONOFREI FLAVIA","0746562383"));
        angajat.add(new Angajat(2,"MIHAILA ALEXANDRU","0787542383"));
        angajat.add(new Angajat(3,"STROESCU ALEXANDRA","0745483918"));
        angajat.add(new Angajat(4,"TUNARU DIANA","0798765432"));
        angajat.add(new Angajat(5,"DUMITRIU MIHAI","0764896751"));
        for(Angajat angajat1: angajat)
            em.persist(angajat1);

        System.out.println("creare recomandari");
        List<Recomandari> recomandari = new ArrayList<Recomandari>();
        recomandari.add(new Recomandari(1, "Pretty Little Things","Daca ai vizionat Jurnalele Vampirilor, iti recomandam Pretty Little Things.", "http://dd"));
        recomandari.add(new Recomandari(2, "How To Get Away With Murder","Daca ai vizionat The Notebook, iti recomandam How To Get Away With Murder.", "http://aa"));
        recomandari.add(new Recomandari(3, "Anabelle","Daca ai vizionat The Rain, iti recomandam Anabelle.", "http://bb"));
        recomandari.add(new Recomandari(4, "Mirai Nikki","Daca ai vizionat Naruto, iti recomandam Mirai Nikki.", "http://cc"));
        recomandari.add(new Recomandari(5, "Viata Pinguinilor","Daca ai The Cecil Hotel, iti recomandam Viata Pinguinilor.", "http://ee"));
        for(Recomandari recomandare: recomandari)
            em.persist(recomandare);


        System.out.println("creare persoana");
        List<Persoana> persoana = new ArrayList<Persoana>();
        persoana.add(new Persoana(1,"POPESCU ANDREI"));
        persoana.add(new Persoana(2,"IONUT GABRIEL"));
        persoana.add(new Persoana(3,"ALEXANDRIU ALEXANDRU"));
        persoana.add(new Persoana(4,"CIUCA ANDREEA"));
        persoana.add(new Persoana(5,"PLUGARU VIVIANA"));
        for(Persoana persoana1: persoana)
            em.persist(persoana1);

        System.out.println("creare recenzie");
        List<Recenzie> recenzie = new ArrayList<Recenzie>();
        recenzie.add(new Recenzie(1, "a fost un serial foarte fain!", 5));
        recenzie.add(new Recenzie(2, "prea overrated acest serial", 2));
        recenzie.add(new Recenzie(3, "un film destul de emotionant, se putea finalul sa fie mai bun!", 3));
        recenzie.add(new Recenzie(4, "un desen animat foarte dubios!", 1));
        recenzie.add(new Recenzie(5, "documentar foarte interesant si aduce informatii suplimentare relevante", 4));
        for(Recenzie recenzie1: recenzie)
            em.persist(recenzie1);


        System.out.println("creare abonament");
        List<Abonament> abonament = new ArrayList<Abonament>();
        abonament.add(new Abonament(1, TipAbonament.STANDARD, 20.0, Plata.DEBIT));
        abonament.add(new Abonament(2, TipAbonament.INTERMEDIAR, 29.9, Plata.CREDIT));
        abonament.add(new Abonament(3, TipAbonament.PREMIUM, 39.9, Plata.APPLE_PAY));
        abonament.add(new Abonament(4, TipAbonament.STANDARD, 20.0, Plata.DEBIT));
        abonament.add(new Abonament(5, TipAbonament.INTERMEDIAR, 29.9, Plata.APPLE_PAY));
        for(Abonament abonament1: abonament)
            em.persist(abonament1);


        System.out.println("creare server");
        List<Server> server = new ArrayList<Server>();
        server.add(new Server(1,"192.168.1.1", 1));
        server.add(new Server(2,"10.0.0.1", 1));
        server.add(new Server(3,"172.16.0.1", 0));
        server.add(new Server(4,"192.168.0.100", 1));
        server.add(new Server(5,"192.168.2.50", 1));
        for(Server server1: server)
            em.persist(server1);


        System.out.println("creare utilizator");
        List<Utilizator> utilizator = new ArrayList<Utilizator>();
        utilizator.add(new Utilizator(1, "IOANA", "ioana@gmail.com","1234"));
        utilizator.add(new Utilizator(2, "FLAVIA", "flavia@gmail.com","5678a"));
        utilizator.add(new Utilizator(3, "DENISA", "denisa@gmail.com","abcd"));
        utilizator.add(new Utilizator(4, "ANDREI", "andrei@gmail.com","defg"));
        utilizator.add(new Utilizator(5, "SEBASTIAN", "sebastian@gmail.com","h1j2"));

        for (Utilizator utilizator1: utilizator)
            em.persist(utilizator1);



        System.out.println("creare continut");
        List<Continut> continut = new ArrayList<Continut>();
        continut.add(new Continut(1, "Jurnalele Vampirilor", TipContinut.SERIAL, 100.0));
        continut.add(new Continut(2, "The Rain", TipContinut.SERIAL, 200.0));
        continut.add(new Continut(3, "The Notebook", TipContinut.FILM, 130.0));
        continut.add(new Continut(4, "Naruto", TipContinut.ANIMATIE, 300.0));
        continut.add(new Continut(5, "The Cecil Hotel", TipContinut.DOCUMENTAR, 60.0));
        for(Continut continut1: continut)
            em.persist(continut1);



        System.out.println("creare categorie");
        List<Categorie> categorie = new ArrayList<Categorie>();
        categorie.add(new Categorie(1, GenCinematografic.DRAGOSTE));
        categorie.add(new Categorie(2, GenCinematografic.ACTIUNE));
        categorie.add(new Categorie(3, GenCinematografic.HORROR));
        categorie.add(new Categorie(4, GenCinematografic.THRILLER));
        categorie.add(new Categorie(5, GenCinematografic.POLITIST));
        for(Categorie categorie1: categorie)
            em.persist(categorie1);

        em.getTransaction().commit();


        em.getTransaction().begin();

        //update
        System.out.println("update utilizator");
        Utilizator utilizatorTest = (Utilizator) queryUtilizator.getSingleResult();
        utilizatorTest.setNumeUtilizator(utilizatorTest.getNumeUtilizator() + "- MODIFICAT");


        //update merge

        System.out.println("update utilizator");
        utilizatorTest = new Utilizator(4, "ALEXANDRU - MERGED", "alexandru@yahoo.com - MERGED", "gdkmfs - MERGED");
        em.merge(utilizatorTest);

        //delete

        System.out.println("delete recenzie");
        Recenzie recenzieToRemove = em.find(Recenzie.class, 5);
        em.remove(recenzieToRemove);

        //end
        System.out.println("END Tranzactie: CREATE-UPDATE-DELETE --------------------");
        em.getTransaction().commit();

        //read

        System.out.println("read recenzii");
        recenzie = queryRecenzie.getResultList();
        for(Recenzie r: recenzie)
            System.out.println("recenzie entitate persistenta: " + r);
        em.close();

    }



}
