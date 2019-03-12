package cn.itcast.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 葛斌
 * @create 2019-03-12 10:11
 */
@Component
public class HttpClientUtil {
    @Autowired
    private PoolingHttpClientConnectionManager connectionManager;

    public CloseableHttpClient getHttpClient(){
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }
}
