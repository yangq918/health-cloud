package com.haici.health.component.id.snowflake.support;

import com.haici.health.component.id.ID;

import java.util.Random;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 14:04
 * @Description:
 */
public class SnowflakeWorker {


    private static final Random RANDOM = new Random();


    /**
     * 起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量
     * Tue Mar 17 14:26:56 CST 2020
     */
    private final long twepoch = 1584426416221L;

    /**
     * 保存该节点的workId
     */
    private long workerId;

    /**
     * workID占用的比特数
     */
    private final long workerIdBits = 10L;
    /**
     * 最大能够分配的workerid =1023
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);


    /**
     * 上一次请求id时所用的时间戳
     */
    private long lastTimestamp = -1L;


    /**
     * 序列号
     */
    private long sequence = 0L;


    /**
     * 自增序列号
     */
    private final long sequenceBits = 12L;
    /**
     * workID左移位数为自增序列号的位数
     */
    private final long workerIdShift = sequenceBits;
    /**
     * 时间戳的左移位数为 自增序列号的位数+workID的位数
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    /**
     * 后12位都为1
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    public SnowflakeWorker(String nacosAddress, String ip, int port) {
        SnowflakeNacosHolder holder = new SnowflakeNacosHolder(nacosAddress, ip, port);
        boolean initFlag = holder.init();
        if (initFlag) {
            workerId = holder.getWorkId();
        } else {
            throw new IllegalStateException("snowflake worker not init ok");
        }

        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalStateException("workerID must gte 0 and lte 1023");
        }
    }


    public synchronized ID get() {
        /**
         * 生成id号需要的时间戳和序列号
         * 1. 时间戳要求大于等于上一次用的时间戳（主要解决机器工作时NTP时间同步问题）
         * 2. 序列号在时间戳相等的情况下要递增，大于的情况下回到起点
         */

        // 获取当前时间戳，timestamp用于记录生成id的时间戳
        long timestamp = timeGen();

        // 如果比上一次记录的时间戳早，也就是NTP造成时间回退了
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            // 如果相差小于5
            if (offset <= 5) {
                try {
                    // 等待 2*offset ms就可以唤醒重新尝试获取锁继续执行。当然，在这段时间内lastTimestamp很可能又被更新了
                    wait(offset << 1);
                    // 重新获取当前时间戳，理论上这次应该比上一次记录的时间戳迟了
                    timestamp = timeGen();
                    // 如果还是早，这绝对有问题的
                    if (timestamp < lastTimestamp) {
                        throw new IllegalStateException("gen id has exeption");
                    }
                } catch (InterruptedException e) {
                    throw new IllegalStateException("gen id has InterruptedException exeption", e);
                }
            }
            // 如果差的比较大，直接返回异常
            else {
                throw new IllegalStateException("gen id has exeption");
            }
        }

        // 如果从上一个逻辑分支产生的timestamp仍然和lastTimestamp相等
        if (lastTimestamp == timestamp) {
            // 自增序列+1然后取后12位的值
            sequence = (sequence + 1) & sequenceMask;
            // seq 为0的时候表示当前毫秒12位自增序列用完了，应该用下一毫秒时间来区别，否则就重复了
            if (sequence == 0) {
                // 对seq做随机作为起始，主要出于DB分表均匀的考虑
                sequence = RANDOM.nextInt(100);
                // 生成比lastTimestamp滞后的时间戳，这里不进行wait，因为很快就能获得滞后的毫秒数
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 如果是新的ms开始，序列号要重新回到大致的起点
            sequence = RANDOM.nextInt(100);
        }
        // 记录这次请求id的时间戳，用于下一个请求进行比较
        lastTimestamp = timestamp;

        /**
         * 利用生成的时间戳、序列号和workID组合成id
         */
        long id = ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
        return new ID(id, lastTimestamp);
    }

    /**
     * 自旋生成直到比lastTimestamp之后的当前时间戳
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
