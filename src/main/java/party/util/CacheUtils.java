package party.util;

import org.joda.time.DateTime;
import party.model.CacheDetail;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CacheUtils {

    private static Map<String, CacheDetail> map = new HashMap<>();
    private static CacheUtils instance;

    private CacheUtils() {

    }

    public static CacheUtils getInstance() {
        if (instance == null) {
            instance = new CacheUtils();
            Runnable cleaner = () -> {
                map.entrySet().forEach(item -> {
                    if (item.getValue() != null && item.getValue().getExpiredAt() <= System.currentTimeMillis()) {
                        map.remove(item);
                    }
                });
            };
            Executors.newScheduledThreadPool(1)
                    .scheduleWithFixedDelay(cleaner, 0, 60, TimeUnit.SECONDS);

        }
        return instance;
    }

    public boolean addDataToCache(String key) {
        DateTime now = new DateTime();
        int minute = now.minuteOfHour().get();
        String compositeKey = key + minute;
        CacheDetail value = map.get(compositeKey);
        if (value == null) {
            value = new CacheDetail();
            value.setCount(1);
            value.setExpiredAt(now.plusMinutes(2).getMillis());
            value.setTime(now.getMillis());
            map.put(compositeKey, value);
        } else if (value.getCount() >= 5) {
            return false;
        } else {
            value.setCount(value.getCount() + 1);
        }
        return true;
    }
}
