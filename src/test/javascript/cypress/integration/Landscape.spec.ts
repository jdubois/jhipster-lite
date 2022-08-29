import { interceptForever } from '../support/Interceptor';
import { dataSelector } from '../support/selector';

describe('Landscape', () => {
  beforeEach(() => cy.intercept({ path: '/api/project-folders' }, { body: '/tmp/jhlite/1234' }));

  it('Should display landscape as default page', () => {
    cy.intercept({ path: '/api/modules-landscape' }, { fixture: 'landscape.json' });
    cy.visit('/');

    cy.url().should('include', '/landscape');
  });

  it('Should apply modules using WebServices', () => {
    const result = interceptForever({ path: '/api/modules-landscape' }, { fixture: 'landscape.json' });

    cy.intercept({
      path: '/api/apply-patches',
      method: 'POST',
    }).as('modules-application');

    cy.visit('/landscape');

    cy.get(dataSelector('landscape-loader'))
      .should('be.visible')
      .then(() => {
        result.send();
        cy.get(dataSelector('landscape-loader')).should('not.exist');

        cy.get(dataSelector('init-module')).click();
        cy.get(dataSelector('parameter-packageName-field')).type('value');
        cy.get(dataSelector('modules-application-button')).click();

        cy.wait('@modules-application').should(xhr => {
          const body = xhr.request.body;

          expect(body).to.deep.equal({
            modules: ['init'],
            properties: {
              projectFolder: '/tmp/jhlite/1234',
              commit: true,
              parameters: {
                packageName: 'value',
              },
            },
          });
        });
      });
  });
});
