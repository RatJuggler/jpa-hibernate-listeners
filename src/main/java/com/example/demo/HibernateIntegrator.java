package com.example.demo;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateIntegrator implements Integrator {

  @Autowired
  PreEventListener preEventListener;

  @Autowired
  PostEventListener postEventListener;

  @Override
  public void integrate(
      Metadata metadata,
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry) {

    EventListenerRegistry eventListenerRegistry =
      sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

    eventListenerRegistry.appendListeners(EventType.PRE_INSERT, preEventListener);
    eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, preEventListener);

    eventListenerRegistry.appendListeners(EventType.POST_INSERT, postEventListener);
    eventListenerRegistry.appendListeners(EventType.POST_UPDATE, postEventListener);

    eventListenerRegistry.appendListeners(EventType.POST_LOAD, postEventListener);
  }

  @Override
  public void disintegrate(
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry) {
    // Nothing to disintegrate.
  }
}
