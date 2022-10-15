import { shallowMount, VueWrapper } from '@vue/test-utils';
import { HeaderVue } from '@/common/primary/header';

let wrapper: VueWrapper;

const wrap = () => {
  wrapper = shallowMount(HeaderVue, {
    global: {
      stubs: ['router-link'],
    },
  });
};

describe('Header', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });
});
