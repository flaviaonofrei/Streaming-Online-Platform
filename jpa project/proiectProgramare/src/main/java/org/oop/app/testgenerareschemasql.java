package org.oop.app;

import jakarta.persistence.*;

public class testgenerareschemasql {
    public static void main(String[] args){
// Comutare in persistence.xml
// <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/>
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        EntityManager em = emf.createEntityManager();


    }
}