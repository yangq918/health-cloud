package com.haici.health.component.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 * @Auther: QiaoYang
 * @Date: 2020/3/14 16:18
 * @Description:
 */
public interface DistributedReentrantLock {

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException;

    public void unlock();
}
