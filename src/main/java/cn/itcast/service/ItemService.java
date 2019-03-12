package cn.itcast.service;

import cn.itcast.pojo.Item;

import java.util.List;

/**
 * @author 葛斌
 * @create 2019-03-12 10:00
 */
public interface ItemService {
    /**
     * 根据条件查询
     * @return
     */
    public List<Item> findAll(Item item);

    /**
     * 保存数据
     * @param item
     */
    public void save(Item item);
}
