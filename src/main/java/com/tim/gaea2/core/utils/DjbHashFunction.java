package com.tim.gaea2.core.utils;

/**
 * djb2， 俗称“Times33”算法
 * Created by tianzhonghai on 2017/6/28.
 */
public class DjbHashFunction {
    public static int DJB_HASH(String value) {
        long hash = 5381;
        for (int i = 0; i < value.length(); i++) {
            hash = ((hash << 5) + hash) + value.charAt(i);
        }
        return (int) hash;
    }
    public static int DJB_HASH(byte[] value, int offset, int length) {
        long hash = 5381;
        final int end = offset + length;
        for (int i = offset; i < end; i++) {
            hash = ((hash << 5) + hash) + value[i];
        }
        return (int) hash;
    }

    public static int hash(String routing) {
        return DJB_HASH(routing);
    }

    public static int hash(String type, String id) {
        long hash = 5381;
        for (int i = 0; i < type.length(); i++) {
            hash = ((hash << 5) + hash) + type.charAt(i);
        }
        for (int i = 0; i < id.length(); i++) {
            hash = ((hash << 5) + hash) + id.charAt(i);
        }
        return (int) hash;
    }
}
