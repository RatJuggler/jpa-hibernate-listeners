package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class HibernateCustomizer implements HibernatePropertiesCustomizer {

  @Autowired
  HibernateIntegrator interceptor;

  @Override
  public void customize(Map<String, Object> properties) {
    properties.put("hibernate.integrator_provider", interceptor);
  }
}
