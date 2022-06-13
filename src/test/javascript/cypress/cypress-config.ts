import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:7471',
    specPattern: 'src/test/javascript/cypress/integration/**/*.spec.ts',
    fixturesFolder: false,
    supportFile: false,
    video: false,
  },
});
