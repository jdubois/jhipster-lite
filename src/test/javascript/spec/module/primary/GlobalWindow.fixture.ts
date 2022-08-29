import sinon from 'sinon';

export const stubWindow = () => ({
  URL: { createObjectURL: sinon.stub(), revokeObjectURL: sinon.stub() },
  document: { createElement: sinon.stub(), body: { appendChild: sinon.stub(), removeChild: sinon.stub() } },
});
