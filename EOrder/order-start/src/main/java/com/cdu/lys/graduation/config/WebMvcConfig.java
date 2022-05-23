package com.cdu.lys.graduation.config;

import com.cdu.lys.graduation.interceptor.AdminLoginInterceptor;
import com.cdu.lys.graduation.interceptor.FileInterceptor;
import com.cdu.lys.graduation.interceptor.SystemInterceptor;
import com.cdu.lys.graduation.interceptor.LoginInterceptor;
import com.cdu.lys.graduation.types.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author liyinsong
 * @date 2022/1/8 15:14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private SystemInterceptor systemInterceptor;

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    private FileInterceptor fileInterceptor;

    /**
     * 跨域方法
     */
    private static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /*系统所有请求拦截器*/
        registry.addInterceptor(systemInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**",
                        "/swagger-ui.html/**",
                        "/test/**",
                        "/favicon.ico",
                        "/error",
                        "/csrf",
                        "/");

        /*用户登录拦截器*/
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(SystemConstant.SYSTEM_API_ROOT);

        /*管理系统登录拦截器*/
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns(SystemConstant.SYSTEM_MANAGE_PATH);

        /*文件上传接口拦截*/
        registry.addInterceptor(fileInterceptor)
                .addPathPatterns(SystemConstant.SYSTEM_FILE_UPLOAD_PATH);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(SystemConstant.SYSTEM_STATIC_PATH)
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8848")
                .allowCredentials(true)
                .allowedMethods(ORIGINS)
                .maxAge(3600);
    }
}
