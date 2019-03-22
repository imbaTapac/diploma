package com.student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.student.rating.config.Initializer;
import com.student.rating.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 21.04.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Initializer.class,loader = AnnotationConfigContextLoader.class)
public class EntityTests {
    private SessionFactory sessionFactory;
    private Session session = null;


    @Before
    public void before() {
        // setup the session factory
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Block.class).addAnnotatedClass(Subblock.class).addAnnotatedClass(Paragraph.class)
        .addAnnotatedClass(Rating.class).addAnnotatedClass(Student.class).addAnnotatedClass(Group.class)
        .addAnnotatedClass(Faculty.class).addAnnotatedClass(Specialty.class).addAnnotatedClass(OKR.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.username","root");
        configuration.setProperty("hibernate.connection.password","1234");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/studentrating");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.transaction.jta.platform","org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformResolver");
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();

    }

    @Test
    public void returnsBlock(){
        Block block = new Block();
        block.setName("Block 2");

        List<Subblock> subblockList = new ArrayList();
        Subblock subblock = new Subblock();
        subblock.setName("Subblock 2");
        subblockList.add(subblock);


        block.setSubblock(subblockList);

        /*
        session.getTransaction().begin();
        session.persist(block);
        session.getTransaction().commit();
        */

        session.persist(block);
        session.flush();
    }

    @After
    public void after() {
        session.close();
        sessionFactory.close();
    }
}
