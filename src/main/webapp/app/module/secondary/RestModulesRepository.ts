import { AxiosHttp } from '@/http/AxiosHttp';
import { AxiosResponse } from 'axios';
import { Modules } from '../domain/Modules';
import { Category } from '../domain/Category';
import { Module } from '../domain/Module';
import { ModulesRepository } from '../domain/ModulesRepository';
import { ModuleProperties } from '../domain/ModuleProperties';
import { ModuleSlug } from '../domain/ModuleSlug';
import { ModuleProperty, ModulePropertyType } from '../domain/ModuleProperty';

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

export class RestModulesRepository implements ModulesRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  list(): Promise<Modules> {
    return this.axiosInstance.get<RestModules>('/api/modules').then(mapToModules);
  }

  get(slug: ModuleSlug): Promise<ModuleProperties> {
    return this.axiosInstance.get<RestModuleProperties>(`/api/modules/${slug}`).then(mapToModule);
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
});

const mapToModule = (response: AxiosResponse<RestModuleProperties>): ModuleProperties => ({
  properties: response.data.definitions.map(toProperty),
});

const toProperty = (restProperty: RestModuleProperty): ModuleProperty => ({
  type: restProperty.type,
  mandatory: restProperty.mandatory,
  key: restProperty.key,
  description: restProperty.description,
  example: restProperty.example,
});
