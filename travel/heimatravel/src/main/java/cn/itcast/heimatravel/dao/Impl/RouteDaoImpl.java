package cn.itcast.heimatravel.dao.Impl;

import cn.itcast.heimatravel.dao.RouteDao;
import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
       private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    private    List params = new ArrayList<>();

    @Override
    public List<Route> listroute(int cid, int currentpage, int pagesize,String search) {
           String sql=null;
        List<Route> query=null;
        if(cid==0){
            sql="SELECT * FROM tab_route WHERE rname LIKE ? LIMIT ?,?";
            query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),"%"+search+"%",(currentpage - 1) * pagesize , pagesize);
        }else {
            sql="SELECT * FROM tab_route WHERE cid=? AND rname LIKE ? LIMIT ?,?";
        query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid,"%"+search+"%" ,(currentpage - 1) * pagesize , pagesize);
        }

        System.out.println(sql);
        System.out.println((currentpage - 1) * pagesize);
        System.out.println(pagesize);

        return query;
    }

    @Override
    public int totalcount(int cid,String search) {
        String sql=null;
        Integer integer=null;
        if(cid==0){
            sql="SELECT COUNT(*) FROM tab_route WHERE rname LIKE '%"+search+"%'";
             integer = template.queryForObject(sql, Integer.class);
        } else {
         sql="SELECT COUNT(*) FROM tab_route WHERE cid=? AND rname LIKE '%"+search+"%'";
      integer = template.queryForObject(sql, Integer.class, cid);
         }
        return integer;
    }

    @Override
    public Route ridfindone(int rid) {
        String sql="SELECT * FROM tab_route WHERE rid=?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return query.get(0);
    }

    @Override
    public int checkbyrid(int rid) {
        String sql="SELECT COUNT(*) FROM tab_favorite WHERE rid=?";
        Integer integer = template.queryForObject(sql, Integer.class, rid);
        return integer;
    }



    @Override
    public List<Route> rankpb(String search, String curpage, int pagesize,String min, String max) {
        String foot=" GROUP BY f.rid ORDER BY count DESC  LIMIT ?,? ";
        StringBuilder sb = putsb(search, min, max);
        int start=(Integer.parseInt(curpage)-1)*pagesize;
        params.add(start);
        params.add(pagesize);
        sb.append(foot);
        String sql = sb.toString();
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return query;
    }

    @Override
    public int ranktoco(String search, String min, String max) {
        params.clear();
        StringBuilder putsb = putsb(search, min, max);
        putsb.append(" GROUP BY f.rid ");
        String sql=putsb.toString();
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return query.size();
    }

    @Override
    public List<Route> latestrank() {
        String sql="SELECT * FROM tab_route ORDER BY rdate DESC";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return query;
    }

    @Override
    public List<Route> themerank() {
        String sql="SELECT * FROM tab_route WHERE isThemeTour =1";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return query;
    }

    private StringBuilder putsb(String search, String min, String max){
        String head="SELECT COUNT(f.rid) count,r.rid,r.rname,r.price,r.rimage,r.rdate FROM tab_favorite f ,tab_route r WHERE f.rid=r.rid ";
        StringBuilder sb=new StringBuilder();
        sb.append(head);

        if(!"null".equals(search)&&search!=null&&search.length()!=0){
            sb.append(" AND rname LIKE ? ");
            params.add("%"+search+"%");
        }
        if(!"null".equals(min)&&min!=null&&min.length()!=0){
            sb.append(" AND price>? ");
            params.add(min);
        }
        if(!"null".equals(max)&&max!=null&&max.length()!=0){
            sb.append(" AND price<? ");
            params.add(max);
        }
        return sb;
    }
}
