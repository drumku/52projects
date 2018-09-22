package cn.itcast.heimatravel.dao;

import cn.itcast.heimatravel.domain.Seller;

public interface SellerDao {
    /**
     * 通过rid找到商家
     * @param rid
     * @return
     */
    Seller ridfindseller(int rid);
}
