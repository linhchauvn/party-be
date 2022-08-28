package jokes.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import party.util.CacheUtils;

public class CacheUtilsTest {
    private CacheUtils cache = CacheUtils.getInstance();

    @Test
    public void addDataToCache_Success() {
        Assertions.assertTrue(cache.addDataToCache("key"));
        Assertions.assertTrue(cache.addDataToCache("key"));
        Assertions.assertTrue(cache.addDataToCache("key"));
        Assertions.assertTrue(cache.addDataToCache("key"));
        Assertions.assertTrue(cache.addDataToCache("key"));

        Assertions.assertFalse(cache.addDataToCache("key"));
    }
}
