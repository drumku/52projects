package cn.itcast.heimatravel.dao;

import cn.itcast.heimatravel.domain.PageBean;
import cn.itcast.heimatravel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 路线分页
     * @param currentpage
     * @param pagesize
     * @return
     */
    List<Route> listroute(int cid, int currentpage, int pagesize,String search);

    /**
     * 计算所有数据
     * @return
     */
    int totalcount(int cid,String search);

    /**
     * 通过rid找到某个旅游线路
     * @param rid
     * @return
     */
    Route ridfindone(int rid);

    /**
     * 查找改路线被收藏的次数
     * @param rid
     * @return
     */
    int checkbyrid(int rid);



    /**
     * 通过各种条件得到List
     * @param search
     * @param min
     * @param max
     * @return
     */
    List<Route> rankpb(String search,String curpage,int pagesize,String min,String max);


    /**
     * 查询记录总数
     * @param search
     * @param min
     * @param max
     * @return
     */
    int ranktoco(String search,String min,String max);


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
