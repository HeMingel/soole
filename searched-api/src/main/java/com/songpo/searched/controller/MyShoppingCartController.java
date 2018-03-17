package com.songpo.searched.controller;

import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.MyShoppingCartPojo;
import com.songpo.searched.domain.ShoppingCart;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.service.MyShoppingCartService;
import com.songpo.searched.service.ProductService;
import com.songpo.searched.service.RepositoryService;
import com.songpo.searched.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

@Api(description = "购物车管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/myShoppingCart")
public class MyShoppingCartController {

    @Autowired
    private ShoppingCartCache cache;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private MyShoppingCartService shoppingCartService;

    /**
     * 购物车添加
     *
     * @param pojo
     */
    @PostMapping("add")
    public BusinessMessage<Json> addmyShoppingCart(MyShoppingCartPojo pojo) {
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
     * @return
     */
    @GetMapping("serch")
    public BusinessMessage<MyShoppingCartPojo> findCart(String uid) {
        BusinessMessage message = new BusinessMessage();
        message.setMsg("查询成功");
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
                MyShoppingCartPojo pojo = this.cache.get(uid);
                List<ShoppingCart> list = new ArrayList<>();
                ShoppingCart shoppingCart = null;
                if (null != pojo)
                {
                    for (ShoppingCart sc : pojo.getCarts())
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
                                shoppingCart = new ShoppingCart();;
                                shoppingCart.setGoodName(slProduct.getName());// TODO 商品名称
                                shoppingCart.setCounts(sc.getCounts());// TODO 加入购物车商品的数量
                                shoppingCart.setImageUrl(slProduct.getImageUrl()); // TODO 商品图片
                                SlRepository repository = this.repositoryService.selectOne(new SlRepository() {{
                                    /*setTagId(sc.getTagId());*/
                                    setProductId(sc.getGoodId());
                                }});
                                shoppingCart.setPulse(repository.getPulse());// TODO 了豆
                                shoppingCart.setSaleType(repository.getSaleType());// TODO 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                shoppingCart.setPrice(repository.getPrice());// TODO 商品价格
                                /**
                                 * TODO 查询标签名称 返回null的话 前台就显示失效
                                 */
                                shoppingCart.setTagName(this.shoppingCartService.findTagNameByTagId(sc.getTagId(), sc.getGoodId()));
                                list.add(shoppingCart);
                            } else
                            {
                                message.setMsg("商品已下架");
                                message.setSuccess(true);
                            }
                        }
                    }
                }
                pojo.setCarts(list);// TODO 把查询好的list 加入pojo中
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

