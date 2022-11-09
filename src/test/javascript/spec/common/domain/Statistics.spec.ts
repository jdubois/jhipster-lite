import { Statistics } from '@/common/domain/Statistics';
import { describe, it, expect } from 'vitest';

describe('Statistics', () => {
  it('Should not build with negative application count', () => {
    expect(() => new Statistics(-1)).toThrow('Statistics cannot be negative');
  });

  it('Should get applied modules count', () => {
    expect(new Statistics(2).get()).toBe(2);
  });
});
