import { RestModule } from './RestModule';

export interface RestCategory {
  name: string;
  modules: RestModule[];
}
