package org.jcl.life.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Component
public class Interceptor implements WebMvcConfigurer {
	private static Logger logger =
			LoggerFactory.getLogger(Interceptor.class);

	@Autowired
	private LoginInterceptor loginInterceptor;

	/**
	 * 拦截器的添加
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String path = "/**";
		logger.info("拦截器[{}]被添加， 拦截路径为[{}]",
				loginInterceptor.getClass().getName(), path);
		registry.addInterceptor(loginInterceptor).addPathPatterns(path);
	}

	/**
	 * 静态资源映射
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

	}

	/**
	 * 跨域Cors配置
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {

	}

	/**
	 * 视图控制器配置
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	/**
	 * 视图配置
	 * @param registry
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

	}

	/**
	 * 消息内容转换配置
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

	}
}
