package org.scbit.lsbi.redisconfig.controller;

import org.scbit.lsbi.redisconfig.service.RedisService;
import org.scbit.lsbi.redisconfig.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Controller
public class RedisController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/hello/{id}")
    public String hello(@PathVariable("id") String id) {
        String result = "";

        //查询缓存中是否存在
        boolean hasKey = redisUtils.exists(id);
        if (hasKey) {
            //获取缓存
            Object object = redisUtils.get(id);
            System.out.println("从缓存获取的数据"+ object);
            result = object.toString();
        } else {
            //从数据库获取对象
            System.out.println("从数据库中获取数据");
            //模拟从数据库获取数据
            result = redisService.getById(id);
            //数据插入缓存
            redisUtils.set(id, result, 10L, TimeUnit.MINUTES);
            System.out.println("从数据库中获取数据");
        }

        return result;
    }
}
