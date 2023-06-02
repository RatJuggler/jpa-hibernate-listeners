package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Base64;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreLoadEvent;
import org.hibernate.event.spi.PreLoadEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import org.springframework.stereotype.Component;

@Component
public class PreEventListener implements PreInsertEventListener, PreUpdateEventListener, PreLoadEventListener {

	private static final Logger log = LoggerFactory.getLogger(PreEventListener.class);

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    log.info("OnPreInsert: About to insert from: {}", event.getEntity());
    if (event.getEntity() instanceof Customer) {
      String decodedSecret = (String) event.getState()[2];
      String encodedSecret = Base64.getEncoder().encodeToString(decodedSecret.getBytes());
      log.info("OnPreInsert: Secret encoded: {} -> {}", decodedSecret, encodedSecret);
      event.getState()[2] = encodedSecret;
      log.info("OnPreInsert: {}", Arrays.toString(event.getState()));
    }
    return false;
  }

  @Override
  public boolean onPreUpdate(PreUpdateEvent event) {
    log.info("OnPreUpdate: About to update from: {}", event.getEntity());
    if (event.getEntity() instanceof Customer) {
      String decodedSecret = (String) event.getState()[2];
      String encodedSecret = Base64.getEncoder().encodeToString(decodedSecret.getBytes());
      log.info("OnPreUpdate: Secret encoded: {} -> {}", decodedSecret, encodedSecret);
      event.getState()[2] = encodedSecret;
      log.info("OnPreUpdate: {}", Arrays.toString(event.getState()));
    }
    return false;
  }

  @Override
  public void onPreLoad(PreLoadEvent event) {
    log.info("OnPreLoad: About to load to: {}", event.getEntity());
    if (event.getEntity() instanceof Customer) {
      String encodedSecret = (String) event.getState()[2];
      byte[] decodedBytes = Base64.getDecoder().decode(encodedSecret);
      String decodedSecret = new String(decodedBytes);
      log.info("OnPreLoad: Secret decoded: {} -> {}", encodedSecret, decodedSecret);
      event.getState()[2] = decodedSecret;
      log.info("OnPreLoad: {}", Arrays.toString(event.getState()));
    }
  }
}
