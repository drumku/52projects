package cn.itcast.heimatravel.service.Impl;

import cn.itcast.heimatravel.dao.Impl.UserDaoImpl;
import cn.itcast.heimatravel.dao.UserDao;
import cn.itcast.heimatravel.domain.Infomsg;
import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.domain.User;
import cn.itcast.heimatravel.service.UserService;
import cn.itcast.heimatravel.utils.MailUtils;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();
    @Override
    public boolean reguser(User user) {

        return userDao.samename(user.getUsername())&&userDao.reguser(user);
    }

    @Override
    public User finduserbyuuid(String uuid) {
        User finduserbyuuid = userDao.finduserbyuuid(uuid);
        if(finduserbyuuid!=null){
            userDao.changstatus(finduserbyuuid);
        }
        return finduserbyuuid;
    }

    @Override
    public Infomsg loginuser(String username, String password) {
        Infomsg infomsg=new Infomsg();
        User loginuser = userDao.loginuser(username, password);
        if(loginuser==null){
            infomsg.setFlag(false);
            infomsg.setMsg("登录失败用户名或者密码错误!");
        }else {
        if(loginuser.getStatus().equals("N")){
            infomsg.setFlag(false);
            infomsg.setMsg("登录失败,该用户未激活");
        }else {
            infomsg.setFlag(true);
            infomsg.setMsg("登录成功!");
            infomsg.setData_along(loginuser);
        }}
        return infomsg;
    }

    @Override
    public boolean checkfav(int rid, int uid) {
        return  userDao.checkfav(rid, uid);
    }

    @Override
    public boolean addfav(int rid, int uid) {
        return userDao.addfav(rid,uid);
    }

    @Override
    public PageBean<Route> findfavbyuid(int curpage,int pagesize,int uid) {
        PageBean<Route> pageBean=new PageBean<>();

        List<Route> findfavbyuid = userDao.findfavbyuid(curpage, pagesize, uid);
        int countfavbyuid = userDao.countfavbyuid(uid);
        pageBean.setList(findfavbyuid);
         pageBean.setCurrentPage(curpage);
         pageBean.setPageSize(pagesize);
         pageBean.setTotalCount(countfavbyuid);
         pageBean.setTotalPage(countfavbyuid%pagesize==0?countfavbyuid/pagesize:(countfavbyuid/pagesize)+1);
        return pageBean;
    }

}
