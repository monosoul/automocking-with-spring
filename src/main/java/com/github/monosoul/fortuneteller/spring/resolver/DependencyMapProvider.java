package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.spring.resolver.CommonUtils.getSimpleName;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Slf4j
class DependencyMapProvider implements Function<ConfigurableListableBeanFactory, Map<DecoratorType, Set<String>>> {

    @Override
    public Map<DecoratorType, Set<String>> apply(@NonNull final ConfigurableListableBeanFactory beanFactory) {
        val dependencies = new HashMap<DecoratorType, Set<String>>();

        log.debug("Building dependencies");
        for (val beanName : beanFactory.getBeanDefinitionNames()) {
            log.debug("Determining decorator type for bean: {}", beanName);
            val beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() == null) {
                log.debug("{} doesn't have class name", beanName);
                break;
            }

            val beanClassSimpleName = getSimpleName(beanDefinition.getBeanClassName());
            log.debug("{} simple class name is: {}", beanName, beanClassSimpleName);
            for (val decoratorType : DecoratorType.values()) {
                log.debug("Comparing {} with {} decorator type", beanClassSimpleName, decoratorType);
                if (beanClassSimpleName.startsWith(decoratorType.getPrefix())) {
                    log.debug("Adding {} to dependencies with type {}", beanName, decoratorType);
                    dependencies.computeIfAbsent(decoratorType, d -> new HashSet<>()).add(beanName);
                    break;
                }
            }
        }

        return dependencies;
    }
}