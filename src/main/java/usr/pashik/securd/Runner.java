package usr.pashik.securd;

import org.redisson.Redisson;
import redis.clients.jedis.Jedis;

import java.util.Set;

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
        System.out.println("jedis = " + value);

        Redisson redisson = Redisson.create();
        Set<String> set = redisson.getSet("mySet");
        set.add("someString");
        System.out.println("redisson = " + set.iterator().next());
        redisson.shutdown();
    }
}
