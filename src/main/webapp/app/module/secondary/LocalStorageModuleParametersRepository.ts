import { ModuleParametersRepository } from '../domain/ModuleParametersRepository';
import { ModuleParameterType } from '../domain/ModuleParameters';

export class LocalStorageModuleParametersRepository implements ModuleParametersRepository {
  private readonly STORAGE_KEY = 'moduleParameters';
  private readonly localStorage: Storage;

  constructor(localStorage: Storage) {
    this.localStorage = localStorage;
  }
  store(map: Map<string, ModuleParameterType>): void {
    this.localStorage.setItem(this.STORAGE_KEY, JSON.stringify(Array.from(map.entries())));
  }

  get(): Map<string, ModuleParameterType> {
    const storedValue = this.localStorage.getItem(this.STORAGE_KEY);
    if (storedValue) {
      return new Map(JSON.parse(storedValue));
    }
    return new Map<string, ModuleParameterType>();
  }
}
