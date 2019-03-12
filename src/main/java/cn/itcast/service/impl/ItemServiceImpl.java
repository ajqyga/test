package cn.itcast.service.impl;

import cn.itcast.dao.ItemDao;
import cn.itcast.pojo.Item;
import cn.itcast.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 葛斌
 * @create 2019-03-12 10:02
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Override
    public List<Item> findAll(Item item) {

        Example example = Example.of(item);
        this.itemDao.findAll(example);
        return itemDao.findAll();
    }

    @Override
    public void save(Item item) {
        itemDao.save(item);
    }
}
