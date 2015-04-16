/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ubitech.hibernateencryption.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Using Singleton Pattern to create a Hibernate Session Factory object.
 *
 * @author Christos Paraskeva
 */
public enum HibernateUtil {

    INSTANCE;
    private SessionFactory sessionFactory;

    private final Session session = getSessionFactory().openSession();

    /**
     * Creates a Hibernate SessionFactory Object based on the default
     * configuration(hibernate.cfg.xml) file.
     *
     * @return An instance of Hibernate SessionFactory Object
     */
    public SessionFactory getSessionFactory() {
        Logger.getLogger(HibernateUtil.class.getName()).info("Requested  Session Factory");
        if (sessionFactory == null) {
            try {
                Logger.getLogger(HibernateUtil.class.getName()).info("Session Factory is null.. Instantiating a new Session Factory object ");
                //Register Hibernate Encryptor
                EncryptionUtil.registerHibernatePBEEncryptor();
                Logger.getLogger(HibernateUtil.class.getName()).info("Loaded Hibernate Encryptor...");
                // Create the SessionFactory from standard (hibernate.cfg.xml) config file.
                Configuration configuration = new Configuration().configure();
                ServiceRegistryBuilder srbuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(srbuilder.buildServiceRegistry());
                Logger.getLogger(HibernateUtil.class.getName()).info("Session Factory instance created!");
            } catch (HibernateException ex) {
                // Log the exception. 
                Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "Initial SessionFactory creation failed.{0}", ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    /**
     * Is used in order to get an instance of a Hibernate Session based on the
     * created SessionFactory.
     *
     * @return A Hibernate Session
     */
    public Session getSession() {
        //return this.getSessionFactory().openSession();
        return session;
    }

    /**
     * Commits the changes of the current Transaction to the database.
     */
    public void commitTransaction(Session session) {
        session.getTransaction().commit();
    }

    /**
     * Roll back all changes took effect within the current Transaction.
     */
    public void rollbackTransaction(Session session) {
        session.getTransaction().rollback();
    }

    /**
     * Close the current active Hibernate Session.
     */
    public void closeSession(Session session) {
        session.close();
    }
}
