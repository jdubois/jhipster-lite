module.exports = {
  moduleFileExtensions: ['js', 'ts', 'json', 'svelte'],
  transform: {
    '^.+\\.ts$': 'ts-jest',
    '^.+\\.svelte$': ['svelte-jester', { preprocess: true }],
    '.+\\.(css|styl|less|sass|scss|png|jpg|svg|ttf|woff|woff2)$': 'jest-transform-stub',
  },
  moduleNameMapper: {
    '^@webapp(.*)': '<rootDir>/src/main/webapp/$1',
    '^@assets(.*)$': '<rootDir>/src/main/webapp/assets/$1',
  },
  collectCoverage: true,
  collectCoverageFrom: [
    'src/main/webapp/**/*.{js,ts,svelte}',
    '!src/main/webapp/jest-setup.ts',
    '!src/main/webapp/routes/+page.svelte',
    '!**/*.d.ts',
  ],
  coverageReporters: ['html', 'json-summary', 'text-summary', 'lcov', 'clover'],
  coverageDirectory: '<rootDir>/target/test-results/',
  coverageThreshold: {
    global: {
      statements: 100,
      branches: 100,
      functions: 100,
      lines: 100,
    },
  },
  modulePathIgnorePatterns: ['<rootDir>/src/main/resources/'],
  testResultsProcessor: 'jest-sonar-reporter',
  testEnvironment: 'jsdom',
};
