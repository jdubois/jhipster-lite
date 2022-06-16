import { ModulesListVue } from '@/module/primary/modules-list';
import { GeneratorVue } from '@/springboot/primary';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    redirect: { name: 'Generator' },
  },
  {
    path: '/generator',
    name: 'Generator',
    component: GeneratorVue,
  },
  {
    path: '/modules',
    name: 'ModulesList',
    component: ModulesListVue,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
