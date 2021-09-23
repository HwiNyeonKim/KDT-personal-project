package com.example.cccoffeebackendfromatoz;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfigProperties {
	// DB Access Info
	private String url;
	private String username;
	private String password;

	// Table
	private String productTable;
	private String orderTable;
	private String orderItemTable;

	// DataSource
	private DataSource dataSource;

	public String getUrl() {
		return url;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

	@Bean
	public String productTable() {
		return productTable;
	}

	@Bean
	public String orderTable() {
		return orderTable;
	}

	@Bean
	public String orderItemTable() {
		return orderItemTable;
	}


	@Bean
	public DataSource dataSource() {
		if (dataSource == null) {
			dataSource = DataSourceBuilder.create()
					.url(url)
					.username(username)
					.password(password)
					.type(HikariDataSource.class)
					.build();
		}
		return dataSource;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProductTable(String productTable) {
		this.productTable = productTable;
	}

	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}

	public void setOrderItemTable(String orderItemTable) {
		this.orderItemTable = orderItemTable;
	}
}
