package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JpaHibernateListenersApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(JpaHibernateListenersApplicationTests.class);

  @Autowired
  private EntityManager entityManager;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired
  private CustomerLinkRepository customerLinkRepository;

  private void populate() {
    // save a few customers
    createCustomer("Test1", "User1", "secret1");
    createCustomer("Test2", "User2", "secret2");
    createCustomer("Test3", "User3", "secret3");

    log.info("Flush Customers...");
    customerRepository.flush();
    // exerciseCustomers();
    log.info("Clear Customers from cache...");
    entityManager.clear();

    // save a few customer links
    createCustomerLink("customer1", 1L);
    createCustomerLink("customer2", 2L);
    createCustomerLink("customer3", 3L);

    log.info("Flush CustomerLinks...");
    customerLinkRepository.flush();
    // exerciseCustomerLinks();
    log.info("Clear CustomerLinks from cache...");
    entityManager.clear();
  }

  private void createCustomer(String firstName, String lastName, String secret) {
    Customer customer = new Customer(firstName, lastName, secret);
    customerRepository.save(customer);
  }

  private void createCustomerLink(String account, Long customerId) {
    CustomerLink customerLink = new CustomerLink(account);
    customerLink.setCustomer(customerRepository.findById(customerId).orElseThrow());
    customerLinkRepository.save(customerLink);
  }

  @Test
	void testEverything() {
    populate();

    // scenario1();
    // entityManager.clear();

    scenario2();
    entityManager.clear();

    // scenario3();
    // entityManager.clear();
  }

  /**
   * Find the Customer via it's primary key.
   * - postLoad event triggers for Customer and decodes secret.
   * Find the CustomerLink for the Customer via it's primary key.
   * - postLoad event triggers for CustomerLink, but no events trigger for Customer.
   * PASS: Secret is left decoded.
   */
  private void scenario1() {
    log.info("");
    log.info("Scenario 1");
    log.info("==========");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("--------------------------------");
    Customer customer = customerRepository.findById(1L);
    log.info(customer.toString());
    log.info("");

    log.info("Find CustomerLink for the same Customer with findById(4L):");
    log.info("----------------------------------------------------------");
    CustomerLink customerLink = customerLinkRepository.findById(4L);
    log.info(customerLink.toString());
    log.info("");
  }

  /**
   * Find the Customer via it's primary key.
   * - postLoad event triggers for Customer and decodes secret.
   * Find the CustomerLink for the Customer via the Customer entity.
   * - preUpdate event triggers for Customer and encodes the secret.
   * - Customer is not flushed to database, postUpdate event is NOT triggered.
   * - postLoad event triggers for CustomerLink.
   * FAILS: Secret is left encoded.
   */
  private void scenario2() {
    log.info("");
    log.info("Scenario 2");
    log.info("==========");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("--------------------------------");
    Customer customer = customerRepository.findById(1L);
    log.info(customer.toString());
    log.info("");

    log.info("Find CustomerLink for the same Customer with findByCustomer(customer):");
    log.info("----------------------------------------------------------------------");
    CustomerLink customerLink = customerLinkRepository.findByCustomer(customer);
    log.info(customerLink.toString());
    log.info("");
  }

  /**
   * Find the Customer via it's primary key.
   * - postLoad event triggers for Customer and decodes secret.
   * Find the CustomerLink for the Customer via the Customer entity.
   * - preUpdate event triggers for Customer and encodes the secret.
   * - Customer is flushed to database.
   * - postUpdate event trigger for Customer and decodes the secret.
   * - postLoad event triggers for CustomerLink.
   * FAILS: Secret is left decoded at the expense of an unwanted update.
   */
  private void scenario3() {
    log.info("");
    log.info("Scenario 3");
    log.info("==========");
    log.info("");
    log.info("Find Customer with findById(1L):");
    log.info("-------------- -----------------");
    Customer customer = customerRepository.findById(1L);
    log.info(customer.toString());
    log.info("");

    log.info("Find CustomerLink for the same Customer with findByCustomerId(customer.getId()):");
    log.info("--------------------------------------------------------------------------------");
    CustomerLink customerLink = customerLinkRepository.findByCustomerId(customer.getId());
    log.info(customerLink.toString());
    log.info("");
  }

  private void exerciseCustomers() {
    // fetch all customer
    log.info("Customers found with findAll():");
    log.info("-------------------------------");
    for (Customer customer : customerRepository.findAll()) {
      log.info(customer.toString());
    }
    log.info("");

    // fetch an individual customer by ID
    log.info("Customer found with findById(1L):");
    log.info("---------------------------------");
    Customer customer = customerRepository.findById(1L);
    log.info(customer.toString());
    log.info("");

    // fetch customers by last name
    log.info("Cutomer found with findByLastName('User3'):");
    log.info("-------------------------------------------");
    customerRepository.findByLastName("User3").forEach(found -> log.info(found.toString()));
    log.info("");
  }

  private void exerciseCustomerLinks() {
    // fetch all customer
    log.info("CustomerLinks found with findAll():");
    log.info("-----------------------------------");
    for (CustomerLink customerLink : customerLinkRepository.findAll()) {
      log.info(customerLink.toString());
    }
    log.info("");

    // fetch an individual customer link by ID
    log.info("CustomerLink found with findById(4L):");
    log.info("-------------------------------------");
    CustomerLink customerLinkById = customerLinkRepository.findById(4L);
    log.info(customerLinkById.toString());
    log.info("");

    // fetch customer links by account
    log.info("CutomerLink found with findByAccount('customer3'):");
    log.info("--------------------------------------------------");
    CustomerLink customerLinkByAccount = customerLinkRepository.findByAccount("customer3");
    log.info(customerLinkByAccount.toString());
    log.info("");
  }
}
