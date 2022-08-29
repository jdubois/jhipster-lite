import { ModulePropertyDefinition } from './ModulePropertyDefinition';
import { ModuleSlug } from './ModuleSlug';

type ModuleDescription = string;
type ModuleTag = string;

export interface Module {
  slug: ModuleSlug;
  description: ModuleDescription;
  properties: ModulePropertyDefinition[];
  tags: ModuleTag[];
}
