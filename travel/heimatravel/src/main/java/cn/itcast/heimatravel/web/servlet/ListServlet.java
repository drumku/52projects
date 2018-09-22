package cn.itcast.heimatravel.web.servlet;

import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.service.Impl.RouteServiceImpl;
import cn.itcast.heimatravel.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@WebServlet("/list/*")
public class ListServlet extends BaseServlet {
    public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("test/html;charset=utf-8");
        String search = request.getParameter("search");

        if (search == null || "null".equals(search)) {
            search = "";
        } else {
            search = URLDecoder.decode(search, "utf-8");
            System.out.println("search:" + search);
        }
    /*    String rename="qwe";
           search=new String(search.getBytes("ISO-8859-1"),"UTF-8");*/

        String cid = request.getParameter("cid");
        if ("null".equals(cid)) {
            cid = "0";
        }
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        if (currentPage == null || currentPage.length() == 0) {
            currentPage = "1";
        }
        if (pageSize == null || currentPage.length() == 0) {
            pageSize = "10";
        }
        RouteService routeService = new RouteServiceImpl();
        PageBean<Route> listroute = routeService.listroute(cid, currentPage, pageSize, search);
        WriteValue(listroute, response);
    }

    public void detailone(HttpServletRequest request, HttpServletResponse response) {
        String rid = request.getParameter("rid");
        System.out.println("被访问");
        RouteService routeService = new RouteServiceImpl();
        int irid = Integer.parseInt(rid);
        Route detailr = routeService.detailr(irid);
        WriteValue(detailr, response);
    }

    public void rank(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String search = request.getParameter("search");
        if (search == null || search.length() == 0) {
            search = "";
        }
        search = new String(search.getBytes("ISO-8859-1"), "UTF-8");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String curpage = request.getParameter("curpage");
        int pagesize = 6;
        if ("null".equals(curpage) || curpage == null || curpage.length() == 0) {
            curpage = "1";
        }
        RouteService routeService = new RouteServiceImpl();
        PageBean<Route> rankpb = routeService.rankpb(search, curpage, pagesize, min, max);
        WriteValue(rankpb, response);
    }

    public void latest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        RouteService routeService = new RouteServiceImpl();
        List<Route> latestrank = routeService.latestrank();
        WriteValue(latestrank, response);
    }

    public void theme(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        RouteService routeService=new RouteServiceImpl();
        List<Route> themerank = routeService.themerank();
        WriteValue(themerank,response);
    }
}
