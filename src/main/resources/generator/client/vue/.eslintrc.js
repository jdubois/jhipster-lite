module.exports = {
  env: {
    node: true,
  },
  extends: ['eslint:recommended', 'plugin:vue/vue3-recommended', '@vue/eslint-config-typescript', '@vue/prettier'],
  parserOptions: {
    parser: '@typescript-eslint/parser',
  },
  rules: {
    quotes: ['error', 'single', { avoidEscape: true }],
    '@typescript-eslint/no-unused-vars': ['error'],
    'prettier/prettier': ['error'],
  },
};
