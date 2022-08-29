import { Modules } from '@/module/domain/Modules';
import { ModuleToApply } from '@/module/domain/ModuleToApply';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import sinon, { SinonStub } from 'sinon';
import { Project } from '@/module/domain/Project';
import { ModulePropertyValue, ProjectHistory } from '@/module/domain/ProjectHistory';
import { Landscape } from '@/module/domain/landscape/Landscape';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { LandscapeFeatureSlug } from '@/module/domain/landscape/LandscapeFeatureSlug';
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

const applicationBaseNamePropertyDefinition = (): ModulePropertyDefinition => ({
  type: 'STRING',
  mandatory: true,
  key: 'baseName',
  description: 'Application base name',
  example: 'jhipster',
  order: -300,
});

const optionalBooleanPropertyDefinition = (): ModulePropertyDefinition => ({
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
  modules: [moduleSlug('init')],
  properties: appliedModuleProperties(),
});

const appliedModuleProperties = (): ModulePropertyValue[] => {
  return [{ key: 'baseName', value: 'settedbase' }];
};

export const defaultProject = (): Project => ({
  filename: 'jhipster.zip',
  content: Uint8Array.from([]).buffer,
});

export const defaultLandscape = (): Landscape =>
  new Landscape([
    {
      elements: [
        new LandscapeModule(moduleSlug('infinitest'), 'Add infinitest filters', [applicationBaseNamePropertyDefinition()], []),
        new LandscapeModule(moduleSlug('init'), 'Add some initial tools', [applicationBaseNamePropertyDefinition()], []),
      ],
    },
    {
      elements: [
        new LandscapeFeature(featureSlug('client'), [
          new LandscapeModule(moduleSlug('vue'), 'Add vue', [], moduleSlugs('init')),
          new LandscapeModule(moduleSlug('react'), 'Add react', [], moduleSlugs('init')),
          new LandscapeModule(moduleSlug('angular'), 'Add angular', [], moduleSlugs('init')),
        ]),
        javaBuildToolFeature(),
      ],
    },
    {
      elements: [
        new LandscapeModule(moduleSlug('java-base'), 'Add base java classes', [], featureSlugs('java-build-tools')),
        new LandscapeModule(moduleSlug('spring-boot'), 'Add spring boot core', [], featureSlugs('java-build-tools')),
        new LandscapeFeature(featureSlug('ci'), [
          new LandscapeModule(moduleSlug('gitlab-maven'), 'Add simple gitlab ci for maven', [], moduleSlugs('maven')),
          new LandscapeModule(moduleSlug('gitlab-gradle'), 'Add simple gitlab ci for gradle', [], moduleSlugs('gradle')),
        ]),
      ],
    },
    {
      elements: [
        new LandscapeFeature(featureSlug('jpa'), [
          new LandscapeModule(moduleSlug('postgresql'), 'Add PostGreSQL', [], moduleSlugs('spring-boot')),
        ]),
        new LandscapeFeature(featureSlug('spring-mvc'), [
          new LandscapeModule(moduleSlug('spring-boot-tomcat'), 'Add Tomcat', [], moduleSlugs('spring-boot')),
          new LandscapeModule(moduleSlug('spring-boot-undertow'), 'Add Undertow', [], moduleSlugs('spring-boot')),
        ]),
        new LandscapeModule(moduleSlug('bean-validation-test'), 'Add bean validation test tools', [], moduleSlugs('spring-boot')),
        new LandscapeModule(moduleSlug('build'), 'Add build information', [], featureSlugs('ci')),
      ],
    },
    {
      elements: [
        new LandscapeModule(
          moduleSlug('dummy-feature'),
          'Add dummy feature',
          [],
          [featureSlug('spring-mvc'), moduleSlug('bean-validation-test')]
        ),
        new LandscapeModule(moduleSlug('liquibase'), 'Add liquibase', [], featureSlugs('jpa')),
      ],
    },
  ]);

const moduleSlugs = (...slugs: string[]): ModuleSlug[] => slugs.map(moduleSlug);

const moduleSlug = (slug: string): ModuleSlug => new ModuleSlug(slug);

const featureSlugs = (...slugs: string[]): LandscapeFeatureSlug[] => slugs.map(featureSlug);

const featureSlug = (slug: string): LandscapeFeatureSlug => new LandscapeFeatureSlug(slug);

export const javaBuildToolFeature = (): LandscapeFeature => {
  return new LandscapeFeature(featureSlug('java-build-tools'), [
    new LandscapeModule(moduleSlug('maven'), 'Add maven', [optionalBooleanPropertyDefinition()], moduleSlugs('init')),
    new LandscapeModule(moduleSlug('gradle'), 'Add gradle', [], moduleSlugs('init')),
  ]);
};
