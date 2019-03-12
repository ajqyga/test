package cn.itcast.test;

import cn.itcast.dao.ItemDao;
import cn.itcast.pojo.Item;
import cn.itcast.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 葛斌
 * @create 2019-03-12 10:17
 */
@Component
public class Crawler {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private HttpClientUtil httpClientUtil;

    private String url = "https://list.jd.com/list.html?cat=9987,653,655&ev=exbrand_8557&sort=sort_rank_asc&trans=1&JL=3_%E5%93%81%E7%89%8C_%E5%8D%8E%E4%B8%BA%EF%BC%88HUAWEI%EF%BC%89#J_crumbsBar";
    private Thread cThread;

    public void start() {
        cThread = new Thread(new CrawlerTask());
        cThread.start();
    }

    public void stop() {
        cThread.interrupt();
    }

    class CrawlerTask implements Runnable {
        @Override
        public void run() {
            CloseableHttpClient httpClient = httpClientUtil.getHttpClient();
            for (int page = 1; page < 21; page += 2) {
                HttpGet httpGet = new HttpGet(url + page);
                try {
                    CloseableHttpResponse response = httpClient.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String listPageHtml = EntityUtils.toString(response.getEntity(), "utf-8");
                        Jsoup.parse(listPageHtml)
                                .select("li.gi-item")
                                .forEach(li -> {
                                    String sku = li.attr("data-sku");
                                    String spu = li.attr("data-spu");
                                    String title = li.select("div.p-name > a").text();
                                    String price = li.select("div.p-price i").text();
                                    String url = li.select("div.p-img > a").attr("href");
                                    String imgUrl = li.select("div.p-img > a > img").attr("source-date-lazy-img");
                                    String image = downloadImage(imgUrl);
                                    Item item = new Item();
                                    item.setSku(Long.parseLong(sku));
                                    item.setSpu(Long.parseLong(spu));
                                    item.setTitle(title);
                                    if (StringUtils.isNotBlank(price)){
                                        item.setPrice((double) Float.parseFloat(price));
                                    }
                                    item.setUrl(url);
                                    item.setPic(image);
                                    itemDao.save(item);
                                    System.out.println("run");
                                });

                    }
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private String downloadImage(String url) {
            CloseableHttpClient httpClient = httpClientUtil.getHttpClient();
            HttpGet get = new HttpGet("http:"+url);

            String fileName = UUID.randomUUID() + url.substring(url.lastIndexOf("."));
            try {
                CloseableHttpResponse response = httpClient.execute(get);
                if (response.getStatusLine().getStatusCode() == 200) {
                    FileOutputStream fileOutputStream = new FileOutputStream("d:/temp/bak/images/" + fileName);
                    HttpEntity entity = response.getEntity();
                    entity.writeTo(fileOutputStream);
                    fileOutputStream.close();
                }
                response.close();
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }


    }
}
