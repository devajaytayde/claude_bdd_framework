package com.framework.utils;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe context for sharing data between Cucumber step definitions
 */
public class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> contextMap =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        contextMap.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) contextMap.get().get(key);
    }

    public static boolean containsKey(String key) {
        return contextMap.get().containsKey(key);
    }

    public static void clear() {
        contextMap.get().clear();
    }

    // Convenience methods for common types
    public static void setApiResponse(Response response) {
        set("API_RESPONSE", response);
    }

    public static Response getApiResponse() {
        return get("API_RESPONSE");
    }

    public static void setUserId(String userId) {
        set("USER_ID", userId);
    }

    public static String getUserId() {
        return get("USER_ID");
    }
}
