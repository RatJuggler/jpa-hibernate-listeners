package com.example.demo;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

@Component
public class HibernateIntegrator implements Integrator {

  @Override
  public void integrate(
      Metadata metadata,
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry) {
    throw new UnsupportedOperationException("Unimplemented method 'integrate'");
  }

  @Override
  public void disintegrate(
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry) {
    throw new UnsupportedOperationException("Unimplemented method 'disintegrate'");
  }
}
