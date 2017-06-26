package com.oblenergo.chat.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("com.oblenergo.chat.repositories")
@EnableMongoRepositories("com.oblenergo.chat.repositories")
@EnableTransactionManagement
@PropertySource("classpath:datasource.properties")
public class DAOConfiguration {

  @Autowired
  private Environment environment;

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "datasource.jdbc")
  public DataSourceProperties dataSourceProperties() {

    return new DataSourceProperties();
  }

  @Bean
  public DataSource dataSource(DataSourceProperties dataSourceProperties) {

    HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create().driverClassName(dataSourceProperties.getDriverClassName())
        .url(dataSourceProperties.getUrl()).username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword())
        .type(HikariDataSource.class).build();
    dataSource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("datasource.jdbc.maxPoolSize")));
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter adapter) throws NamingException {

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setJpaVendorAdapter(adapter);
    factoryBean.setDataSource(dataSource);
    factoryBean.setPackagesToScan(new String[] { "com.oblenergo.chat.models" });
    factoryBean.setJpaProperties(jpaProperties());
    return factoryBean;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {

    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    return hibernateJpaVendorAdapter;
  }

  private Properties jpaProperties() {

    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
    return properties;
  }

  @Bean
  @Autowired
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {

    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(emf);
    return txManager;
  }

}
