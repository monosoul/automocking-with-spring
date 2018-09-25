package com.github.monosoul.fortuneteller.spring;

import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DecoratorAutowireCandidateResolverConfigurer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Assert.state(beanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        val dlBeanFactory = (DefaultListableBeanFactory) beanFactory;

        val resolver = new DecoratorAutowireCandidateResolver(dlBeanFactory);

        dlBeanFactory.setAutowireCandidateResolver(resolver);
    }
}