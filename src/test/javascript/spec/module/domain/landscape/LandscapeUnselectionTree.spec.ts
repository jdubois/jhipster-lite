import { LandscapeUnselectionTree } from '@/module/domain/landscape/LandscapeUnselectionTree';
import { moduleSlug } from '../Modules.fixture';

describe('Unselection tree', () => {
  it('Should not include unknown element', () => {
    expect(new LandscapeUnselectionTree([]).includes(moduleSlug('unknown'))).toBe(false);
  });

  it('Should include known module', () => {
    expect(new LandscapeUnselectionTree([moduleSlug('init')]).includes(moduleSlug('init'))).toBe(true);
  });

  it('Should include known feature', () => {
    expect(new LandscapeUnselectionTree([moduleSlug('jpa')]).includes(moduleSlug('jpa'))).toBe(true);
  });
});
