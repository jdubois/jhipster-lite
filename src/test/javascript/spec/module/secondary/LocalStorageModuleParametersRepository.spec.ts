import { LocalStorageModuleParametersRepository } from '@/module/secondary/LocalStorageModuleParametersRepository';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { describe, it, expect } from 'vitest';

const fakeStorage = (): Storage => {
  let store: { [key: string]: string } = {};
  return {
    getItem: (key: string) => store[key],
    setItem: (key: string, value: string) => (store[key] = value),
    removeItem: (key: string) => delete store[key],
    clear: () => (store = {}),
    get length() {
      return Object.keys(store).length;
    },
    key: (index: number) => Object.keys(store)[index],
  };
};

describe('LocalStorageModuleParametersRepository', () => {
  it('Should store the map in localStorage', () => {
    const storage = fakeStorage();
    const repo = new LocalStorageModuleParametersRepository(storage);
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    repo.store(new Map(data));
    expect(JSON.parse(storage.getItem('moduleParameters')!)).toEqual(data);
  });

  it('Should return empty Map when localStorage is empty', () => {
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);
    expect(repo.get()).toEqual(new Map());
  });

  it('Should return values from localStorage when it is not empty', () => {
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const storage = fakeStorage();
    storage.setItem('moduleParameters', JSON.stringify(Array.from(data)));
    const repo = new LocalStorageModuleParametersRepository(storage);
    expect(repo.get()).toEqual(new Map(data));
  });
});
