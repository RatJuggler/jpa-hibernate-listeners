package com.example.demo;

import java.util.Collections;
import java.util.Map;

import org.hibernate.jpa.boot.spi.IntegratorProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class HibernateCustomizer implements HibernatePropertiesCustomizer {

  @Autowired
  HibernateIntegrator interceptor;

  @Override
  public void customize(Map<String, Object> properties) {
    properties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(interceptor));
  }
}
