import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src/main/webapp/app'),
    },
  },
  plugins: [
    vue({
      template: {
        compilerOptions: {
          isCustomElement: tag => /^x-/.test(tag),
        },
      },
    }),
  ],
  build: {
    outDir: '../../../target/classes/static',
  },
  root: 'src/main/webapp',
  server: {
    port: 9000,
    proxy: {
      '/api': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:7471',
      },
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "@/assets/global.scss"; @import "@/assets/side-form.scss";',
      },
    },
  },
});
