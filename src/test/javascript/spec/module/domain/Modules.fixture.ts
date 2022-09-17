import { Modules } from '@/module/domain/Modules';
import { ModuleToApply } from '@/module/domain/ModuleToApply';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import sinon, { SinonStub } from 'sinon';
import { Project } from '@/module/domain/Project';
import { ModulePropertyValue, ProjectHistory } from '@/module/domain/ProjectHistory';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';

export interface ModulesRepositoryStub extends ModulesRepository {
  list: SinonStub;
  landscape: SinonStub;
  apply: SinonStub;
  applyAll: SinonStub;
  history: SinonStub;
  format: SinonStub;
  download: SinonStub;
}

export const stubModulesRepository = (): ModulesRepositoryStub =>
  ({
    list: sinon.stub(),
    landscape: sinon.stub(),
    apply: sinon.stub(),
    applyAll: sinon.stub(),
    history: sinon.stub(),
    format: sinon.stub(),
    download: sinon.stub(),
  } as ModulesRepositoryStub);

export const applicationBaseNamePropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'STRING',
  mandatory: true,
  key: 'baseName',
  description: 'Application base name',
  example: 'jhipster',
  order: -300,
});

export const optionalBooleanPropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'BOOLEAN',
  mandatory: false,
  key: 'optionalBoolean',
  order: -200,
});

export const defaultModules = (): Modules =>
  new Modules([
    {
      name: 'Spring',
      modules: [
        {
          slug: moduleSlug('spring-cucumber'),
          description: 'Add cucumber to the application',
          properties: [
            applicationBaseNamePropertyDefinition(),
            optionalBooleanPropertyDefinition(),
            {
              type: 'INTEGER',
              mandatory: false,
              key: 'optionalInteger',
              order: 100,
            },
          ],
          tags: ['server'],
        },
        {
          slug: moduleSlug('banner'),
          description: 'Add a banner to the application',
          properties: [],
          tags: [],
        },
      ],
    },
  ]);

export const defaultModuleToApply = (): ModuleToApply => ({
  projectFolder: '/tmp/dummy',
  commit: true,
  parameters: defaultPropertiesToApply(),
});

export const defaultModulesToApply = (): ModulesToApply => ({
  modules: [moduleSlug('init')],
  projectFolder: '/tmp/dummy',
  commit: true,
  parameters: defaultPropertiesToApply(),
});

const defaultPropertiesToApply = () => {
  return new Map<string, ModuleParameterType>().set('baseName', 'testproject').set('optionalBoolean', true).set('optionalInteger', 42);
};

export const defaultProjectHistory = (): ProjectHistory => ({
  modules: [moduleSlug('spring-cucumber')],
  properties: appliedModuleProperties(),
});

export const projectHistoryWithInit = (): ProjectHistory => ({
  modules: [moduleSlug('init'), moduleSlug('prettier')],
  properties: appliedModuleProperties(),
});

const appliedModuleProperties = (): ModulePropertyValue[] => {
  return [{ key: 'baseName', value: 'settedbase' }];
};

export const defaultProject = (): Project => ({
  filename: 'jhipster.zip',
  content: Uint8Array.from([]).buffer,
});

export const moduleSlug = (slug: string): ModuleSlug => new ModuleSlug(slug);
