package com.haici.health.edu.controller;

import com.haici.health.base.facade.vo.BaseFacadeReqVo;
import com.haici.health.common.vo.BaseResult;
import com.haici.health.common.vo.HcContext;
import com.haici.health.component.cache.CacheClient;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.lock.DistributedLockTemplate;
import com.haici.health.component.lock.LockCallback;
import com.haici.health.edu.entity.Order;
import com.haici.health.edu.entity.OrderItem;
import com.haici.health.edu.entity.User;
import com.haici.health.edu.facade.EduFacade;
import com.haici.health.edu.mapper.OrderItemMapper;
import com.haici.health.edu.mapper.OrderMapper;
import com.haici.health.edu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: QiaoYang
 * @Date: 2020/1/21 16:51
 * @Description:
 */
@RestController
public class EduController {

    @Autowired
    private EduFacade eduFacade;

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private DistributedLockTemplate distributedLockTemplate;

    @Autowired
    private IDGenerator idGenerator;

    ExecutorService executor = Executors.newFixedThreadPool(50);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @GetMapping("/api/edu/getUserById")
    public BaseResult<User> getUserById(Long id) {
        User user = userMapper.selectById(id);

        return BaseResult.succssResult(user);
    }


    @PostMapping("/api/edu/addUser")
    public BaseResult<User> addUser(User user) {
        user.setId(idGenerator.next("user_id").id);
        userMapper.insert(user);
        return BaseResult.succssResult(user);
    }

    @PostMapping("/api/edu/addOrder")
    public BaseResult<Void> addOrder(OrderItem orderItem) {
        long userId = idGenerator.next("user_id").id;
        long id = idGenerator.next("order_id").id;
        long itemId = idGenerator.next("item_id").id;
        orderItem.setUserId(userId);
        orderItem.setOrderId(id);
        orderItem.setItemId(itemId);

        Order order = new Order();
        order.setUserId(orderItem.getUserId());
        order.setOrderId(id);

        orderMapper.insert(order);
        orderItemMapper.insert(orderItem);
        return BaseResult.succssResult(null);
    }


    @GetMapping("/api/edu/listOrderItems")
    public BaseResult<List<OrderItem>> listOrderItems() {
        List<OrderItem> users = orderItemMapper.selectByMap(new HashMap<>());

        return BaseResult.succssResult(users);
    }


    @GetMapping("/api/edu/listAllUser")
    public BaseResult<List<User>> listAllUser() {
        List<User> users = userMapper.selectByMap(new HashMap<>());

        return BaseResult.succssResult(users);
    }

    @GetMapping("/api/edu/test")
    public BaseResult<String> test() {
        return BaseResult.succssResult("api test is ok!");
    }

    @GetMapping("/api/edu/test1")
    public BaseResult<String> test1() {
        return eduFacade.test(BaseFacadeReqVo.newInstance(null));
    }


    @GetMapping("/api/edu/setCache")
    public BaseResult<Void> setCache(String key, String value) {
        cacheClient.set(key, value);
        HcContext context = new HcContext();
        context.setAttr("xxx", "123");
        cacheClient.set("hcon", context);
        return BaseResult.succssResult(null);
    }


    @GetMapping("/api/edu/getCache")
    public BaseResult<String> getCache(String key) {
        String value = cacheClient.get(key);

        return BaseResult.succssResult(value);
    }

    @GetMapping("/api/edu/getHCCache")
    public BaseResult<HcContext> getHCCache() {
        HcContext value = cacheClient.get("hcon");

        return BaseResult.succssResult(value);
    }


    @GetMapping("/api/edu/incr")
    public BaseResult<Long> incr() {

        long id = cacheClient.incr("edu-id");

        return BaseResult.succssResult(id);
    }


    @GetMapping("/api/edu/genId")
    public BaseResult<Long> genId() {

        long id = idGenerator.next("xxxxx").id;

        return BaseResult.succssResult(id);
    }

    @GetMapping("/api/edu/lockTest")
    public BaseResult<Void> lockTest() {

        HcContext hcContext = new HcContext();
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                distributedLockTemplate.execute("xxxxxx", 30000, new LockCallback<String>() {
                    @Override
                    public String onGetLock() throws InterruptedException {
                        Long id = hcContext.getAttr("id");
                        if (null == id) {
                            id = 0L;
                            hcContext.setAttr("id", id);
                        } else {
                            id = id + 1;
                            hcContext.setAttr("id", id);
                        }
                        System.out.println(id);

                        return null;
                    }

                    @Override
                    public String onTimeout() throws InterruptedException {
                        return null;
                    }
                });
            });
        }

        return BaseResult.succssResult(null);
    }
}
