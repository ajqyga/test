package cn.itcast.web;

import cn.itcast.test.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 葛斌
 * @create 2019-03-12 10:58
 */
@RestController
public class ItemController {
    @Autowired
    private Crawler crawler;

    @RequestMapping("/crawler")
    public Map crawlerConter (String active){
        Map map = new HashMap(1);

        if ("start".equals(active)) {
            crawler.start();
            map.put("status", "start");
        } else if ("stop".equals(active)) {
            crawler.stop();
            map.put("status", "stop");
        }
        return map;
    }
}
