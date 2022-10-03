module.exports = {
  '{src/test/javascript,src/main/webapp}/**/*.{js,ts,tsx,vue}': ['eslint --fix'],
  'src/main/style/**/*.{css,scss}': ['stylelint --fix'],
  'src/main/style/**/*.pug': ['pug-lint'],
  '{{src/**/,}*.{md,json,yml,html,vue,java,xml},*.{js,ts},src/{main/webapp,main/glyph,test/javascript}/**/*.{css,scss}}': [
    'prettier --write',
  ],
};
