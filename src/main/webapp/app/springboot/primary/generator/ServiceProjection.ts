import { Service } from '@/common/domain/Service';

export type ServiceProjection =
  | 'aop-logging'
  | 'angular'
  | 'angular-with-jwt'
  | 'angular-oauth2'
  | 'angular-health'
  | 'consul'
  | 'dev-tools'
  | 'dockerfile'
  | 'download'
  | 'eureka-client'
  | 'initialization'
  | 'flyway'
  | 'flyway-with-users-and-authority-changelogs'
  | 'frontend-maven-plugin'
  | 'jacoco-check-minimal-coverage'
  | 'java-base'
  | 'jib'
  | 'liquibase'
  | 'liquibase-with-users-and-authority-changelogs'
  | 'logstash'
  | 'maven-java'
  | 'codespaces-setup'
  | 'gitpod-setup'
  | 'mariadb'
  | 'mysql'
  | 'mongodb'
  | 'mongock'
  | 'postgresql'
  | 'sonar-java-backend'
  | 'sonar-java-backend-and-frontend'
  | 'spring-boot'
  | 'spring-boot-actuator'
  | 'spring-doc'
  | 'spring-boot-jwt'
  | 'spring-boot-jwt-with-basic-authentication'
  | 'springdoc-openapi-with-security-jwt'
  | 'spring-boot-oauth2'
  | 'spring-boot-oauth2-account'
  | 'spring-boot-mvc-with-tomcat'
  | 'spring-boot-mvc-with-undertow'
  | 'spring-boot-webflux-netty'
  | 'spring-boot-dummy-feature'
  | 'spring-boot-cucumber'
  | 'spring-boot-pulsar'
  | 'spring-boot-kafka'
  | 'spring-boot-kafka-dummy-producer-consumer'
  | 'spring-boot-kafka-akhq'
  | 'spring-boot-async'
  | 'spring-cloud'
  | 'react'
  | 'react-styled'
  | 'vue'
  | 'unknown';

const SERVICES_PROJECTION: Record<Service, ServiceProjection> = {
  [Service.AOP_LOGGING]: 'aop-logging',
  [Service.ANGULAR]: 'angular',
  [Service.ANGULAR_WITH_JWT]: 'angular-with-jwt',
  [Service.ANGULAR_OAUTH2]: 'angular-oauth2',
  [Service.ANGULAR_HEALTH]: 'angular-health',
  [Service.CONSUL]: 'consul',
  [Service.DEV_TOOLS]: 'dev-tools',
  [Service.DOCKERFILE]: 'dockerfile',
  [Service.DOWNLOAD]: 'download',
  [Service.EUREKA_CLIENT]: 'eureka-client',
  [Service.FLYWAY]: 'flyway',
  [Service.FLYWAY_WITH_USERS_AND_AUTHORITY_CHANGELOGS]: 'flyway-with-users-and-authority-changelogs',
  [Service.FRONTEND_MAVEN_PLUGIN]: 'frontend-maven-plugin',
  [Service.INITIALIZATION]: 'initialization',
  [Service.JACOCO_CHECK_MINIMAL_COVERAGE]: 'jacoco-check-minimal-coverage',
  [Service.JAVA_BASE]: 'java-base',
  [Service.JIB]: 'jib',
  [Service.LIQUIBASE]: 'liquibase',
  [Service.LIQUIBASE_WITH_USERS_AND_AUTHORITY_CHANGELOGS]: 'liquibase-with-users-and-authority-changelogs',
  [Service.LOGSTASH]: 'logstash',
  [Service.MAVEN_JAVA]: 'maven-java',
  [Service.CODESPACES_SETUP]: 'codespaces-setup',
  [Service.GITPOD_SETUP]: 'gitpod-setup',
  [Service.MARIADB]: 'mariadb',
  [Service.MYSQL]: 'mysql',
  [Service.MONGODB]: 'mongodb',
  [Service.MONGOCK]: 'mongock',
  [Service.POSTGRESQL]: 'postgresql',
  [Service.SONAR_JAVA_BACKEND]: 'sonar-java-backend',
  [Service.SONAR_JAVA_BACKEND_AND_FRONTEND]: 'sonar-java-backend-and-frontend',
  [Service.SPRINGBOOT]: 'spring-boot',
  [Service.SPRINGBOOT_ACTUATOR]: 'spring-boot-actuator',
  [Service.SPRING_DOC]: 'spring-doc',
  [Service.SPRINGBOOT_JWT]: 'spring-boot-jwt',
  [Service.SPRINGBOOT_JWT_WITH_BASIC_AUTHENTICATION]: 'spring-boot-jwt-with-basic-authentication',
  [Service.SPRINGDOC_OPENAPI_WITH_SECURIITY_JWT]: 'springdoc-openapi-with-security-jwt',
  [Service.SPRINGBOOT_OAUTH2]: 'spring-boot-oauth2',
  [Service.SPRINGBOOT_OAUTH2_ACCOUNT]: 'spring-boot-oauth2-account',
  [Service.SPRINGBOOT_MVC_WITH_TOMCAT]: 'spring-boot-mvc-with-tomcat',
  [Service.SPRINGBOOT_MVC_WITH_UNDERTOW]: 'spring-boot-mvc-with-undertow',
  [Service.SPRINGBOOT_DUMMY_FEATURE]: 'spring-boot-dummy-feature',
  [Service.SPRINGBOOT_WEBFLUX_NETTY]: 'spring-boot-webflux-netty',
  [Service.SPRINGBOOT_CUCUMBER]: 'spring-boot-cucumber',
  [Service.SPRINGBOOT_PULSAR]: 'spring-boot-pulsar',
  [Service.SPRINGBOOT_KAFKA]: 'spring-boot-kafka',
  [Service.SPRINGBOOT_KAFKA_DUMMY_PRODUCER_CONSUMER]: 'spring-boot-kafka-dummy-producer-consumer',
  [Service.SPRINGBOOT_KAFKA_AKHQ]: 'spring-boot-kafka-akhq',
  [Service.SPRINGBOOT_ASYNC]: 'spring-boot-async',
  [Service.SPRING_CLOUD]: 'spring-cloud',
  [Service.REACT]: 'react',
  [Service.REACT_STYLED]: 'react-styled',
  [Service.VUE]: 'vue',
  [Service.UNKNOWN]: 'unknown',
};

