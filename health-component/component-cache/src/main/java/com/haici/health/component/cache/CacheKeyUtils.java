package com.haici.health.component.cache;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description: <br/>
 *
 * @author dengzhineng
 * @date: 2016年12月1日 上午10:27:10
 * @version 1.0
 * @since JDK 1.7
 */
public class CacheKeyUtils {

    public static final String split = ":";

    /**
     * 由若干ID生成一个key
     * 
     * @param pre 前缀
     * @param version 版本号
     * @param objects 生成该key所有对应的ID
     * @return
     */
    public static String genKey(String pre, int version, Object... objects) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(pre).append(CacheKeyUtils.split);
        for (Object o : objects) {
            if (o == null) {
                sb.append(String.valueOf(o)).append(CacheKeyUtils.split);
            } else if (o.getClass().isArray()) {
                sb.append(arrayToString(o)).append(CacheKeyUtils.split);
            } else if (o instanceof Collection) {
                sb.append(colletionToString((Collection<?>) o)).append(CacheKeyUtils.split);
            } else {
                sb.append(String.valueOf(o)).append(CacheKeyUtils.split);
            }
        }
        sb.append(version);
        return sb.toString();
    }

    /**
     *
     * @param pre 前缀
     * @param version 版本号
     * @param objects 生成该key所有对应的ID
     * @return
     */
    public static String genDefaultKey(String pre, Object... objects) {
        return genKey(pre, 1, objects);
    }

    private static String arrayToString(Object o) {
        int len = Array.getLength(o);
        if (len == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(256);
            sb.append('[');
            for (int i = 0; i < len; i++) {
                sb.append(String.valueOf(Array.get(o, i))).append(",");
            }
            if (sb.length() > 1) {
                sb.setLength(sb.length() - 1);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    private static String colletionToString(Collection<?> col) {
        int len = col.size();
        if (len == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(256);
            sb.append('[');
            for (Object o : col) {
                sb.append(String.valueOf(o)).append(",");
            }
            if (sb.length() > 1) {
                sb.setLength(sb.length() - 1);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    /**
     * 生成多个key，并形成原始值对目标key值的映射
     * 
     * @param pre
     * @param version
     * @param objects
     * @return 原始值对目标key值
     */
    public static <T> Map<T, String> genMultiT2Key(String pre, int version, Collection<T> objects) {
        Map<T, String> map = new LinkedHashMap<T, String>();
        for (T os : objects) {
            map.put(os, genKey(pre, version, os));
        }
        return map;
    }

    /**
     * 生成多个key，并形成目标key值对原始值的映射
     * 
     * @param pre
     * @param version
     * @param objects 单一对象的集合
     * @return 目标key值对原始值的映射
     */
    public <T> Map<String, T> genMultiKey2T(String pre, int version, Collection<T> objects) {
        Map<String, T> map = new LinkedHashMap<String, T>();
        for (T os : objects) {
            map.put(genKey(pre, version, os), os);
        }
        return map;
    }

    /**
     * 由（若干ID）組生成一組key。适合若干ID生成一個key的情況。
     * 
     * @param pre 前缀
     * @param version 版本号
     * @param objects List<IDs>,每一个值裏面可能有若干ID
     * @return
     */
    public static List<String> genMultiKey(String pre, int version, Collection<?> objects) {
        List<String> l = new ArrayList<String>(objects.size());
        for (Object os : objects) {
            l.add(genKey(pre, version, os));
        }
        return l;
    }
}
