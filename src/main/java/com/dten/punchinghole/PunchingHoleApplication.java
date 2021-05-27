package com.dten.punchinghole;

import org.mybatis.spring.annotation.MapperScan;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.dten.punchinghole.*"})
//@MapperScan("com.dten.punchinghole.Mapper")
public class PunchingHoleApplication {

	// 启用的http请求的端口(如80)
	@Value("${http.port}")
	Integer httpPort;

	// 启用的https端口(如443)
	@Value("${server.port}")
	Integer httpsPort;

	public static void main(String[] args) {
		SpringApplication.run(PunchingHoleApplication.class, args);
	}

	// --------------------------------------
	// http和https共存
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
			}
		};
		tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
		return tomcat;
	}

	// 配置http
	private Connector createStandardConnector() {
		// 默认协议为org.apache.coyote.http11.Http11NioProtocol
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setSecure(false);
		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setRedirectPort(httpsPort); // 当http重定向到https时的https端口号
		return connector;
	}
	// --------------------------------------

	// --------------------------------------
	// 使用内嵌tomcat启动时，将http请求自动转为https地址
//	@Bean
//	public TomcatServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint constraint = new SecurityConstraint();
//				constraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				constraint.addCollection(collection);
//				context.addConstraint(constraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpConnector());
//		return tomcat;
//	}
//
//	@Bean
//	public Connector httpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		// Connector监听的http的端口号
//		connector.setPort(httpPort);
//		connector.setSecure(false);
//		// 监听到http的端口号后转向到的https的端口号
//		connector.setRedirectPort(httpsPort);
//		return connector;
//	}
	// --------------------------------------

}
