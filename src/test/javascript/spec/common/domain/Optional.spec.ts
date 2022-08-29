import { Optional } from '@/common/domain/Optional';

describe('Optional', () => {
  describe('Empty check', () => {
    it('Should be empty for empty optional', () => {
      expect(Optional.empty().isEmpty()).toBe(true);
    });

    it('Should not be empty for valuated optional', () => {
      expect(Optional.of('beer').isEmpty()).toBe(false);
    });
  });

  describe('Map', () => {
    it('Should map empty optional to empty', () => {
      expect(
        Optional.empty()
          .map(dummy => dummy)
          .isEmpty()
      ).toBe(true);
    });

    it('Should map valuated optional', () => {
      expect(
        Optional.of('value')
          .map(value => 'beer ' + value)
          .orElse('dummy')
      ).toBe('beer value');
    });
  });

  describe('Or else', () => {
    it('Should get alternative from empty optional', () => {
      expect(Optional.empty().orElse('beer')).toBe('beer');
    });
  });

  describe('Of undefinable', () => {
    it('Should get empty optional from undefined value', () => {
      expect(Optional.ofUndefinable(undefined).isEmpty()).toBe(true);
    });

    it('Should get valuated optional from actual value', () => {
      expect(Optional.ofUndefinable('toto').isEmpty()).toBe(false);
    });
  });

  describe('Flat map', () => {
    it('Should flat map empty optional to empty', () => {
      expect(Optional.empty().flatMap(Optional.empty).isEmpty()).toBe(true);
    });

    it('Should flat map valuated optional to empty when mapping to empty', () => {
      expect(Optional.of('beer').flatMap(Optional.empty).isEmpty()).toBe(true);
    });

    it('Should flat map valuated optional to new value', () => {
      expect(
        Optional.of('beer')
          .flatMap(value => Optional.of('my ' + value))
          .orElse('dummy')
      ).toBe('my beer');
    });
  });

  describe('If present', () => {
    it('Should not consume value on empty optional', () => {
      let value = 42;

      Optional.empty().ifPresent(() => value++);

      expect(value).toBe(42);
    });

    it('Should consume value on valuated optional', () => {
      let value = 42;

      Optional.of(12).ifPresent(v => (value = value + v));

      expect(value).toBe(42 + 12);
    });
  });
});
