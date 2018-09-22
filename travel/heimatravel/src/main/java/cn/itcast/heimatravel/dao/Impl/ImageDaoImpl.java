package cn.itcast.heimatravel.dao.Impl;

import cn.itcast.heimatravel.dao.ImageDao;
import cn.itcast.heimatravel.domain.RouteImg;
import cn.itcast.heimatravel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ImageDaoImpl implements ImageDao {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> ridfindimage(int rid) {
        String sql="SELECT * FROM tab_route_img WHERE rid=?";
        List<RouteImg> query = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return query;
    }
}
