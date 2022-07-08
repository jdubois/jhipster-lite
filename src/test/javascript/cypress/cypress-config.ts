import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:7471',
    specPattern: 'src/test/javascript/cypress/integration/**/*.spec.ts',
    fixturesFolder: 'src/test/javascript/cypress/fixtures',
    supportFolder: 'src/test/javascript/cypress/support',
    supportFile: false,
    video: false,
  },
});
