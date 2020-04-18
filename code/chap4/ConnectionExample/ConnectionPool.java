package chap4.ConnectionExample;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    // 连接池
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    // 构造函数，初始化连接池最大连接数
    public ConnectionPool(int initialSize) {
        for (int i = 0;i < initialSize;++i) {
            pool.addLast(ConnectionDriver.createConnection());
        }
    }

    // 线程释放连接。注意释放时要对连接池加锁，并唤醒所有等待在连接池上的线程
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 线程获取连接。采用超时等待的方式，在 mills 内没有获得连接，则返回
    // 同样需要对连接池进行加锁
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long futuer = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = futuer - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
