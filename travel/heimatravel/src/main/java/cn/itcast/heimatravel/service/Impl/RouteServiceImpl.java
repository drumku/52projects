package cn.itcast.heimatravel.service.Impl;

import cn.itcast.heimatravel.dao.ImageDao;
import cn.itcast.heimatravel.dao.Impl.ImageDaoImpl;
import cn.itcast.heimatravel.dao.Impl.RouteDaoImpl;
import cn.itcast.heimatravel.dao.Impl.SellerDaoImpl;
import cn.itcast.heimatravel.dao.RouteDao;
import cn.itcast.heimatravel.dao.SellerDao;
import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;
import cn.itcast.heimatravel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    @Override
    public PageBean<Route> listroute(String cid, String currentpage, String pagesize,String search) {
        int icid = Integer.parseInt(cid);
        int icurenp = Integer.parseInt(currentpage);
        int ipz = Integer.parseInt(pagesize);

        List<Route> listroute = routeDao.listroute(icid, icurenp, ipz ,search);
        PageBean<Route> routePageBean = new PageBean<>();
        routePageBean.setList(listroute);
        routePageBean.setCurrentPage(icurenp);
        routePageBean.setPageSize(ipz);
        int size = routeDao.totalcount(icid, search);
        routePageBean.setTotalCount(size);
        routePageBean.setTotalPage(size %ipz==0?size/ipz:size/ipz+1);
        return routePageBean;
    }

    @Override
    public Route detailr(int rid) {
        Route ridfindone = routeDao.ridfindone(rid);
        SellerDao sellerDao=new SellerDaoImpl();
        ridfindone.setSeller(sellerDao.ridfindseller(rid));
        ImageDao imageDao=new ImageDaoImpl();
        ridfindone.setRouteImgList(imageDao.ridfindimage(rid));
        return ridfindone;
    }

    @Override
    public int checkbyrid(int rid) {
        return routeDao.checkbyrid(rid);
    }

    @Override
    public PageBean<Route> rankpb(String search,String curpage, int pagesize,String min, String max) {
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(curpage));
        List<Route> rankpb = routeDao.rankpb(search, curpage, pagesize,min, max);
        pageBean.setList(rankpb);
        int tc=routeDao.ranktoco(search,min,max);
        pageBean.setTotalCount(tc);
        pageBean.setTotalPage(tc%pagesize==0?tc/pagesize:tc/pagesize+1);
        pageBean.setPageSize(pagesize);
        return pageBean;
    }

    @Override
    public List<Route> latestrank() {
        return routeDao.latestrank();
    }

    @Override
    public List<Route> themerank() {
        return routeDao.latestrank();
    }
}
