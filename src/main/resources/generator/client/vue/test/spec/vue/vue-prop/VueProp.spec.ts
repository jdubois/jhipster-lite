import { describe, expect } from 'vitest';
import { shallowMount } from '@vue/test-utils';

import ArrayComponentVue from './ArrayComponentVue.vue';
import ObjectComponentVue from './ObjectComponentVue.vue';
describe('VueProp', () => {
  describe('Array', () => {
    it('Should type', () => {
      const mounted = shallowMount(ArrayComponentVue, {
        props: {
          strings: ['first', 'second'],
        },
      });

      expect(mounted.vm.strings).toEqual(['first', 'second']);
    });
  });

  describe('Object', () => {
    it('Should type', () => {
      const mounted = shallowMount(ObjectComponentVue, {
        props: {
          customObject: {
            someString: 'Some string',
            someNumber: 42,
          },
        },
      });

      const customObject = mounted.vm.customObject;

      expect(customObject.someString).toBe('Some string');
      expect(customObject.someNumber).toBe(42);
    });
  });
});
