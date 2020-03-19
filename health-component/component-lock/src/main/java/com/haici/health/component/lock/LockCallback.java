package com.haici.health.component.lock;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 14:04
 * @Description:
 */
public interface LockCallback<T> {

    public T onGetLock() throws InterruptedException;

    public T onTimeout() throws InterruptedException;
}
