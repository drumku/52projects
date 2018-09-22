package cn.itcast.heimatravel.service;

import cn.itcast.heimatravel.domain.Catagory;

import java.util.List;

public interface CatagoryService {
    /**
     * 查出目录
     * @return
     */
    List<Catagory> findcata();
}
