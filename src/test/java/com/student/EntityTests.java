package com.student;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.student.rating.entity.Block;
import com.student.rating.entity.Faculty;
import com.student.rating.entity.Group;
import com.student.rating.entity.OKR;
import com.student.rating.entity.Paragraph;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Specialty;
import com.student.rating.entity.Student;
import com.student.rating.entity.Subblock;

/**
 * Created by Тарас on 21.04.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "1234");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/studentrating");
		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		configuration.setProperty("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformResolver");
		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();

	}

	@Test
	public void returnsBlock() {
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

	@Test
	public void cipherTest() throws IOException, NoSuchAlgorithmException, KeyManagementException {
		URL url = new URL("https://yandex.ua");
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		SSLContext.setDefault(context);
		context.init(null,null,null);
		//context.getDefaultSSLParameters().setProtocols(new String[]{"TLSv1.0"});
		//context.getDefaultSSLParameters().setCipherSuites(new String[]{"TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256"});
		SSLSocket socket = (SSLSocket) context.getSocketFactory().createSocket("google.com",443);
		socket.setEnabledCipherSuites(new String[]{"AES128-SHA"});
		socket.setEnabledProtocols(new String[]{"TLSv1.2"});
		socket.startHandshake();
		//socket.startHandshake();
		String[] test = socket.getEnabledProtocols();
		for(String protocol: test) {
			System.out.println(protocol);
		}
		String[] enabledCipherSuites = socket.getSupportedCipherSuites();

		//con.connect();
		//System.out.println(con.getCipherSuite());
		System.out.println(socket.getSession().getCipherSuite());
		System.out.println("Ciphers");
		for(String cipher : enabledCipherSuites){
			System.out.println(cipher);
		}
	}


	@After
	public void after() {
		session.close();
		sessionFactory.close();
	}
}
