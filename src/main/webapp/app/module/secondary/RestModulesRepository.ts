import { AxiosHttp } from '@/http/AxiosHttp';
import { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Modules } from '../domain/Modules';
import { Category } from '../domain/Category';
import { Module } from '../domain/Module';
import { ModulesRepository } from '../domain/ModulesRepository';
import { ModuleProperty, ModulePropertyType } from '../domain/ModuleProperty';
import { ModuleToApply } from '../domain/ModuleToApply';
import { ModuleSlug } from '../domain/ModuleSlug';
import { ProjectFolder } from '../domain/ProjectFolder';
import { Project } from '../domain/Project';
import { ModulePropertyValue, ProjectHistory } from '../domain/ProjectHistory';
import { ModulePropertyValueType } from '../domain/ModuleProperties';

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
  tags?: string[];
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
  commit: boolean;
  properties: {};
}

export interface RestProjectHistory {
  modules?: RestAppliedModule[];
  properties?: {};
}

export interface RestAppliedModule {
  slug: string;
}

export class RestModulesRepository implements ModulesRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  list(): Promise<Modules> {
    return this.axiosInstance.get<RestModules>('/api/modules').then(mapToModules);
  }

  async apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void> {
    await this.axiosInstance.post<void, RestModuleToApply>(`/api/modules/${module}/apply-patch`, toRestModuleToApply(moduleToApply));
  }

  history(folder: ProjectFolder): Promise<ProjectHistory> {
    return this.axiosInstance.get<RestProjectHistory>(`/api/projects?path=${folder}`).then(mapToModuleHistory);
  }

  download(folder: string): Promise<Project> {
    const config: AxiosRequestConfig = {
      responseType: 'blob',
      headers: {
        Accept: 'application/octet-stream',
      },
    };

    return this.axiosInstance.get<ArrayBuffer>(`/api/projects?path=${folder}`, config).then(mapToProject);
  }
}

const mapToModules = (response: AxiosResponse<RestModules>): Modules => new Modules(response.data.categories.map(toCategory));

const toCategory = (restCategory: RestCategory): Category => ({
  name: restCategory.name,
  modules: restCategory.modules.map(toModule),
});

const toModule = (restModule: RestModule): Module => ({
  slug: restModule.slug,
  description: restModule.description,
  properties: toProperties(restModule.properties),
  tags: toTags(restModule.tags),
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

const toTags = (tags: string[] | undefined): string[] => {
  if (tags === undefined) {
    return [];
  }

  return tags;
};

const toRestModuleToApply = (moduleToApply: ModuleToApply): RestModuleToApply => ({
  projectFolder: moduleToApply.projectFolder,
  commit: moduleToApply.commit,
  properties: Object.fromEntries(moduleToApply.properties),
});

const mapToModuleHistory = (response: AxiosResponse<RestProjectHistory>): ProjectHistory => {
  const data = response.data;
  return {
    modules: mapAppliedModules(data.modules),
    properties: mapAppliedProperties(data.properties),
  };
};

const mapAppliedModules = (modules: RestAppliedModule[] | undefined): string[] => {
  if (modules === undefined) {
    return [];
  }

  return modules.map(module => module.slug);
};

function mapAppliedProperties(properties: {} | undefined): ModulePropertyValue[] {
  if (properties === undefined) {
    return [];
  }

  return Object.entries(properties).map(entry => {
    return {
      key: entry[0],
      value: entry[1] as ModulePropertyValueType,
    };
  });
}

const mapToProject = (response: AxiosResponse<ArrayBuffer>): Project => {
  return {
    filename: response.headers['x-suggested-filename'],
    content: response.data,
  };
};
