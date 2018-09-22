package cn.itcast.heimatravel.dao;

import cn.itcast.heimatravel.domain.Catagory;

import java.util.List;

public interface CatagoryDao {

    /**
     * 查出目录
     * @return
     */
    List<Catagory> findcata();
}
