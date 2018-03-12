package com.songpo.cache;

import com.songpo.domain.BusinessMessage;
import com.songpo.domain.MyShoppingCartPojo;
import com.songpo.entity.SlUser;
import com.songpo.service.MyShoppingCartService;
import com.songpo.validator.OrderValidator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ShoppingCartCache extends BaseCache<MyShoppingCartPojo> {

    public ShoppingCartCache(RedisTemplate<String, MyShoppingCartPojo> redisTemplate) {
        super("com.songpo.seached:shoppingCart:", redisTemplate, 7200L, TimeUnit.SECONDS, true);
    }
}