export const toServiceProjection = (service: Service): ServiceProjection => SERVICES_PROJECTION[service];

const SERVICES: Record<ServiceProjection, Service> = {
  'aop-logging': Service.AOP_LOGGING,
  angular: Service.ANGULAR,
  'angular-with-jwt': Service.ANGULAR_WITH_JWT,
  'angular-oauth2': Service.ANGULAR_OAUTH2,
  'angular-health': Service.ANGULAR_HEALTH,
  consul: Service.CONSUL,
  'dev-tools': Service.DEV_TOOLS,
  dockerfile: Service.DOCKERFILE,
  download: Service.DOWNLOAD,
  'eureka-client': Service.EUREKA_CLIENT,
  flyway: Service.FLYWAY,
  'flyway-with-users-and-authority-changelogs': Service.FLYWAY_WITH_USERS_AND_AUTHORITY_CHANGELOGS,
  'frontend-maven-plugin': Service.FRONTEND_MAVEN_PLUGIN,
  initialization: Service.INITIALIZATION,
  'jacoco-check-minimal-coverage': Service.JACOCO_CHECK_MINIMAL_COVERAGE,
  'java-base': Service.JAVA_BASE,
  jib: Service.JIB,
  liquibase: Service.LIQUIBASE,
  'liquibase-with-users-and-authority-changelogs': Service.LIQUIBASE_WITH_USERS_AND_AUTHORITY_CHANGELOGS,
  logstash: Service.LOGSTASH,
  'maven-java': Service.MAVEN_JAVA,
  'codespaces-setup': Service.CODESPACES_SETUP,
  'gitpod-setup': Service.GITPOD_SETUP,
  mariadb: Service.MARIADB,
  mysql: Service.MYSQL,
  mongodb: Service.MONGODB,
  mongock: Service.MONGOCK,
  postgresql: Service.POSTGRESQL,
  'sonar-java-backend': Service.SONAR_JAVA_BACKEND,
  'sonar-java-backend-and-frontend': Service.SONAR_JAVA_BACKEND_AND_FRONTEND,
  'spring-boot': Service.SPRINGBOOT,
  'spring-boot-actuator': Service.SPRINGBOOT_ACTUATOR,
  'spring-doc': Service.SPRING_DOC,
  'spring-boot-jwt': Service.SPRINGBOOT_JWT,
  'spring-boot-jwt-with-basic-authentication': Service.SPRINGBOOT_JWT_WITH_BASIC_AUTHENTICATION,
  'springdoc-openapi-with-security-jwt': Service.SPRINGDOC_OPENAPI_WITH_SECURIITY_JWT,
  'spring-boot-oauth2': Service.SPRINGBOOT_OAUTH2,
  'spring-boot-oauth2-account': Service.SPRINGBOOT_OAUTH2_ACCOUNT,
  'spring-boot-mvc-with-tomcat': Service.SPRINGBOOT_MVC_WITH_TOMCAT,
  'spring-boot-mvc-with-undertow': Service.SPRINGBOOT_MVC_WITH_UNDERTOW,
  'spring-boot-dummy-feature': Service.SPRINGBOOT_DUMMY_FEATURE,
  'spring-boot-webflux-netty': Service.SPRINGBOOT_WEBFLUX_NETTY,
  'spring-boot-cucumber': Service.SPRINGBOOT_CUCUMBER,
  'spring-boot-pulsar': Service.SPRINGBOOT_PULSAR,
  'spring-boot-kafka': Service.SPRINGBOOT_KAFKA,
  'spring-boot-kafka-dummy-producer-consumer': Service.SPRINGBOOT_KAFKA_DUMMY_PRODUCER_CONSUMER,
  'spring-boot-kafka-akhq': Service.SPRINGBOOT_KAFKA_AKHQ,
  'spring-boot-async': Service.SPRINGBOOT_ASYNC,
  'spring-cloud': Service.SPRING_CLOUD,
  react: Service.REACT,
  'react-styled': Service.REACT_STYLED,
  vue: Service.VUE,
  unknown: Service.UNKNOWN,
};

export const fromServiceProjection = (serviceProjection: ServiceProjection): Service => SERVICES[serviceProjection];
