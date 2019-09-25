package com.mbyte.easy.config;

import com.mbyte.easy.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring mvc 配置
 * @author nicc
 *
 */
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
	/**
	 * 文件上传路径前缀
	 */
	@Value("${file.upload.suffix.path}")
	public String uploadSuffixPath;
	/**
	 * 本地磁盘目录
	 */
	@Value("${file.upload.local.path}")
	public String uploadLocalPath;

	/**
	 * @Description : 视图跳转控制器
	 *
	 * @param registry
	 * @return : void
	 * @author : 申劭明
	 * @date : 2019/8/21 15:47
	*/
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//controller路径映射和页面映射
		registry.addViewController("/login").setViewName("security/login");
//		registry.addViewController("/welcome").setViewName("welcome");
	}
	/**
	 * @Title: addResourceHandlers
	 * @Description:  映射本地磁盘为静态目录
	 * @param: registry
	 * @throws:
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		FileUtil.uploadSuffixPath = uploadSuffixPath;
		FileUtil.uploadLocalPath = uploadLocalPath;
		//所有请求形式为uploadSuffixPath/**的,都会被转换为file:/home/lxt/Desktop/0713/test/
		registry.addResourceHandler(uploadSuffixPath +"/**").addResourceLocations("file:"+uploadLocalPath);
	}

	/**
	 * 跨域
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**");
	}
}
