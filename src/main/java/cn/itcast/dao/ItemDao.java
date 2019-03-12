package cn.itcast.dao;

import cn.itcast.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 葛斌
 * @create 2019-03-12 9:59
 */
public interface ItemDao extends JpaRepository<Item,Long> {
}
