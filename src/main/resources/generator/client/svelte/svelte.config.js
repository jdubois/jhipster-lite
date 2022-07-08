import adapter from '@sveltejs/adapter-static';
import preprocess from 'svelte-preprocess';

const config = {
  preprocess: preprocess(),

  kit: {
    adapter: adapter({
      pages: 'target/classes/static/',
      assets: 'target/classes/static/',
      fallback: 'index.html',
    }),
    appDir: '_app',
    files: {
      routes: 'src/main/webapp/routes',
      template: 'src/main/webapp/app.html',
      assets: 'src/main/webapp/assets',
      hooks: 'src/main/webapp/hooks',
      lib: 'src/main/webapp/lib',
      serviceWorker: 'src/main/webapp/service-worker',
    },
  },
};

export default config;
