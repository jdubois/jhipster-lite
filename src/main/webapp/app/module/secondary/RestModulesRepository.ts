import { AxiosHttp } from '@/http/AxiosHttp';
import { AxiosResponse } from 'axios';
import { Modules } from '../domain/Modules';
import { Category } from '../domain/Category';
import { Module } from '../domain/Module';
import { ModulesRepository } from '../domain/ModulesRepository';
import { ModuleProperty, ModulePropertyType } from '../domain/ModuleProperty';
import { ModuleToApply } from '../domain/ModuleToApply';
import { ModuleSlug } from '../domain/ModuleSlug';
import { ProjectFolder } from '../domain/ProjectFolder';

export interface RestModules {
  categories: RestCategory[];
}

export interface RestCategory {
  name: string;
  modules: RestModule[];
}

export interface RestModule {
  slug: string;
  description: string;
  properties?: RestModuleProperties;
}

export interface RestModuleProperties {
  definitions: RestModuleProperty[];
}

export interface RestModuleProperty {
  type: ModulePropertyType;
  mandatory: boolean;
  key: string;
  description?: string;
  example?: string;
}

export interface RestModuleToApply {
  projectFolder: string;
  properties: {};
}

interface RestModuleHistory {
  serviceId: string;
}

export class RestModulesRepository implements ModulesRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  list(): Promise<Modules> {
    return this.axiosInstance.get<RestModules>('/api/modules').then(mapToModules);
  }

  async apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void> {
    await this.axiosInstance.post<void, RestModuleToApply>(`/api/modules/${module}/apply-patch`, toRestModuleToApply(moduleToApply));
  }

  appliedModules(folder: ProjectFolder): Promise<ModuleSlug[]> {
    return this.axiosInstance.get<RestModuleHistory[]>(`/api/project-histories?folder=${folder}`).then(mapToAppliedModules);
  }
}

const mapToModules = (response: AxiosResponse<RestModules>): Modules => ({
  categories: response.data.categories.map(toCategory),
});

const toCategory = (restCategory: RestCategory): Category => ({
  name: restCategory.name,
  modules: restCategory.modules.map(toModule),
});

const toModule = (restModule: RestModule): Module => ({
  slug: restModule.slug,
  description: restModule.description,
  properties: toProperties(restModule.properties),
});

const toProperties = (restProperties: RestModuleProperties | undefined): ModuleProperty[] => {
  if (!restProperties) {
    return [];
  }

  return restProperties.definitions.map(toProperty);
};

const toProperty = (restProperty: RestModuleProperty): ModuleProperty => ({
  type: restProperty.type,
  mandatory: restProperty.mandatory,
  key: restProperty.key,
  description: restProperty.description,
  example: restProperty.example,
});

const toRestModuleToApply = (moduleToApply: ModuleToApply): RestModuleToApply => ({
  projectFolder: moduleToApply.projectFolder,
  properties: Object.fromEntries(moduleToApply.properties),
});

const mapToAppliedModules = (response: AxiosResponse<RestModuleHistory[]>): ModuleSlug[] => response.data.map(module => module.serviceId);
