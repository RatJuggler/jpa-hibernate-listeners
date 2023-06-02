package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import org.springframework.stereotype.Component;

@Component
public class PostEventListener implements PostInsertEventListener, PostUpdateEventListener {

  private static final Logger log = LoggerFactory.getLogger(PostEventListener.class);

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return true;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    log.info("OnPostInsert: Insert complete for: {}", event.getEntity());
  }

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    log.info("OnPostUpdate: Update complete for: {}", event.getEntity());
  }
}
