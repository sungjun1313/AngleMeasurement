package org.zerock.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses(){
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses(){
		// TODO Auto-generated method stub
		return new Class[] {ServletConfig.class};
	}
	
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		//spring security 하고 충돌해서 작동하지 않는다.
		//SecurityConfig에서 configure 메서드 안에 필터를 넣어줘야 한다.
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return new Filter[] { characterEncodingFilter };
	}
	
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("jsp-inheritance-prefix", "/WEB-INF/layouts/");
		servletContext.setInitParameter("jsp-inheritance-suffix", ".jsp");

	}
	
	@Override
	public void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		
		MultipartConfigElement multipartConfig = new MultipartConfigElement(
				FileConfig.PATH,
				FileConfig.MAX_FILE_SIZE,
				FileConfig.MAX_REQUEST_SIZE,
				FileConfig.FILE_SIZE_THRESHOLD
				);
		registration.setMultipartConfig(multipartConfig);
	}
	
	
}
