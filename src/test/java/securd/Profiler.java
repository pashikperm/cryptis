/**
 * Created by pashik on 13.04.2014 21:22.
 */
package securd;

public class Profiler {
    static long currentTime;
    static long evaluationTime;

    static void start() {
        currentTime = System.currentTimeMillis();
    }

    static void finish() {
        evaluationTime = System.currentTimeMillis() - currentTime;
    }

    static double time() {
        return evaluationTime/1000.0;
    }

    static double countPerSec(int count) {
        return count*1000.0/evaluationTime;
    }

    static String countPerSecS(int count) {
        return String.format("%.2f", count*1000.0/evaluationTime).replace(',', '.');
    }
}
