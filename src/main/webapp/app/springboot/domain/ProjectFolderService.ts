import { FolderName } from '@/springboot/domain/FolderName';

export interface ProjectFolderService {
  get(): Promise<FolderName>;
}
