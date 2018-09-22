package cn.itcast.heimatravel.dao.Impl;

import cn.itcast.heimatravel.dao.CatagoryDao;
import cn.itcast.heimatravel.domain.Catagory;
import cn.itcast.heimatravel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CatagoryDaoImpl implements CatagoryDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());//我球球你不要忘记传参
    @Override
    public List<Catagory> findcata() {
        String sql="SELECT * FROM tab_category";
        List<Catagory> query = template.query(sql, new BeanPropertyRowMapper<Catagory>(Catagory.class));
        return query;
    }
}
