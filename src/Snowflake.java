/**
 * ID生成器
 * Created by listening on 2017/3/24.
 */
public class Snowflake {
    private long workerId;
    private long dataCenterId;
    private long sequence;

    private long workerIdBits = 5L;
    private long dataCenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDataCenterId = -1L ^ (-1l << dataCenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long twepoch = 1288834974657L;

    protected Snowflake() {
    }

    public Snowflake(long workerId, long dataCenterId, long sequence) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.sequence = sequence;
    }

    public long nextId() {
        long timestamp = timeGen();
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 环境：
     *  jdk1.8.0_101
     *  Intel Core i3-4170  3.7GHz  8GB RAM  64位
     * 测试数据：
     * 生成1000000000个ID耗用时间: 245007毫秒       10亿
     * 生成1000000个ID耗用时间: 264毫秒             100万
     * 生成10000个ID耗用时间: 4毫秒                 1万
     * @param args
     */
    public static void main(String[] args) {
        Snowflake snowflake = new Snowflake(12, 1, 1);
        long start = System.currentTimeMillis();
        long num = 10000L;
        for (long i = 0; i < num; i++) {
            long id = snowflake.nextId();
            System.out.println("id: " + id + ", len: " + (id + "").length());
        }
        long end = System.currentTimeMillis();
        System.out.println("生成" + num + "个ID耗用时间: " + (end - start) + "毫秒");
    }
}
