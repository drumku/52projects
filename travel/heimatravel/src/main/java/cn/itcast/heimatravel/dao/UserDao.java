package cn.itcast.heimatravel.dao;

import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.domain.User;

import java.util.List;

public interface UserDao {

    /**
     * 判断用户民是否已经存在
     * * @param user
     * @return
     */
    boolean samename(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean reguser(User user);

    /**
     * 通过uuid找user
     * @param uuid
     * @return
     */
    User finduserbyuuid(String uuid);

    /**
     * 验证成功更改状态
     * @param user
     * @return
     */
    Boolean changstatus(User user);

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @return
     */
    User loginuser(String username,String password);

    /**
     * 同时查询是否添加收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean checkfav(int rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean addfav(int rid,int uid);


    /**
     * 通过用户id联合查询路线的集合
     * @param uid
     * @return
     */
    List<Route> findfavbyuid(int curpage,int pagesize,int uid);

    /**
     * 通过用户id查询该用户收藏总数
     * @param uid
     * @return
     */
    int countfavbyuid(int uid);

}
