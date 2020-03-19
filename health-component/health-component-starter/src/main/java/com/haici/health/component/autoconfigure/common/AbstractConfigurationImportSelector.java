package com.haici.health.component.autoconfigure.common;

import com.haici.health.component.autoconfigure.cache.CacheClientConfigurations;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.ParameterizedType;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 22:04
 * @Description:
 */
public class AbstractConfigurationImportSelector<T extends Enum> implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        Class<? extends Enum> enumClass = getTClass();
        Enum<? extends Enum<?>>[] enums = enumClass.getEnumConstants();
        String[] imports = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            imports[i] = ConfigurationsUtils.getConfigurationClass(enums[i]);
        }
        return imports;
    }


    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
