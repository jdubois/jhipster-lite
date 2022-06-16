export type ModulePropertyType = 'STRING' | 'INTEGER' | 'BOOLEAN';
export type ModulePropertyKey = string;
export type ModulePropertyDescription = string;
export type ModulePropertyExample = string;

export interface ModuleProperty {
  type: ModulePropertyType;
  mandatory: boolean;
  key: ModulePropertyKey;
  description?: ModulePropertyDescription;
  example?: ModulePropertyExample;
}
