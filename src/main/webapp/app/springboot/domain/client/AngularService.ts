import { Project } from '@/springboot/domain/Project';

export interface AngularService {
  add(project: Project): Promise<void>;
  addWithJWT(project: Project): Promise<void>;
  addOauth2(project: Project): Promise<void>;
  addHealth(project: Project): Promise<void>;
}
