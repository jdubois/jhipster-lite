import { ModuleSlug } from '../domain/ModuleSlug';

export interface RestAppliedModule {
  slug: string;
}

export const mapAppliedModules = (modules: RestAppliedModule[] | undefined): ModuleSlug[] => {
  if (modules === undefined) {
    return [];
  }

  return modules.map(module => new ModuleSlug(module.slug));
};
