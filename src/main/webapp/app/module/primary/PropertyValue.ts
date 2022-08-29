import { ModuleParameterType } from '../domain/ModuleParameters';

export const notEmpty = (value: ModuleParameterType | undefined): boolean => {
  return !empty(value);
};

export const empty = (value: ModuleParameterType | undefined): boolean => {
  if (value === undefined) {
    return true;
  }

  if (typeof value === 'string') {
    return value.trim().length === 0;
  }

  return false;
};
