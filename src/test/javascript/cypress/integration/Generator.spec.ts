import { composeSelector, dataSelector } from '../support/selector';

const generatorSelector = (name: string) => dataSelector(composeSelector('generator', name));
const headerSelector = (name: string) => dataSelector(composeSelector('header', name));
const projectGeneratorSelector = (name: string) => dataSelector(composeSelector('project-generator', name));
const springBootGeneratorSelector = (name: string) => dataSelector(composeSelector('spring-boot-generator', name));
const angularGeneratorSelector = (name: string) => dataSelector(composeSelector('angular-generator', name));
const reactGeneratorSelector = (name: string) => dataSelector(composeSelector('react-generator', name));
const vueGeneratorSelector = (name: string) => dataSelector(composeSelector('vue-generator', name));
const svelteGeneratorSelector = (name: string) => dataSelector(composeSelector('svelte-generator', name));

describe('Generator', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should redirect to generator by default', () => {
    cy.url().should('include', '/generator');
  });

  it('should display generator page', () => {
    cy.get(headerSelector('title')).contains('JHipster lite');

    cy.get(projectGeneratorSelector('add-initialization-button')).contains('Init');
    cy.get(projectGeneratorSelector('add-maven-java-button')).contains('Maven');
    cy.get(projectGeneratorSelector('add-jacoco-check-minimal-coverage-button')).contains('JaCoCo');
    cy.get(projectGeneratorSelector('add-sonar-java-backend-button')).contains('Sonar Backend');
    cy.get(projectGeneratorSelector('add-sonar-java-backend-and-frontend-button')).contains('Sonar Backend+Frontend');
    cy.get(projectGeneratorSelector('add-java-base-button')).contains('Java Base');
    cy.get(projectGeneratorSelector('add-frontend-maven-plugin-button')).contains('Frontend Maven Plugin');
    cy.get(projectGeneratorSelector('add-download-button')).should('not.exist');
  });

  it('should display spring boot', () => {
    cy.get(generatorSelector('option-springboot')).check();

    cy.get(springBootGeneratorSelector('add-spring-boot-button')).contains('Spring Boot');
    cy.get(springBootGeneratorSelector('add-spring-boot-mvc-with-tomcat-button')).contains('Spring MVC Tomcat');
    cy.get(springBootGeneratorSelector('add-spring-boot-mvc-with-undertow-button')).contains('Spring MVC Undertow');
    cy.get(springBootGeneratorSelector('add-spring-boot-webflux-netty-button')).contains('Spring Webflux Netty');
    cy.get(springBootGeneratorSelector('add-spring-boot-actuator-button')).contains('Spring Boot Actuator');
    cy.get(springBootGeneratorSelector('add-spring-doc-button')).contains('Spring Doc');

    cy.get(springBootGeneratorSelector('add-aop-logging-button')).contains('AOP Logging');
    cy.get(springBootGeneratorSelector('add-logstash-button')).contains('Logstash');

    cy.get(springBootGeneratorSelector('add-spring-boot-jwt-button')).contains('Security JWT');
    cy.get(springBootGeneratorSelector('add-spring-boot-jwt-with-basic-authentication-button')).contains('Security JWT Basic Auth');
    cy.get(springBootGeneratorSelector('add-springdoc-openapi-with-security-jwt-button')).contains('SpringDoc OpenApi with Security JWT');
    cy.get(springBootGeneratorSelector('add-spring-boot-oauth2-button')).contains('Security OAuth2');
    cy.get(springBootGeneratorSelector('add-spring-boot-oauth2-account-button')).contains('OAuth2 Account Context');

    cy.get(springBootGeneratorSelector('add-postgresql-button')).contains('PostgreSQL');
    cy.get(springBootGeneratorSelector('add-mysql-button')).contains('MySQL');
    cy.get(springBootGeneratorSelector('add-mariadb-button')).contains('MariaDB');
    cy.get(springBootGeneratorSelector('add-mongodb-button')).contains('MongoDB');
    cy.get(springBootGeneratorSelector('add-flyway-button')).contains('Flyway');
    cy.get(springBootGeneratorSelector('add-flyway-with-users-and-authority-changelogs-button')).contains('Flyway with users');
    cy.get(springBootGeneratorSelector('add-liquibase-button')).contains('Liquibase');
    cy.get(springBootGeneratorSelector('add-liquibase-with-users-and-authority-changelogs-button')).contains('Liquibase with users');
    cy.get(springBootGeneratorSelector('add-mongock-button')).contains('Mongock');

    cy.get(springBootGeneratorSelector('add-eureka-client-button')).contains('Eureka client');
    cy.get(springBootGeneratorSelector('add-consul-button')).contains('Consul');
    cy.get(springBootGeneratorSelector('add-spring-cloud-button')).contains('SpringCloud Config client');

    cy.get(springBootGeneratorSelector('add-dev-tools-button')).contains('Dev tools');
    cy.get(springBootGeneratorSelector('add-dockerfile-button')).contains('Docker file');
    cy.get(springBootGeneratorSelector('add-jib-button')).contains('Docker Jib');
    cy.get(springBootGeneratorSelector('add-spring-boot-async-button')).contains('Async config');

    cy.get(springBootGeneratorSelector('add-spring-boot-pulsar-button')).contains('Pulsar');
    cy.get(springBootGeneratorSelector('add-spring-boot-kafka-button')).contains('Kafka');
    cy.get(springBootGeneratorSelector('add-spring-boot-kafka-dummy-producer-consumer-button')).contains(
      'Dummy Producer and Consumer for Kafka'
    );
    cy.get(springBootGeneratorSelector('add-spring-boot-kafka-akhq-button')).contains('AKHQ for Kafka');
    cy.get(springBootGeneratorSelector('add-spring-boot-cucumber-button')).contains('Cucumber');

    cy.get(springBootGeneratorSelector('add-spring-boot-dummy-feature-button')).contains('Dummy feature');
  });

  it('should display angular', () => {
    cy.get(generatorSelector('option-angular')).check();
    cy.get(angularGeneratorSelector('add-angular-button')).contains('Angular');
    cy.get(angularGeneratorSelector('add-angular-with-jwt-button')).contains('Add JWT');
    cy.get(angularGeneratorSelector('add-angular-oauth2-button')).contains('Add OAuth2');
    cy.get(angularGeneratorSelector('add-angular-health-button')).contains('Add Health');
  });

  it('should display react', () => {
    cy.get(generatorSelector('option-react')).check();
    cy.get(reactGeneratorSelector('add-react-button')).contains('React');
  });

  it('should display vue', () => {
    cy.get(generatorSelector('option-vue')).check();
    cy.get(vueGeneratorSelector('add-vue-button')).contains('Vue');
  });

  it('should display svelte', () => {
    cy.get(generatorSelector('option-svelte')).check();
    cy.get(svelteGeneratorSelector('add-svelte-button')).contains('Svelte');
  });

  it('should display Codespaces', () => {
    cy.get(generatorSelector('setup-tool-codespaces')).check();
    cy.get(projectGeneratorSelector('add-codespaces-setup-button')).contains('Codespaces');
  });

  it('should display Gitpod', () => {
    cy.get(generatorSelector('setup-tool-gitpod')).check();
    cy.get(projectGeneratorSelector('add-gitpod-setup-button')).contains('Gitpod');
  });

  it('should display download button when project path is filled', () => {
    cy.get('#path').type('/tmp/jhlite');
    cy.get(projectGeneratorSelector('add-download-button')).contains('Download');
  });
});
