package kr.co.softcampus.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		// SpringMVC 프로젝트 설정 클래스의 객체를 생성하여 ApplicationContext에 등록한다.
		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		// 요청을 처리하는 DispatcherServlet 설정 (위에서 설정한 ApplicationContext를 매개변수로 넘긴다.)
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		
		// 위에서 생성한 dispatcherServlet 객체를 등록한다.
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
		
		// 부가적인 설정
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		// Bean을 정의하는 클래스 지정
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		// ContextLoaderListener 설정 (Bean 정의 클래스를 등록한 ApplicationContext를 Listener의 매개변수로 넘긴다.)
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		// Regist listener
		servletContext.addListener(listener);
		
		// Encoding Filter
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.addMappingForServletNames(null, false, "dispatcher");
	}
}
