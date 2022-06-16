import { Modules } from '@/module/domain/Modules';
import { ModuleProperties } from '@/module/domain/ModuleProperties';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import sinon, { SinonStub } from 'sinon';

interface ModulesRepositoryStub extends ModulesRepository {
  list: SinonStub;
}

export const stubModulesRepository = (): ModulesRepositoryStub =>
  ({
    list: sinon.stub(),
  } as ModulesRepositoryStub);

export const defaultModules = (): Modules => ({
  categories: [
    {
      name: 'Spring',
      modules: [
        {
          slug: 'spring-cucumber',
          description: 'Add cucumber to the application',
        },
      ],
    },
  ],
});

export const defaultModuleProperties = (): ModuleProperties => ({
  properties: [
    {
      type: 'STRING',
      mandatory: true,
      key: 'baseName',
      description: 'Application base name',
      example: 'jhipster',
    },
    {
      type: 'BOOLEAN',
      mandatory: false,
      key: 'optionalBoolean',
    },
    {
      type: 'INTEGER',
      mandatory: false,
      key: 'optionalInteger',
    },
  ],
});
