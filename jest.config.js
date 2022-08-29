module.exports = {
  moduleFileExtensions: ['js', 'ts', 'json', 'vue'],
  moduleNameMapper: {
    '^@/(.*)': '<rootDir>/src/main/webapp/app/$1',
  },
  transform: {
    '^.+\\.ts$': 'ts-jest',
    '^.+\\.vue$': 'vue-jest',
    '.+\\.(css|styl|less|sass|scss|png|jpg|svg|ttf|woff|woff2)$': 'jest-transform-stub',
  },
  collectCoverage: true,
  collectCoverageFrom: [
    'src/main/webapp/**/*.{js,ts,vue}',
    '!src/main/webapp/**/*.component.ts',
    '!src/main/webapp/app/main.ts',
    '!src/main/webapp/app/router/index.ts',
    '!**/*.d.ts',
  ],
  coverageReporters: ['html', 'json-summary', 'text', 'text-summary', 'lcov', 'clover'],
  coverageDirectory: '<rootDir>/target/test-results/',
  coverageThreshold: {
    global: {
      statements: 100,
      branches: 100,
      functions: 100,
      lines: 100,
    },
  },
  coveragePathIgnorePatterns: ['<rootDir>/src/main/webapp/app/common/primary/applicationlistener/WindowApplicationListener'],
  modulePathIgnorePatterns: ['<rootDir>/src/main/resources/', '<rootDir>/target/classes/generator/'],
  testResultsProcessor: 'jest-sonar-reporter',
};
