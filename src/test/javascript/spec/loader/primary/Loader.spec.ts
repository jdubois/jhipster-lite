import { Loader } from '@/loader/primary/Loader';

describe('Loader', () => {
  it('Should be loading for loading loader', () => {
    expect(Loader.loading().isLoading()).toBe(true);
  });

  it('Should not get value for not loaded loader', () => {
    expect(() => Loader.loading().value()).toThrow();
  });

  it('Should build loaded value', () => {
    const loader = Loader.loaded('pouet');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('pouet');
  });

  it('Should load value', () => {
    const loader = Loader.loading();

    loader.loaded('pouet');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('pouet');
  });
});
