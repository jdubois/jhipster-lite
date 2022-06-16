import { Module } from './Module';

type CategoryName = string;

export interface Category {
  name: CategoryName;
  modules: Module[];
}
