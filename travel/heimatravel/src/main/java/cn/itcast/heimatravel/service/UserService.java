package cn.itcast.heimatravel.service;

import cn.itcast.heimatravel.domain.Infomsg;
import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.domain.User;

import java.util.List;

public interface UserService  {

    /**
     * 注册用户是否成功
     * @param user
     * @return
     */
    boolean reguser(User user);

    /**
     * 通过uuid找到user
     * @param uuid
     * @return
     */
    User finduserbyuuid(String uuid);

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @return
     */
    Infomsg loginuser(String username, String password);

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
    PageBean<Route> findfavbyuid(int curpage,int pagesize,int uid);


}
