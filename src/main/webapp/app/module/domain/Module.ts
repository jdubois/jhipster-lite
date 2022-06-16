import { ModuleSlug } from './ModuleSlug';

type ModuleDescription = string;

export interface Module {
  slug: ModuleSlug;
  description: ModuleDescription;
}
