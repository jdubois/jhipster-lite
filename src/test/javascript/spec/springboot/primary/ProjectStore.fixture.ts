import sinon, { SinonStub } from 'sinon';

export interface ProjectStoreFixture {
  getProject: SinonStub;
  setProject: SinonStub;
  $subscribe: SinonStub;
}

export const stubProjectStore = (): ProjectStoreFixture =>
  ({
    getProject: sinon.stub(),
    setProject: sinon.stub(),
    $subscribe: sinon.stub(),
  } as ProjectStoreFixture);
