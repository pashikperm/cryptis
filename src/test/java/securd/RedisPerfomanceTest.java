/**
 * Created by pashik on 13.04.2014 21:21.
 */
package securd;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static securd.Profiler.*;

public class RedisPerfomanceTest {
    final int RANDOM_SEED = 0;
    final int MAXDEPOSIT = (int) 1e8;

    final boolean USE_SECURD = true;
    final int SECURD_PORT = 7070;

    Scanner in;
    PrintWriter out;
    static Jedis r;
    Connection c;

    public static void main(String[] args) {
        try {
            new RedisPerfomanceTest().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void run() {
        initIO();

        int rowCount = 100000;
        int queryCount = 3000;
        int responseLimit = 10;

        insert(rowCount);
        selectByEqual(rowCount, queryCount);
        selectByRange(rowCount, queryCount, responseLimit);
        selectTop(queryCount, responseLimit);
        updateSet(rowCount, queryCount);
        updateSum(rowCount, queryCount);
        delete(rowCount, queryCount);

        closeIO();
    }

    private void prepareDummyData(Map<String, String> data, int i) {
        data.put("id", Integer.toString(i));
        data.put("name", "Nickolay" + i);
        data.put("passport", "5143 614000" + i);
        data.put("address", "Russia, Perm" + i);
        data.put("phone", "+7 919 000 00 00" + i);
        data.put("password", "nick-is-cool" + i);
        data.put("email", "pacan_at_perm@gmail.com" + i);
        data.put("ssc", "3121-1232-1232-1231" + i);
    }

    private int userDeposit(int i) {
        return i * 10;
    }

    private void insert(int rowCount) {
        out.println("[insert]");

        r.flushDB();

        Map<String, String> user = new HashMap<>();

        start();
        for (int i = 1; i <= rowCount; i++) {
            user.clear();
            prepareDummyData(user, i);

            String id = Integer.toString(i);
            r.hmset("users:" + id, user);
            r.zadd("users:deposit", userDeposit(i), id);
        }
        finish();
        printPerfomance(rowCount);
    }

    private void insertBatch(int rowCount) {
        out.println("[insert batch]");

        r.flushDB();

        Map<String, String> user = new HashMap<>();
        Pipeline p = r.pipelined();

        start();
        for (int i = 1; i <= rowCount; i++) {
            user.clear();
            prepareDummyData(user, i);

            String id = Integer.toString(i);
            p.hmset("users:" + id, user);
            p.zadd("users:deposit", userDeposit(i), id);
        }
        p.sync();
        finish();
        printPerfomance(rowCount);
    }

    private void selectByEqual(int rowCount, int queryCount) {
        out.println("[select by equal]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            String id = Integer.toString(rnd.nextInt(rowCount));

            r.hgetAll("users:" + id);
        }
        finish();
        printPerfomance(queryCount);
    }

    private void selectByRange(int rowCount, int queryCount, int limit) {
        out.println("[select by range limit " + limit + "]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            double border = rnd.nextInt(rowCount * 10);

            r.zrangeByScore("users:deposit", border, MAXDEPOSIT, 0, limit);
        }
        finish();
        printPerfomance(queryCount);
    }

    private void selectTop(int queryCount, int limit) {
        out.println("[select top limit up to " + limit + "]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            r.zrevrange("users:deposit", 0, rnd.nextInt(limit));
        }
        finish();
        printPerfomance(queryCount);
    }

    private void updateSet(int rowCount, int queryCount) {
        out.println("[update set]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            String id = Integer.toString(rnd.nextInt(rowCount));

            r.hset("users:" + id, "name", "Nickolay" + rnd.nextInt(rowCount));
        }
        finish();
        printPerfomance(queryCount);
    }

    private void updateSum(int rowCount, int queryCount) {
        out.println("[update sum]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            String id = Integer.toString(rnd.nextInt(rowCount));

            r.zincrby("users:deposit", rnd.nextInt(rowCount * 10), id);
        }
        finish();
        printPerfomance(queryCount);
    }

    private void delete(int rowCount, int queryCount) {
        out.println("[delete]");

        Random rnd = new Random(RANDOM_SEED);

        start();
        for (int i = 0; i < queryCount; i++) {
            String id = Integer.toString(rnd.nextInt(rowCount));

            r.del("users:" + id);
            r.zrem("users:deposit", id);
        }
        finish();
        printPerfomance(queryCount);
    }

    private void initDummyData(int rowCount) {
        r.flushDB();

        Map<String, String> user = new HashMap<>();
        Pipeline p = r.pipelined();
        for (int i = 1; i <= rowCount; i++) {
            user.clear();
            prepareDummyData(user, i);

            String id = Integer.toString(i);
            p.hmset("users:" + id, user);
            p.zadd("users:deposit", userDeposit(i), id);
        }
        p.sync();
    }

    private void closeIO() {
        in.close();
        out.close();
        r.quit();
    }

    protected void initIO() {
        in = new Scanner(System.in);
        out = new PrintWriter(System.out);
        r = createRedisClient();
        out.println("[generated client]");
        out.println("> is securd " + USE_SECURD);
        out.println();
    }

    private Jedis createRedisClient() {
        Jedis r = USE_SECURD ? new Jedis("localhost", SECURD_PORT) : new Jedis("localhost");
        r.auth(USE_SECURD ? "pashik8:pashik8" : "pashik");
        return r;
    }

    private void printPerfomance(int queryCount) {
        out.println("> done " + queryCount + " in " + time() + " s");
        out.println("> speed is " + countPerSecS(queryCount) + " qps\n");
        out.flush();
    }
}
