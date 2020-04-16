package com.engin.focab;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ComponentScan(basePackages = "com")
//@EnableJpaRepositories(basePackages = "com.engIN.focab.repository")
//@EntityScan(basePackages ="com.engIN.focab.jpa")

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
//	@ConfigurationProperties("sprINg.datasource")
//	public HikariDataSource dataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//	@Bean(name = "entityManagerFactory")
//	public EntityManagerFactory entityManagerFactory() {
//		LocalContaINerEntityManagerFactoryBean emf = new LocalContaINerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource());
//		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//		emf.setPackagesToScan("com.engIN.focab");
//		emf.setPersistenceUnitName("default");
//		emf.afterPropertiesSet();
//		return emf.getObject();
//	}
	@Bean
	public LinkedHashMap<String, String> phrasalVerbTagRules() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("NN NN", "NN");
		map.put("JJ NN", "NN");
		map.put("CD NN", "NN");
		map.put("IN NN", "NN");
		map.put("PRP NN", "NN");
		map.put("JJ CD", "CD");
		return map;
	}

	@Bean
	public List<String> phrasalVerbOmitTags() {
		return Arrays.asList("PRP$", "PRP", "RB", "$", "CC", "DT", "DT IN", "PDT");
	}

	@Bean
	public List<String> phrasalVerbFinalTags() {
		return Arrays.asList("", "NN", "JJ", "DT", "CD", "IN", "NN VBN");
	}
}
