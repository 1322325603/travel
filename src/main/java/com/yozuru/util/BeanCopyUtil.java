package com.yozuru.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yozuru
 */

public class BeanCopyUtil {
    public static <V> V copyBean(Object source,Class<V> targetClass) {
        V target = null;
        try {
            target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    public static <O,V> List<V> copyBeanList(List<O> source, Class<V> targetClass){
        return source.stream()
                .map(o -> copyBean(o,targetClass))
                .collect(Collectors.toList());
    }
}
