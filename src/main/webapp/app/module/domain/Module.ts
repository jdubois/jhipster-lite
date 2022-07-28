import { ModuleProperty } from './ModuleProperty';
import { ModuleSlug } from './ModuleSlug';

type ModuleDescription = string;
type ModuleTag = string;

export interface Module {
  slug: ModuleSlug;
  description: ModuleDescription;
  properties: ModuleProperty[];
  tags: ModuleTag[];
}
