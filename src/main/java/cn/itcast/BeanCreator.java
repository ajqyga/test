package cn.itcast;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 葛斌
 * @create 2019-03-12 10:15
 */
@Configuration
public class BeanCreator {
    @Bean
    public PoolingHttpClientConnectionManager crateConnectionManager(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        return connectionManager;
    }
}
