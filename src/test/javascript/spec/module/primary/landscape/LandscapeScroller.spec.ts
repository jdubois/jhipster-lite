import { describe, expect, it, vi } from 'vitest';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';

interface HTMLElementStub extends HTMLElement {
  scroll: vi.fn;
}
const stubHtmlElement = (): HTMLElementStub =>
  ({
    scroll: vi.fn(),
  }) as HTMLElementStub;

describe('LandscapeScroller', () => {
  it('should scroll', () => {
    const element = stubHtmlElement();
    const landscapeScroller = new LandscapeScroller();

    landscapeScroller.scroll(element, 10, 10);

    expect(element.scroll).toHaveBeenCalledTimes(1);
    expect(element.scroll).toBeCalledWith(10, 10);
  });
});
