package com.sap.cf.odata.spring.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

/**
 * The configuration class for beans required by the application.
 * 
 * @author i324363
 *
 */

@Configuration
@Profile("cloud")
@ComponentScan(basePackages = "com.sap.cf")
public class CloudConfig extends AbstractCloudConfig {

	private static final String HANA_SVC = "hana-schema-svc";

	private static final Logger LOG = LoggerFactory.getLogger(CloudConfig.class);

	/**
	 * Create dataSource bean from SAP CF
	 * 
	 * @return dataSource dataSoruce created from HANA Service.
	 */
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = null;
		try {
			dataSource = connectionFactory().dataSource(HANA_SVC);
		} catch (CloudException ex) {
			LOG.error(" ", ex);
		}
		return dataSource;
	}

	/**
	 * Create Eclipselink EMF from the dataSource bean. JPAvendor and datasource
	 * will be set here. rest will be taken from persistence.xml
	 * 
	 * @return EntityManagerFactory
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean springEMF = new LocalContainerEntityManagerFactoryBean();
		springEMF.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		springEMF.setDataSource(dataSource());
		springEMF.afterPropertiesSet();
		return springEMF.getObject();

	}

	/**
	 * Registers OData servlet bean with Spring Application context to handle
	 * ODataRequests.
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean odataServlet() {

		ServletRegistrationBean odataServRegstration = new ServletRegistrationBean(new CXFNonSpringJaxrsServlet(),
				"/odata.svc/*");
		Map<String, String> initParameters = new HashMap<>();
		initParameters.put("javax.ws.rs.Application", "org.apache.olingo.odata2.core.rest.app.ODataApplication");
		initParameters.put("org.apache.olingo.odata2.service.factory",
				"com.sap.cf.odata.spring.context.JPAServiceFactory");
		odataServRegstration.setInitParameters(initParameters);

		return odataServRegstration;

	}

}
