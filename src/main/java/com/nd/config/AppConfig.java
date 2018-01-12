package com.nd.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by quanzongwei(207127) on 2017/10/18 0018.
 */
// cglib代理不需要接口的比较好用
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@PropertySource({ "classpath:app.properties" })
@ComponentScan(value = { "com.nd.*" }, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class) })
public class AppConfig {// application context

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() {
//        try {
//            ComboPooledDataSource dataSource = new ComboPooledDataSource();
//            dataSource.setDriverClass(environment.getProperty("db.c3p0.driverClassName"));
//            dataSource.setJdbcUrl(environment.getProperty("kiiwow_db.c3p0.url"));
//            dataSource.setUser(environment.getProperty("kiiwow_db.c3p0.username"));
//            dataSource.setPassword(environment.getProperty("kiiwow_db.c3p0.password"));
//            // 当连接池在没有可用空闲连接时每次可以新增的连接数
//            dataSource.setAcquireIncrement(environment.getProperty("c3p0.acquireIncrement", int.class, 5));
//            // 连接池初始连接数
//            dataSource.setInitialPoolSize(environment.getProperty("c3p0.initialPoolSize", int.class, 5));
//            // 连接池可持有的最大连接数
//            dataSource.setMaxPoolSize(environment.getProperty("c3p0.maxPoolSize", int.class, 200));
//            // 连接池可持有的最小连接数
//            dataSource.setMinPoolSize(environment.getProperty("c3p0.minPoolSize", int.class, 5));
//            // 连接池中的连接失效的阀值(即最大未被使用时长)
//            dataSource.setMaxIdleTime(environment.getProperty("c3p0.maxIdleSize", int.class, 1800));
//            // 与MaxIdleTime配合使用，必须小于MaxIdleTime的值，用于减少连接池中的连接
//            dataSource.setMaxIdleTimeExcessConnections(environment.getProperty("c3p0.maxIdleTimeExcessConnections",
//                    int.class, 1200));
//            // 连接最大存活时间，超过这个时间将被断开，正在使用的连接在使用完毕后被断开
//            dataSource.setMaxConnectionAge(environment.getProperty("c3p0.maxConnectionAge", int.class, 1000));
//            // 进行空闲连接测试的SQL
//            dataSource.setPreferredTestQuery(environment.getProperty("c3p0.preferredTestQuery", "select 1 from dual"));
//            // 进行空闲连接测试的时间间隔
//            dataSource.setIdleConnectionTestPeriod(environment.getProperty("c3p0.idleConnectionTestPeriod", int.class,
//                    120));
//            return dataSource;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        <!--<property name="driverClassName" value="com.mysql.jdbc.Driver"/>-->
//        <!--<property name="url" value="jdbc:mysql://localhost/spring"/>-->
//        <!--<property name="username" value="root"/>-->
//        <!--<property name="password" value="root"/>-->
        DruidDataSource dataSource= new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/spring");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
}
