package cn.itcast.heimatravel.web.servlet;

import cn.itcast.heimatravel.domain.Infomsg;
import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.domain.User;
import cn.itcast.heimatravel.service.Impl.RouteServiceImpl;
import cn.itcast.heimatravel.service.Impl.UserServiceImpl;
import cn.itcast.heimatravel.service.RouteService;
import cn.itcast.heimatravel.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();//session自杀
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0, 0, width, height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //向图片上写入验证码
        g.drawString(checkCode, 15, 25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        Infomsg infomsg = new Infomsg();


        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || check == null || !checkcode_server.equalsIgnoreCase(check)) {
            infomsg.setFlag(false);
            infomsg.setMsg("验证码有误!");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserService userService = new UserServiceImpl();
            infomsg = userService.loginuser(username, password);
            session.setAttribute("logineduser", infomsg.getData_along());
        }
        WriteValue(infomsg, response);
    }

    public void email(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");
        UserService userService = new UserServiceImpl();
        User finduserbyuuid = userService.finduserbyuuid(code);
        if (finduserbyuuid != null) {
            response.getWriter().write("验证成功,请" + "<a href='http://localhost:8088/travel/login.html'>点击前往登录</a>");
        } else {
            response.getWriter().write("邮箱验证失败,请联系管理员!");
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        Infomsg infomsg = new Infomsg();

        String check = request.getParameter("check");
        if (checkcode_server == null || check == null || !checkcode_server.equalsIgnoreCase(check)) {
            infomsg.setFlag(false);
            infomsg.setMsg("验证码有误!");
        } else {
            User user = new User();
            try {
                BeanUtils.populate(user, parameterMap);
                UserService userService = new UserServiceImpl();
                boolean reguser = userService.reguser(user);
                if (reguser) {
                    infomsg.setFlag(true);
                    infomsg.setMsg("注册成功!");
                } else {
                    infomsg.setFlag(false);
                    infomsg.setMsg("注册失败!");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        WriteValue(infomsg, response);
    }

    public void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User logineduser = (User) session.getAttribute("logineduser");
        Infomsg infomsg = new Infomsg();

        if (logineduser != null) {
            infomsg.setFlag(true);
            infomsg.setMsg("欢迎回来," + logineduser.getUsername());
        } else {
            infomsg.setFlag(false);
        }

        //我球球你别忘记发回去好吗
        WriteValue(infomsg, response);
    }


    public void fav(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Infomsg infomsg = new Infomsg();
        User logineduser = (User) session.getAttribute("logineduser");
            UserService userService = new UserServiceImpl();
            int rid = Integer.parseInt(request.getParameter("rid"));
        RouteService routeService=new RouteServiceImpl();
        int checkbyrid = routeService.checkbyrid(rid);
        infomsg.setData_along(checkbyrid);
        if (logineduser == null) {
            infomsg.setFlag(true);
        } else {
            boolean checkfav = userService.checkfav(rid, logineduser.getUid());
            infomsg.setFlag(!checkfav);
        }
        WriteValue(infomsg, response);
    }

    public void addfav(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User logineduser = (User) session.getAttribute("logineduser");
        Infomsg infomsg = new Infomsg();
        int rid = Integer.parseInt(request.getParameter("rid"));

        if (logineduser == null) {
            infomsg.setFlag(false);
            infomsg.setMsg("收藏失败,请先登录!");
            WriteValue(infomsg, response);
            return;
        } else {
            UserService userService = new UserServiceImpl();
            boolean addfav = userService.addfav(rid, logineduser.getUid());
            infomsg.setFlag(addfav);
            if (addfav) {
                infomsg.setMsg("收藏成功!");
            } else {
                infomsg.setMsg("收藏失败,联系管理员!");
            }
            WriteValue(infomsg, response);
        }
    }

    public void listfav(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("热门排行被访问");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User logineduser = (User) session.getAttribute("logineduser");

        String curpage = request.getParameter("curpage");
        if(curpage==null||curpage.length()==0){
            curpage="1";
        }
        UserService userService=new UserServiceImpl();
        PageBean<Route> findfavbyuid = userService.findfavbyuid(Integer.parseInt(curpage), 8, logineduser.getUid());

        WriteValue(findfavbyuid,response);
    }
}
