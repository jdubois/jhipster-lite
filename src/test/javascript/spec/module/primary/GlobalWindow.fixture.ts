import sinon from 'sinon';

export const stubWindow = (query?: string) => ({
  URL: { createObjectURL: sinon.stub(), revokeObjectURL: sinon.stub() },
  document: {
    createElement: sinon.stub(),
    body: { style: { cursor: undefined }, appendChild: sinon.stub(), removeChild: sinon.stub() },
    documentElement: { className: undefined },
  },
  matchMedia: () => ({ matches: query === '(prefers-color-scheme: dark)' }),
});
