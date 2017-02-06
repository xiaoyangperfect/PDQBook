package com.wehealth.pdqbook.tool;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaoyang on 1/3/2017.
 */

public class ArrayUtils {
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static <T> void swapElement(List<T> list, int idxA, int idxB) {
        T tmp = list.get(idxA);
        list.set(idxA, list.get(idxB));
        list.set(idxB, tmp);
    }

    public static <T> boolean contains(T[] array, T value) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (T item :
                array) {
            if (CommonUtil.equalsWithAllowedNull(item, value)) {
                return true;
            }
        }
        return false;
    }

    public static <T> String joinString(Iterable<T> iterable, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = iterable.iterator();
        // add first without separator
        if (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        while (iterator.hasNext()) {
            sb.append(separator);
            sb.append(iterator.next());
        }
        return sb.toString();
    }
}
