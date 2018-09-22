package cn.itcast.heimatravel.dao.Impl;

import cn.itcast.heimatravel.dao.SellerDao;
import cn.itcast.heimatravel.domain.Seller;
import cn.itcast.heimatravel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller ridfindseller(int rid) {
        String sql="SELECT s.sid,s.sname,s.consphone,s.address FROM tab_seller s INNER JOIN tab_route r ON r.`sid`=s.`sid` AND r.rid=?";
        List<Seller> query = template.query(sql, new BeanPropertyRowMapper<Seller>(Seller.class), rid);
        return query.get(0);
    }
}
