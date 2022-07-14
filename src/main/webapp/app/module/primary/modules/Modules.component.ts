import { ModulePropertiesVue } from '../module-properties';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { Loader } from '@/loader/primary/Loader';
import { Category } from '@/module/domain/Category';
import { Module } from '@/module/domain/Module';
import { ModuleProperty } from '@/module/domain/ModuleProperty';
import { Modules } from '@/module/domain/Modules';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, onMounted, reactive, ref } from 'vue';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';

export default defineComponent({
  name: 'ModulesVue',
  components: { ModulePropertiesVue },
  setup() {
    const alertBus = inject('alertBus') as AlertBus;
    const modules = inject('modules') as ModulesRepository;
    const projectFolders = inject('projectFolders') as ProjectFoldersRepository;

    const applicationModules = reactive({
      all: Loader.loading<Modules>(),
      displayed: Loader.loading<Modules>(),
    });

    const applicationInProgress = ref(false);
    const folderPath = ref('');
    const selectedModule = ref();
    const moduleProperties = ref(new Map<string, string | number | boolean>());
    const appliedModules = ref([] as ModuleSlug[]);

    onMounted(() => {
      modules.list().then(response => {
        applicationModules.all.loaded(response);
        applicationModules.displayed.loaded(response);
      });
      projectFolders.get().then(projectFolder => (folderPath.value = projectFolder));
    });

    const selectModule = (slug: ModuleSlug): void => {
      selectedModule.value = getModule(slug);
    };

    const disabledApplication = (slug: ModuleSlug): boolean => {
      return (
        applicationInProgress.value ||
        empty(folderPath.value) ||
        getModule(slug).properties.some(property => property.mandatory && isNotSet(property.key))
      );
    };

    const isNotSet = (propertyKey: string): boolean => {
      const value = moduleProperties.value.get(propertyKey);

      if (typeof value === 'string') {
        return empty(value);
      }

      return value === undefined;
    };

    const setStringProperty = (property: string, value: string): void => {
      moduleProperties.value.set(property, value);
    };

    const setNumberProperty = (property: string, value: string): void => {
      if (empty(value)) {
        moduleProperties.value.delete(property);
      } else {
        moduleProperties.value.set(property, +value);
      }
    };

    const setBooleanProperty = (property: string, value: string): void => {
      if (value === 'false') {
        moduleProperties.value.set(property, false);
      } else if (value === 'true') {
        moduleProperties.value.set(property, true);
      } else {
        moduleProperties.value.delete(property);
      }
    };

    const mandatoryProperties = (module: ModuleSlug): ModuleProperty[] => {
      return getModule(module).properties.filter(property => property.mandatory);
    };

    const optionalProperties = (module: ModuleSlug): ModuleProperty[] => {
      return getModule(module).properties.filter(property => !property.mandatory);
    };

    const getModule = (slug: ModuleSlug): Module => {
      return applicationModules.all
        .value()
        .categories.flatMap(category => category.modules)
        .find(module => module.slug === slug)!;
    };

    const filter = (search: string): void => {
      applicationModules.displayed.loaded({ categories: filterCategories(search.toLowerCase()) });
    };

    const filterCategories = (search: string): Category[] => {
      return applicationModules.all
        .value()
        .categories.map(category => toFilteredCategory(category, search))
        .filter(category => category.modules.length !== 0);
    };

    const toFilteredCategory = (category: Category, search: string): Category => {
      return {
        name: category.name,
        modules: category.modules.filter(module => {
          const content = module.description.toLowerCase() + ' ' + module.slug.toLowerCase();

          return search.split(' ').every(term => contains(content, term));
        }),
      };
    };

    const contains = (value: string, search: string): boolean => {
      return value.indexOf(search) !== -1;
    };

    const applyModule = (module: ModuleSlug): void => {
      applicationInProgress.value = true;

      modules
        .apply(module, {
          projectFolder: folderPath.value,
          properties: moduleProperties.value,
        })
        .then(() => {
          applicationInProgress.value = false;

          alertBus.success('Module "' + module + '" applied');
          appliedModules.value.push(module);
        })
        .catch(() => {
          applicationInProgress.value = false;

          alertBus.error('Module "' + module + '" not applied');
        });
    };

    const projectFolderUpdated = (): void => {
      modules
        .appliedModules(folderPath.value)
        .then(applied => (appliedModules.value = applied))
        .catch(() => (appliedModules.value = []));
    };

    const appliedModule = (slug: ModuleSlug): boolean => {
      return appliedModules.value.includes(slug);
    };

    return {
      content: applicationModules.displayed,
      folderPath,
      selectModule,
      selectedModule,
      setStringProperty,
      setNumberProperty,
      setBooleanProperty,
      disabledApplication,
      mandatoryProperties,
      optionalProperties,
      moduleProperties,
      filter,
      applyModule,
      applicationInProgress,
      projectFolderUpdated,
      appliedModule,
    };
  },
});

const empty = (value: string): boolean => {
  return value.trim().length === 0;
};
