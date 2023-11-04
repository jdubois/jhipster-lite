import { ManagementRepository } from '@/module/domain/ManagementRepository';
import sinon, { SinonStub } from 'sinon';

export interface ManagementRepositoryStub extends ManagementRepository {
  getInfo: SinonStub;
}

export const stubLocalManagementRepository = (): ManagementRepositoryStub => ({ getInfo: sinon.stub() }) as ManagementRepositoryStub;
