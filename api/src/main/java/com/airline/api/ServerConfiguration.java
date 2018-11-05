package com.airline.api;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.airline.api")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.airline.api.repositories")
public class ServerConfiguration {
  private static final String DB_DRIVER = "db.driver";
  private static final String DB_URL = "db.url";
  private static final String DB_USERNAME = "db.username";

  private static final String HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

  @Resource
  private Environment env;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getRequiredProperty(DB_DRIVER));
    dataSource.setUrl(env.getRequiredProperty(DB_URL));
    dataSource.setUsername(env.getRequiredProperty(DB_USERNAME));
    try {
      File passwordSecret = new File("/run/secrets/mysql_password");
      byte[] passwordBytes = Files.readAllBytes(passwordSecret.toPath());
      String password = new String(passwordBytes);
      dataSource.setPassword(password);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(ENTITYMANAGER_PACKAGES_TO_SCAN));
    entityManagerFactoryBean.setJpaProperties(hibProperties());
    return entityManagerFactoryBean;
  }

  @Bean
  public Properties hibProperties() {
    Properties properties = new Properties();
    properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
    properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }
}
