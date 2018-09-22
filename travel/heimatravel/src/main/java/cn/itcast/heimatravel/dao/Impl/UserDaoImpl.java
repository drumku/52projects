package cn.itcast.heimatravel.dao.Impl;

import cn.itcast.heimatravel.dao.UserDao;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.domain.User;
import cn.itcast.heimatravel.utils.JDBCUtils;
import cn.itcast.heimatravel.utils.MailUtils;
import cn.itcast.heimatravel.utils.UuidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDaoImpl implements UserDao {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean samename(String username) {
        String sql="SELECT * FROM tab_user WHERE username=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        if(user!=null){
        return false;
        }else {
            return true;
        }
        } catch (DataAccessException e) {
            return true;
        }
    }

    @Override
    public boolean reguser(User user) {
        String sql="INSERT INTO tab_user(username,PASSWORD,NAME,birthday,sex,telephone,email,STATUS,CODE) VALUES(?,?,?,?,?,?,?,?,?)";
        String uuid = UuidUtil.getUuid();
        int n=template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                "N",uuid );
        MailUtils.sendMail(user.getEmail(), "<a href='http://localhost:8088/travel/emailcheck?code="+uuid+"'>点击验证邮箱</a>", "黑马旅游网邮箱验证");

        if(n>0){return true;}else {return false;}
    }

    @Override
    public User finduserbyuuid(String uuid) {
        String sql="SELECT * FROM tab_user WHERE code=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), uuid);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public Boolean changstatus(User user) {
        String sql="UPDATE tab_user SET STATUS = 'Y' WHERE uid=?";
        int update = template.update(sql, user.getUid());
        if(update>0){return true;}else {return false;}
    }

    @Override
    public User loginuser(String username, String password) {
        String sql="SELECT * FROM tab_user WHERE username=? and password=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public boolean checkfav(int rid, int uid) {
        String sql="SELECT COUNT(*) FROM tab_favorite WHERE rid=? AND uid=?";
        Integer integer = template.queryForObject(sql, Integer.class, rid, uid);
        if(integer!=0){ return true; }
        return false;
    }

    @Override
    public boolean addfav(int rid, int uid) {
        String sql="INSERT INTO tab_favorite VALUES(?,?,?)";
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        int update = template.update(sql, rid, format, uid);
        if(update>0){return true;}
        return false;
    }

    @Override
    public List<Route> findfavbyuid(int curpage,int pagesize,int uid) {
        String sql="SELECT * FROM tab_route WHERE rid IN\n" +
                "(SELECT rid FROM tab_favorite WHERE uid=?) LIMIT ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid,(curpage-1)*pagesize,pagesize);
        return query;
    }

    @Override
    public int countfavbyuid(int uid) {
        String sql="SELECT COUNT(*) FROM tab_favorite WHERE uid=?";
        Integer integer = template.queryForObject(sql, Integer.class, uid);
        return integer;
    }
}
