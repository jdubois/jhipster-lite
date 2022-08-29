import { Category } from './Category';

export class Modules {
  constructor(public readonly categories: Category[]) {}

  modulesCount(): number {
    return this.categories.map(category => category.modules.length).reduce((previous, current) => previous + current, 0);
  }
}
