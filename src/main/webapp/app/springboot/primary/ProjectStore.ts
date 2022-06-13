import { defineStore } from 'pinia';
import { Project } from '@/springboot/domain/Project';

const emptyProject = (): Project => ({
  folder: '',
});

export const useProjectStore = defineStore('ProjectStore', {
  state: () => {
    return {
      project: emptyProject(),
    };
  },

  getters: {
    getProject: state => state.project,
  },

  actions: {
    async setProject(project: Project) {
      this.project = project;
    },
  },
});
