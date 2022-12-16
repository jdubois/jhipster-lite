import { PropType } from 'vue';

export const objectType = <T>() => Object as PropType<T>;
export const arrayType = <T>() => Array as PropType<T[]>;
