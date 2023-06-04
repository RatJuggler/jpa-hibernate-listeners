# jpa-hibernate-listeners

Experimenting with JPA Hibernate listeners.

Useful links:

- <https://vladmihalcea.com/hibernate-event-listeners/>
- <https://vladmihalcea.com/spring-hibernate-entity-listeners/>
- <https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#events-jpa-callbacks>
- <https://docs.jboss.org/hibernate/orm/5.6/javadocs/org/hibernate/event/spi/package-summary.html>

Old listener configuration style:

- <https://techblog.bozho.net/spring-managed-hibernate-event-listeners/>

Pros

- Complete access to every aspect of the persistence lifecycle.
- Full access to the entity being loaded / persisted.

Cons

- Applies to all entites in the persistence session.
- Can leave the presistence session / entity in a dirty state when you change entity attributes in some events.
- Comprehensive understanding of the persistence lifecycle required.
- Can be difficult to configure in a SpringBoot environment.
- Hibernate specific.
