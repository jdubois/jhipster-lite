import { Landscape } from '@/module/domain/landscape/Landscape';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ComponentLandscape } from '@/module/primary/landscape/ComponentLandscape';

describe('Component Landscape', () => {
  describe('Is selectable', () => {
    it('Should not be selectable in invalid landscape', () => {
      expect(() => ComponentLandscape.from(invalidDependencyLandscape()).isSelectable('dummy')).toThrowError();
    });
  });
});

const invalidDependencyLandscape = (): Landscape => {
  return new Landscape([{ elements: [new LandscapeModule(new ModuleSlug('dummy'), 'operation', [], [new ModuleSlug('unknown')])] }]);
};
