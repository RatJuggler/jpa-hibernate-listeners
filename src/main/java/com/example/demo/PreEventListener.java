package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import org.springframework.stereotype.Component;

@Component
public class PreEventListener implements PreInsertEventListener, PreUpdateEventListener {

	private static final Logger log = LoggerFactory.getLogger(PreEventListener.class);

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    log.info("About to insert an entity: {}", event.getEntity());
    return false;
  }

  @Override
  public boolean onPreUpdate(PreUpdateEvent event) {
    log.info("About to update an entity: {}", event.getEntity());
    return false;
  }
}
