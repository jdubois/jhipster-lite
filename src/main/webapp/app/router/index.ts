import { ModulesVue } from '@/module/primary/modules';
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
    name: 'Modules',
    component: ModulesVue,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
