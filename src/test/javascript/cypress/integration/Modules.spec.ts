import { dataSelector } from '../support/selector';
import { interceptForever } from '../support/Interceptor';

describe('Modules', () => {
  describe('E2E', () => {
    it('Should display modules', () => {
      cy.visit('/modules');

      cy.get(dataSelector('modules-list')).should('be.visible');
    });
  });

  describe('Component', () => {
    beforeEach(() => cy.intercept({ path: '/api/project-folders' }, { body: '/tmp/jhlite/1234' }));

    it('Should display loader while loading modules', () => {
      const result = interceptForever({ path: '/api/modules' }, { fixture: 'modules.json' });

      cy.visit('/modules');

      cy.get(dataSelector('modules-loader'))
        .should('be.visible')
        .then(() => {
          result.send();

          cy.get(dataSelector('modules-loader')).should('not.exist');
          cy.get(dataSelector('modules-list')).should('be.visible');
          cy.get(dataSelector('module-spring-test-application-button')).should('be.enabled');
          cy.get(dataSelector('module-spring-cucumber-application-button')).should('be.disabled');
          cy.get(dataSelector('folder-path-field')).invoke('val').should('equal', '/tmp/jhlite/1234');
        });
    });

    it('Should apply module without properties', () => {
      cy.intercept({ path: '/api/modules' }, { fixture: 'modules.json' });

      cy.intercept({
        path: '/api/modules/spring-test/apply-patch',
        method: 'POST',
      }).as('spring-test-creation');

      cy.visit('/modules');
      cy.get(dataSelector('folder-path-field')).clear().type('test');
      cy.get(dataSelector('module-spring-test-application-button')).click();

      cy.wait('@spring-test-creation').should(xhr => {
        const body = xhr.request.body;

        expect(body).to.deep.equal({
          projectFolder: 'test',
          properties: {},
        });
      });
    });

    it('Should apply module with properties', () => {
      cy.intercept({ path: '/api/modules' }, { fixture: 'modules.json' });

      cy.intercept({
        path: '/api/modules/spring-cucumber/apply-patch',
        method: 'POST',
      }).as('spring-cucumber-creation');

      cy.visit('/modules');

      cy.get(dataSelector('spring-cucumber-module-content')).click();
      cy.get(dataSelector('folder-path-field')).clear().type('test');
      cy.get(dataSelector('property-baseName-field')).type('jhipster');
      cy.get(dataSelector('property-optionalBoolean-field')).select('true');
      cy.get(dataSelector('property-optionalInteger-field')).type('42');
      cy.get(dataSelector('module-spring-cucumber-application-button')).click();

      cy.wait('@spring-cucumber-creation').should(xhr => {
        const body = xhr.request.body;

        expect(body).to.deep.equal({
          projectFolder: 'test',
          properties: {
            baseName: 'jhipster',
            optionalBoolean: true,
            optionalInteger: 42,
          },
        });
      });
    });
  });
});
