package com.nd.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by quanzongwei(207127) on 2017/10/18 0018.
 */
@Configuration
@EnableWebMvc
@PropertySource({ "classpath:web.properties" })
@ComponentScan(value = { "com.nd.controller" })//只扫描控制层
public class WebConfig extends WebMvcConfigurerAdapter {// dispatcher web context
    // web mvc
}
