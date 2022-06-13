import { defineComponent, inject } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'SpringBootGeneratorComponent',

  components: {
    GeneratorButtonVue,
  },

  props: {
    project: {
      type: Object,
      required: true,
    },
  },

  setup(props) {
    const alertBus = inject('alertBus') as AlertBus;
    const springBootService = inject('springBootService') as SpringBootService;

    const selectorPrefix = 'spring-boot-generator';

    const addSpringBoot = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBoot(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot to project failed ${error}`));
      }
    };

    const addSpringBootMvcTomcat = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootMvcTomcat(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot MVC with Tomcat successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot MVC with Tomcat to project failed ${error}`));
      }
    };

    const addSpringBootMvcUndertow = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootMvcUndertow(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot MVC with Undertow successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot MVC with Undertow to project failed ${error}`));
      }
    };

    const addSpringBootDummyFeature = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootDummyFeature(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot dummy feature successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot dummy feature to project failed ${error}`));
      }
    };

    const addSpringBootWebfluxNetty = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootWebfluxNetty(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Webflux with Netty successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Webflux with Netty to project failed ${error}`));
      }
    };

    const addSpringBootActuator = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootActuator(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Webflux with Netty successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Actuator to project failed ${error}`));
      }
    };

    const addSpringDoc = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringDoc(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringDoc successfully added'))
          .catch(error => {
            alertBus.error(`Adding SpringDoc to project failed ${error}`);
          });
      }
    };

    const addSpringBootAopLogging = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootAopLogging(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot AOP Logging successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot AOP Logging to project failed ${error}`));
      }
    };

    const addSpringBootLogstash = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootLogstash(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Logstash successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Logstash to project failed ${error}`));
      }
    };

    const addSpringBootSecurityJWT = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addJWT(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Security JWT successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Security JWT to project failed ${error}`));
      }
    };

    const addSpringBootSecurityJWTBasicAuth = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addBasicAuthJWT(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Security JWT Basic Auth successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Security JWT Basic Auth to project failed ${error}`));
      }
    };

    const addSpringDocOpenApiSecurityJWT = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringdocJWT(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringDoc Open Api with Security JWT successfully added'))
          .catch(error => alertBus.error(`Adding SpringDoc Open Api with Security JWT to project failed ${error}`));
      }
    };

    const addSpringBootSecurityOAuth2 = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addOAuth2(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Security OAuth2 successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Security OAuth2 to project failed ${error}`));
      }
    };

    const addSpringBootSecurityOAuth2Account = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addOAuth2Account(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Security OAuth2 Account Context successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Security OAuth2 Account Context to project failed ${error}`));
      }
    };

    const addPostgreSQL = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addPostgres(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database PostgreSQL successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database PostgreSQL to project failed ${error}`));
      }
    };

    const addMySQL = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addMySQL(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database MySQL successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database MySQL to project failed ${error}`));
      }
    };

    const addMariaDB = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addMariaDB(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database MariaDB successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database MariaDB to project failed ${error}`));
      }
    };

    const addMongoDB = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addMongoDB(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database MongoDB successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database MongoDB to project failed ${error}`));
      }
    };

    const addFlyway = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootFlywayInit(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database Migration Flyway successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database Migration Flyway to project failed ${error}`));
      }
    };

    const addFlywayUser = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootFlywayUser(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database Migration Flyway with Users and Authority changelogs successfully added'))
          .catch(error => alertBus.error(`Adding Flyway with Users and Authority changelogs failed ${error}`));
      }
    };

    const addLiquibase = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootLiquibaseInit(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database Migration Liquibase successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database Migration Liquibase to project failed ${error}`));
      }
    };

    const addLiquibaseUser = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootLiquibaseUser(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database Migration Liquibase with Users and Authority changelogs successfully added'))
          .catch(error => alertBus.error(`Adding Liquibase with Users and Authority changelogs failed ${error}`));
      }
    };

    const addMongock = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addSpringBootMongockInit(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('SpringBoot Database MongoDB successfully added'))
          .catch(error => alertBus.error(`Adding SpringBoot Database Migration Mongock to project failed ${error}`));
      }
    };

    const addCucumber = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addCucumber(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Cucumber successfully added'))
          .catch(error => alertBus.error(`Adding Cucumber to project failed ${error}`));
      }
    };

    const addPulsar = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await springBootService
          .addPulsar(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Pulsar successfully added'))
          .catch(error => alertBus.error(`Adding Pulsar to project failed ${error}`));
      }
    };

    return {
      selectorPrefix,
      addSpringBoot,
      addSpringBootMvcTomcat,
      addSpringBootMvcUndertow,
      addSpringBootDummyFeature,
      addSpringBootWebfluxNetty,
      addSpringBootActuator,
      addSpringDoc,
      addSpringBootAopLogging,
      addSpringBootLogstash,
      addSpringBootSecurityJWT,
      addSpringBootSecurityJWTBasicAuth,
      addSpringDocOpenApiSecurityJWT,
      addSpringBootSecurityOAuth2,
      addSpringBootSecurityOAuth2Account,
      addPostgreSQL,
      addMySQL,
      addMariaDB,
      addMongoDB,
      addFlyway,
      addFlywayUser,
      addLiquibase,
      addLiquibaseUser,
      addMongock,
      addCucumber,
      addPulsar,
    };
  },
});
