package com.songpo.searched.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
public class MyShoppingCartPojo {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 购物车详情
     */
    private List<ShoppingCart> carts;


}

