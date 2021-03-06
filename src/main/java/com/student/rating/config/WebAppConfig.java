package com.student.rating.config;

import static java.lang.Integer.parseInt;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Created by Тарас on 19.02.2018.
 */
@Configuration
@EnableWebMvc
public class WebAppConfig implements WebMvcConfigurer {

	@Value("${db.serverName}")
	private String serverName;
	@Value("${db.portNumber}")
	private int portNumber;
	@Value("${db.name}")
	private String dbName;
	@Value("${db.userName}")
	private String dbUserName;
	@Value("${db.password}")
	private String password;

	@Value("${ssh.userName}")
	private String sshUserName;
	@Value("${ssh.host}")
	private String sshHost;
	@Value("${ssh.port}")
	private int sshPort;
	@Value("${ssh.password}")
	private String sshPassword;

	private static final Logger LOG = LoggerFactory.getLogger(WebAppConfig.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("classpath:/resources/");
		registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory
			(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan("com.student.rating");
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL57Dialect");
		return adapter;
	}

	@Bean
	public Session tunnelSSH() throws JSchException, UnknownHostException, SocketException {
		if(!dbName.equals("studentrating")) {
			final JSch tunnel = new JSch();
			InetAddress address;
			LOG.debug("MY IP = {}", InetAddress.getLocalHost().getHostAddress());
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			while(n.hasMoreElements()) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				while(a.hasMoreElements()) {
					address = a.nextElement();
					LOG.debug("IP = {}", address);
					if(address.getHostAddress().equals("10.17.0.35")) {
						LOG.debug("The IP address is matched");
						LOG.debug("Session is created");
						return null;
					}
				}
			}
			Session session = tunnel.getSession(sshUserName, sshHost, sshPort);
			session.setPassword(sshPassword);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPortForwardingL(3366, serverName, 3306);
			session.connect();
			return session;
		}
		return null;
	}

	@Bean
	public DataSource dataSource(@Nullable Session session) {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(serverName);
		try {
			LOG.debug("Session is = {}", session);
			if(session != null) {
				String[] portWatcher = session.getPortForwardingL();
				int lPort = parseInt(portWatcher[0].substring(0, 4));
				ds.setPortNumber(lPort);
			}
		} catch(JSchException e) {
			LOG.error("Error while creating session. {}", e);
		}
		LOG.debug("DataBase port is = {} ", ds.getPort());

		ds.setDatabaseName(dbName);
		ds.setUser(dbUserName);
		ds.setPassword(password);
		return ds;
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/pages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);

		return resolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setBasenames("classpath:messages");
		messageSource.setDefaultEncoding("Windows-1251");
		return messageSource;
	}

	@Bean
	public LocaleChangeInterceptor changeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		Locale locale = new Locale("uk");
		resolver.setDefaultLocale(locale);
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(changeInterceptor());
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
}