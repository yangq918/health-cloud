package com.haici.health.component.lock;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 16:33
 * @Description:
 */
public interface DistributedLockTemplate {

    public <T> T execute(String lockId, int timeout, LockCallback<T> callback);
}
