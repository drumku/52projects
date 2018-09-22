package cn.itcast.heimatravel.dao;

import cn.itcast.heimatravel.domain.RouteImg;

import java.util.List;

public interface ImageDao {

    /**
     * 通过rid找到对应的图片组
     * @param rid
     * @return
     */
    List<RouteImg> ridfindimage(int rid);
}
