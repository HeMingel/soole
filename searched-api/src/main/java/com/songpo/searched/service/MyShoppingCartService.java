package com.songpo.searched.service;

import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SpecificationNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyShoppingCartService {
    @Autowired
    private SpecificationNameMapper specificationNameMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartCache cache;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepositoryService productRepositoryService;

    /**
     * 新增购物车
     *
     * @param pojo
     * @return
     */
    public BusinessMessage addmyShoppingCart(CMShoppingCart pojo) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(pojo.getUserId()))
        {
            message.setMsg("用户ID为空");
        } else
        {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(pojo.getUserId());
            }});
            if (null != user)
            {
                this.cache.put(pojo.getUserId(), pojo);
                message.setMsg("添加成功");
                message.setSuccess(true);
                message.setData(1);
            } else
            {
                message.setMsg("传入的用户ID不存在");
            }
        }
        return message;
    }

    /**
     * 查询购物车
     *
     * @param uid
     * @return
     */
    public BusinessMessage findCart(String uid) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(uid))
        {
            message.setMsg("用户ID为空");
            message.setData(null);
        } else
        {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user)
            {
                CMShoppingCart pojo = this.cache.get(uid);
                List<CMGoods> list = new ArrayList<>();
                CMGoods CMGoods = null;
                if (null != pojo)
                {
                    for (CMGoods sc : pojo.getCarts())
                    {
                        if (StringUtils.isEmpty(sc.getGoodId()))
                        {
                            message.setMsg("获取商品ID错误");
                        } else
                        {
                            SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(sc.getGoodId());
                                setIsSoldout(false);
                            }});
                            if (null != slProduct)
                            {
                                CMGoods = new CMGoods();
                                CMGoods.setGoodName(slProduct.getName());// 商品名称
                                CMGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                CMGoods.setImageUrl(slProduct.getImageUrl()); // 商品图片
                                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                    setProductId(sc.getGoodId());
                                }});
                                CMGoods.setPulse(repository.getPulse());// 了豆
                                CMGoods.setSaleType(repository.getSaleType());// 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                CMGoods.setPrice(repository.getPrice());// 商品价格
                                /**
                                 * 查询标签名称 返回null的话 前台就显示失效
                                 */
                                CMGoods.setTagName(this.specificationNameMapper.findSpecificationContentBySpecificationId(sc.getSpecificationId(), sc.getGoodId()));
                                list.add(CMGoods);
                            } else
                            {
                                message.setMsg("商品已下架");
                                message.setSuccess(true);
                            }
                        }
                    }
                }
                pojo.setCarts(list);// 把查询好的list 加入pojo中
                if (StringUtils.isEmpty(pojo))
                {
                    message.setMsg("查询状态为空");
                    message.setData(null);
                    message.setSuccess(true);
                } else
                {
                    message.setData(pojo);
                    message.setSuccess(true);
                    message.setMsg("查询成功");
                }
            } else
            {
                message.setMsg("传入用户ID不存在");
            }
        }
        return message;
    }
}
