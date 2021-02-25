package com.shay.aipets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticConfigurer implements  WebMvcConfigurer {
    @Value("${file.root.path}")
    String fileRootPath;

    @Value("${res.root.path}")
    String resRootPath;

    @Value("${img.post.path}")
    String imgPostPath;
    @Value("${img.comment.path}")
    String imgCommentPath;
    @Value("${img.user.head.path}")
    String userHeadPath;
    @Value("${img.user.bg.path}")
    String userBgPath;
    @Value("${img.ipet.head.path}")
    String petHeadPath;
    @Value("${img.ipet.path}")
    String petImgPath;
    @Value("${img.store.path}")
    String storeImgPath;
    @Value("${img.hospital.path}")
    String hospitalImgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 资源映射
        registry.addResourceHandler(resRootPath + imgPostPath + "**").addResourceLocations("file:" + fileRootPath + imgPostPath);
        registry.addResourceHandler(resRootPath + imgCommentPath + "**").addResourceLocations("file:" + fileRootPath + imgCommentPath);
        registry.addResourceHandler(resRootPath + userHeadPath + "**").addResourceLocations("file:" + fileRootPath + userHeadPath);
        registry.addResourceHandler(resRootPath + userBgPath + "**").addResourceLocations("file:" + fileRootPath + userBgPath);
        registry.addResourceHandler(resRootPath + petHeadPath + "**").addResourceLocations("file:" + fileRootPath + petHeadPath);
        registry.addResourceHandler(resRootPath + petImgPath + "**").addResourceLocations("file:" + fileRootPath + petImgPath);
        registry.addResourceHandler(resRootPath + storeImgPath + "**").addResourceLocations("file:" + fileRootPath + storeImgPath);
        registry.addResourceHandler(resRootPath + hospitalImgPath + "**").addResourceLocations("file:" + fileRootPath + hospitalImgPath);

    }
}
