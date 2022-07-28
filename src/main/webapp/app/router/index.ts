import { ModulesVue } from '@/module/primary/modules';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    redirect: { name: 'Modules' },
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
