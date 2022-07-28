import { shallowMount } from '@vue/test-utils';
import { HeaderVue } from '@/common/primary/header';

describe('Header', () => {
  it('should exist', () => {
    const wrapper = shallowMount(HeaderVue);

    expect(wrapper.exists()).toBe(true);
  });
});
