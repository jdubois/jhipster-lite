import sinon from 'sinon';

export const stubWindow = () => ({
  URL: { createObjectURL: sinon.stub(), revokeObjectURL: sinon.stub() },
  document: { createElement: sinon.stub(), body: { style: { cursor: undefined }, appendChild: sinon.stub(), removeChild: sinon.stub() } },
});
