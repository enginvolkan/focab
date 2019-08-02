package com.engin.focab;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.engin.focab.repository.VocabularyRepository;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
//@ComponentScan(basePackages = "com")
//@EnableJpaRepositories(basePackages = "com.engin.focab.repository")
//@EntityScan(basePackages ="com.engin.focab.jpa")

public class FocabApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocabApplication.class, args);
	}

//	@Bean
//    public DataSource dataSource() {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setMaximumPoolSize(100);
//        ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
//        ds.addDataSourceProperty("url", "jdbc:mysql://localhost:3306/focab?serverTimezone=UTC");
//        ds.addDataSourceProperty("user", "focab");
//        ds.addDataSourceProperty("password", "123456");
//        ds.addDataSourceProperty("cachePrepStmts", true);
//        ds.addDataSourceProperty("prepStmtCacheSize", 250);
//        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//        ds.addDataSourceProperty("useServerPrepStmts", true);
//        return ds;
//    }
//	@Bean
//	@ConfigurationProperties("spring.datasource")
//	public HikariDataSource dataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//	@Bean(name = "entityManagerFactory")
//	public EntityManagerFactory entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource());
//		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//		emf.setPackagesToScan("com.engin.focab");
//		emf.setPersistenceUnitName("default");
//		emf.afterPropertiesSet();
//		return emf.getObject();
//	}
}
