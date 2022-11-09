import { shallowMount } from '@vue/test-utils';
import { IconVue } from '@/common/primary/icon';
import { describe, it, expect } from 'vitest';

describe('Icon', () => {
  it('should exist', () => {
    const wrapper = shallowMount(IconVue, {
      props: {
        name: 'icon-name',
      },
    });

    expect(wrapper.exists()).toBe(true);
  });
});
