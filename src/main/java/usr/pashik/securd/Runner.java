package usr.pashik.securd;

import redis.clients.jedis.Jedis;

/**
 * Created by pashik on 05.03.14 22:52.
 */
public class Runner {
    public static void main(String[] args) {
        System.out.println("usr.pashik.securd.Runner.main");
        System.out.println("args = [" + args + "]");

        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");

        System.out.println(value);
    }
}
