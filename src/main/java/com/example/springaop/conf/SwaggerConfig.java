package com.example.springaop.conf;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description TODO
 * @Author zhouxinjian
 * @Date 2019-07-10 14:46
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Bean
    public Docket api1() {
        Docket build = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springaop.controller"))
                .paths(PathSelectors.any())
                .build();
        return build;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试AOP和Swagger2")
                .contact( new Contact("swagger例子", "https://www.ibm.com/developerworks/cn/java/j-using-swagger-in-a-spring-boot-project/index.html", "861630089@qq.com"))
                .version("2.0")
                .description("aop和swagger的测试")
                .build();
//        return new ApiInfo("测试AOP和Swagger2",
//                "我的站：http://localhost:8080，欢迎大家访问",
//                "1.0", "Terms of service",
//                new Contact("名字想好没", "https://itweknow.cn", "gancy.programmer@gmail.com"),
//                "Apache",
//                "http://www.apache.org/",
//                Collections.emptyList());
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int serverPort = event.getWebServer().getPort();
        System.err.println("swagger访问地址:http://" + getAddress() + ":" + serverPort + "/swagger-ui.html");
    }

    private String getAddress() {
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "127.0.0.1";
        }
        return hostAddress;
    }
}
