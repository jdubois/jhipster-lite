import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { stubSpringBootService } from '../../../domain/SpringBootService.fixture';
import { SpringBootGeneratorVue } from '@/springboot/primary/generator/spring-boot-generator';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { AlertBus } from '@/common/domain/alert/AlertBus';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  springBootService: SpringBootService;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, springBootService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    springBootService: stubSpringBootService(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(SpringBootGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        springBootService,
      },
    },
  });
  component = wrapper.vm;
};

const expectAlertErrorToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.error.getCall(0).args;
  expect(alertMessage).toBe(message);
};

const expectAlertSuccessToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.success.getCall(0).args;
  expect(alertMessage).toBe(message);
};

describe('SpringBootGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not add SpringBoot when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBoot();

    expect(springBootService.addSpringBoot.called).toBe(false);
  });

  it('should add SpringBoot when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBoot();

    const args = springBootService.addSpringBoot.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot successfully added');
  });

  it('should handle error on adding spring boot failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBoot();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot to project failed error');
  });

  it('should not add SpringBoot MVC with Tomcat when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootMvcTomcat();

    expect(springBootService.addSpringBootMvcTomcat.called).toBe(false);
  });

  it('should add SpringBoot MVC with Tomcat when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootMvcTomcat();

    const args = springBootService.addSpringBootMvcTomcat.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot MVC with Tomcat successfully added');
  });

  it('should handle error on adding SpringBoot MVC with Tomcat failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootMvcTomcat();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot MVC with Tomcat to project failed error');
  });

  it('should not add SpringBoot MVC with Undertow when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcUndertow.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootMvcUndertow();

    expect(springBootService.addSpringBootMvcUndertow.called).toBe(false);
  });

  it('should add SpringBoot MVC with Undertow when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcUndertow.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootMvcUndertow();

    const args = springBootService.addSpringBootMvcUndertow.getCall(0).args[0];
    expectAlertSuccessToBe(alertBus, 'SpringBoot MVC with Undertow successfully added');
  });

  it('should handle error on adding SpringBoot MVC with Undertow failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcUndertow.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootMvcUndertow();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot MVC with Undertow to project failed error');
  });

  it('should not add SpringBoot dummy feature when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootDummyFeature.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootDummyFeature();

    expect(springBootService.addSpringBootDummyFeature.called).toBe(false);
  });

  it('should add SpringBoot dummy feature when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootDummyFeature.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootDummyFeature();

    const args = springBootService.addSpringBootDummyFeature.getCall(0).args[0];

    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });

    expectAlertSuccessToBe(alertBus, 'SpringBoot dummy feature successfully added');
  });

  it('should handle error on adding SpringBoot dummy feature failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootDummyFeature.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootDummyFeature();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot dummy feature to project failed error');
  });

  it('should not add SpringBoot Webflux with Netty when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootWebfluxNetty();

    expect(springBootService.addSpringBootWebfluxNetty.called).toBe(false);
  });

  it('should add SpringBoot Webflux with Netty when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootWebfluxNetty();

    const args = springBootService.addSpringBootWebfluxNetty.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Webflux with Netty successfully added');
  });

  it('should handle error on adding SpringBoot Webflux with Netty failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootWebfluxNetty();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Webflux with Netty to project failed error');
  });

  it('should not add SpringBoot Actuator when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootActuator();

    expect(springBootService.addSpringBootActuator.called).toBe(false);
  });

  it('should add SpringBoot Actuator when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootActuator();

    const args = springBootService.addSpringBootActuator.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Webflux with Netty successfully added');
  });

  it('should handle error on adding SpringBoot Actuator failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootActuator();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Actuator to project failed error');
  });

  it('should not add SpringDoc when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringDoc.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringDoc();

    expect(springBootService.addSpringDoc.called).toBe(false);
  });

  it('should add SpringDoc when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringDoc.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringDoc();

    const args = springBootService.addSpringDoc.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringDoc successfully added');
  });

  it('should handle error on adding SpringDoc failure', async () => {
    const alertBus = stubAlertBus();
    const springBootService = stubSpringBootService();
    springBootService.addSpringDoc.rejects('error');
    await wrap({ springBootService, alertBus, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringDoc();

    expectAlertErrorToBe(alertBus, 'Adding SpringDoc to project failed error');
  });

  describe('Log tools', () => {
    describe('AOP Logging', () => {
      it('should not add SpringBoot AOP Logging when project path is not filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.resolves({});
        await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

        await component.addSpringBootAopLogging();

        expect(springBootService.addSpringBootAopLogging.called).toBe(false);
      });

      it('should add SpringBoot AOP Logging when project path is filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.resolves({});
        const alertBus = stubAlertBus();
        await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

        await component.addSpringBootAopLogging();

        const args = springBootService.addSpringBootAopLogging.getCall(0).args[0];
        expect(args).toEqual({
          baseName: 'beer',
          folder: 'project/path',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: 8080,
        });
        expectAlertSuccessToBe(alertBus, 'SpringBoot AOP Logging successfully added');
      });

      it('should handle error on adding SpringBoot AOP Logging failure', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.rejects('error');
        const alertBus = stubAlertBus();
        await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

        await component.addSpringBootAopLogging();

        expectAlertErrorToBe(alertBus, 'Adding SpringBoot AOP Logging to project failed error');
      });
    });

    describe('Logstash', () => {
      it('should not add SpringBoot Logstash when project path is not filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.resolves({});
        await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

        await component.addSpringBootLogstash();

        expect(springBootService.addSpringBootLogstash.called).toBe(false);
      });

      it('should add SpringBoot Logstash when project path is filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.resolves({});
        const alertBus = stubAlertBus();
        await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

        await component.addSpringBootLogstash();

        const args = springBootService.addSpringBootLogstash.getCall(0).args[0];
        expect(args).toEqual({
          baseName: 'beer',
          folder: 'project/path',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: 8080,
        });
        expectAlertSuccessToBe(alertBus, 'SpringBoot Logstash successfully added');
      });

      it('should handle error on adding SpringBoot Logstash failure', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.rejects('error');
        const alertBus = stubAlertBus();
        await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

        await component.addSpringBootLogstash();

        expectAlertErrorToBe(alertBus, 'Adding SpringBoot Logstash to project failed error');
      });
    });
  });

  it('should not add SpringBoot Security JWT when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addJWT.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootSecurityJWT();

    expect(springBootService.addJWT.called).toBe(false);
  });

  it('should add SpringBoot Security JWT when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addJWT.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityJWT();

    const args = springBootService.addJWT.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Security JWT successfully added');
  });

  it('should handle error on adding SpringBoot Security JWT failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addJWT.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityJWT();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Security JWT to project failed error');
  });

  it('should not add SpringBoot Security JWT Basic Auth when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootSecurityJWTBasicAuth();

    expect(springBootService.addBasicAuthJWT.called).toBe(false);
  });

  it('should add SpringBoot Security JWT Basic Auth when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityJWTBasicAuth();

    const args = springBootService.addBasicAuthJWT.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Security JWT Basic Auth successfully added');
  });

  it('should handle error on adding SpringBoot Security JWT Basic Auth failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityJWTBasicAuth();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Security JWT Basic Auth to project failed error');
  });

  it('should not add SpringDoc open api with Security JWT when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringdocJWT.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringDocOpenApiSecurityJWT();

    expect(springBootService.addSpringdocJWT.called).toBe(false);
  });

  it('should add SpringDoc open api with Security JWT when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringdocJWT.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringDocOpenApiSecurityJWT();

    const args = springBootService.addSpringdocJWT.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringDoc Open Api with Security JWT successfully added');
  });

  it('should handle error on adding SpringDoc open api with Security JWT failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringdocJWT.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringDocOpenApiSecurityJWT();

    expectAlertErrorToBe(alertBus, 'Adding SpringDoc Open Api with Security JWT to project failed error');
  });

  it('should not add SpringBoot Security OAuth2 when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootSecurityOAuth2();

    expect(springBootService.addOAuth2.called).toBe(false);
  });

  it('should add SpringBoot Security OAuth2 when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityOAuth2();

    const args = springBootService.addOAuth2.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Security OAuth2 successfully added');
  });

  it('should handle error on adding SpringBoot Security OAuth2 failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityOAuth2();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Security OAuth2 to project failed error');
  });

  it('should not add SpringBoot Security OAuth2 Account Context when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2Account.resolves({});
    await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSpringBootSecurityOAuth2Account();

    expect(springBootService.addOAuth2Account.called).toBe(false);
  });

  it('should add SpringBoot Security OAuth2 Account Context when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2Account.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityOAuth2Account();

    const args = springBootService.addOAuth2Account.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
    expectAlertSuccessToBe(alertBus, 'SpringBoot Security OAuth2 Account Context successfully added');
  });

  it('should handle error on adding SpringBoot Security OAuth2 Account Context failure', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addOAuth2Account.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSpringBootSecurityOAuth2Account();

    expectAlertErrorToBe(alertBus, 'Adding SpringBoot Security OAuth2 Account Context to project failed error');
  });

  describe('Databases', () => {
    it('should not add SpringBoot Database PostgreSQL  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addPostgreSQL();

      expect(springBootService.addPostgres.called).toBe(false);
    });

    it('should add SpringBoot Database PostgreSQL when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addPostgreSQL();

      const args = springBootService.addPostgres.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database PostgreSQL successfully added');
    });

    it('should handle error on adding SpringBoot Database PostgreSQL failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addPostgreSQL();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database PostgreSQL to project failed error');
    });

    it('should not add SpringBoot Database MySQL  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addMySQL();

      expect(springBootService.addMySQL.called).toBe(false);
    });

    it('should add SpringBoot Database MySQL when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMySQL();

      const args = springBootService.addMySQL.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database MySQL successfully added');
    });

    it('should handle error on adding SpringBoot Database MySQL failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMySQL();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database MySQL to project failed error');
    });

    it('should not add SpringBoot Database MariaDB  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addMariaDB();

      expect(springBootService.addMariaDB.called).toBe(false);
    });

    it('should add SpringBoot Database MariaDB when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMariaDB();

      const args = springBootService.addMariaDB.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database MariaDB successfully added');
    });

    it('should handle error on adding SpringBoot Database MariaDB failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMariaDB();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database MariaDB to project failed error');
    });

    it('should not add SpringBoot Database MongoDB  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addMongoDB();

      expect(springBootService.addMongoDB.called).toBe(false);
    });

    it('should add SpringBoot Database MongoDB when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMongoDB();

      const args = springBootService.addMongoDB.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database MongoDB successfully added');
    });

    it('should handle error on adding SpringBoot Database MongoDB failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMongoDB();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database MongoDB to project failed error');
    });
  });

  describe('Databases migration', () => {
    it('should not add SpringBoot Database Migration Flyway when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayInit.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addFlyway();

      expect(springBootService.addSpringBootFlywayInit.called).toBe(false);
    });

    it('should add SpringBoot Database Migration Flyway when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayInit.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addFlyway();

      const args = springBootService.addSpringBootFlywayInit.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database Migration Flyway successfully added');
    });

    it('should handle error on adding SpringBoot Database Migration Flyway failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayInit.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addFlyway();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database Migration Flyway to project failed error');
    });

    it('should not add Flyway User and Authority changelogs when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayUser.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addFlywayUser();

      expect(springBootService.addSpringBootFlywayUser.called).toBe(false);
    });

    it('should add Flyway User and Authority changelogs when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayUser.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addFlywayUser();

      const args = springBootService.addSpringBootFlywayUser.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database Migration Flyway with Users and Authority changelogs successfully added');
    });

    it('should handle error on adding Flyway User and Authority changelogs failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootFlywayUser.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addFlywayUser();

      expectAlertErrorToBe(alertBus, 'Adding Flyway with Users and Authority changelogs failed error');
    });

    it('should not add SpringBoot Database Migration Liquibase when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseInit.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addLiquibase();

      expect(springBootService.addSpringBootLiquibaseInit.called).toBe(false);
    });

    it('should add SpringBoot Database Migration Liquibase when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseInit.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addLiquibase();

      const args = springBootService.addSpringBootLiquibaseInit.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database Migration Liquibase successfully added');
    });

    it('should handle error on adding SpringBoot Database Migration Liquibase failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseInit.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addLiquibase();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database Migration Liquibase to project failed error');
    });

    it('should not add Liquibase User and Authority changelogs when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseUser.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addLiquibaseUser();

      expect(springBootService.addSpringBootLiquibaseUser.called).toBe(false);
    });

    it('should add Liquibase User and Authority changelogs when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseUser.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addLiquibaseUser();

      const args = springBootService.addSpringBootLiquibaseUser.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database Migration Liquibase with Users and Authority changelogs successfully added');
    });

    it('should handle error on adding Liquibase with Users and Authority changelogs failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootLiquibaseUser.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addLiquibaseUser();

      expectAlertErrorToBe(alertBus, 'Adding Liquibase with Users and Authority changelogs failed error');
    });

    it('should not add SpringBoot Database Migration Mongock when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootMongockInit.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addMongock();

      expect(springBootService.addSpringBootMongockInit.called).toBe(false);
    });

    it('should add SpringBoot Database Migration Mongock when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootMongockInit.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMongock();

      const args = springBootService.addSpringBootMongockInit.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'SpringBoot Database MongoDB successfully added');
    });

    it('should handle error on adding SpringBoot Database Migration Mongock failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addSpringBootMongockInit.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addMongock();

      expectAlertErrorToBe(alertBus, 'Adding SpringBoot Database Migration Mongock to project failed error');
    });
  });

  describe('Brokers', () => {
    it('should not add Pulsar when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPulsar.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addPulsar();

      expect(springBootService.addPulsar.called).toBe(false);
    });

    it('should add Pulsar when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPulsar.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addPulsar();

      const args = springBootService.addPulsar.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'Pulsar successfully added');
    });

    it('should handle error on adding Pulsar failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPulsar.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addPulsar();

      expectAlertErrorToBe(alertBus, 'Adding Pulsar to project failed error');
    });
  });

  describe('Component tests', () => {
    it('should not add Cucumber when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addCucumber.resolves({});
      await wrap({ springBootService, project: createProjectToUpdate({ folder: '' }) });

      await component.addCucumber();

      expect(springBootService.addCucumber.called).toBe(false);
    });

    it('should add Cucumber when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addCucumber.resolves({});
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addCucumber();

      const args = springBootService.addCucumber.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
      expectAlertSuccessToBe(alertBus, 'Cucumber successfully added');
    });

    it('should handle error on adding Cucumber failure', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addCucumber.rejects('error');
      const alertBus = stubAlertBus();
      await wrap({ alertBus, springBootService, project: createProjectToUpdate({ folder: 'project/path' }) });

      await component.addCucumber();

      expectAlertErrorToBe(alertBus, 'Adding Cucumber to project failed error');
    });
  });
});
