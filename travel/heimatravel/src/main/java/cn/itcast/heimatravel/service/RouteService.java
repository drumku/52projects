package cn.itcast.heimatravel.service;

import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;

import java.util.List;

public interface RouteService {
    /**
     * 路线分页
     * @param currentpage
     * @param pagesize
     * @return
     */
    PageBean<Route> listroute(String cid,String currentpage, String pagesize,String search);

    /**
     * 详情查询
     * @param rid
     * @return
     */
    Route detailr(int rid);

    /**
     * 查找改路线被收藏的次数
     * @param rid
     * @return
     */
    int checkbyrid(int rid);

    /**
     * 通过各种条件得到pb
     * @param search
     * @param min
     * @param max
     * @return
     */
    PageBean<Route> rankpb(String search,String curpage,int pagesize,String min,String max);

    /**
     * 返回最新旅游排行榜
     * @return
     */
    List<Route> latestrank();

    /**
     * 返回主题旅行排行
     * @return
     */
    List<Route> themerank();

}
