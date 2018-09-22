package cn.itcast.heimatravel.service.Impl;

import cn.itcast.heimatravel.dao.CatagoryDao;
import cn.itcast.heimatravel.dao.Impl.CatagoryDaoImpl;
import cn.itcast.heimatravel.domain.Catagory;
import cn.itcast.heimatravel.service.CatagoryService;
import cn.itcast.heimatravel.utils.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CatagoryServiceImpl implements CatagoryService {
        CatagoryDao catagoryDao=new CatagoryDaoImpl();

    @Override
    public List<Catagory> findcata() {
        Jedis jedis = JedisUtils.getJedis();
        Set<Tuple> catagory = jedis.zrangeWithScores("catagory", 0, -1);
        if (catagory != null&& catagory.size() != 0) {
            List<Catagory> calis=new ArrayList<Catagory>();
            for (Tuple tuple : catagory) {
                Catagory cat = new Catagory();
                double score = tuple.getScore();
                String element = tuple.getElement();
                cat.setCid(score);
                cat.setCname(element);
                calis.add(cat);
            }
            return calis;
        }else {
            List<Catagory> findcata = catagoryDao.findcata();
            for (Catagory catagory1 : findcata) {
                jedis.zadd("catagory",catagory1.getCid(),catagory1.getCname());
            }
            return findcata;
        }

    }
}
