const SPACER_SIZE = 15;

export class LandscapeConnector {
  public readonly points: string;
  constructor(
    public readonly positions: LandscapeConnectorPosition[],
    public readonly startingElement: string,
    public readonly endingElement: string
  ) {
    this.points = this.buildPoints();
  }

  private buildPoints(): string {
    return this.positions.map(position => position.x + ',' + position.y).join(' ');
  }
}

export interface LandscapeConnectorPosition {
  x: number;
  y: number;
}

export interface BuildConnectorsParameters {
  dependencyElementSlug: string;
  dependantElementSlug: string;
  dependencyElement: HTMLElement;
  dependantElement: DOMRect;
  container: HTMLElement;
}

export const buildConnector = (parameters: BuildConnectorsParameters): LandscapeConnector => {
  const yPad = parameters.container.scrollTop;
  const xPad = parameters.container.scrollLeft;

  const dependencyElementPosition = parameters.dependencyElement.getBoundingClientRect();

  const dependencyElementX = dependencyElementPosition.x + dependencyElementPosition.width + xPad;
  const dependencyElementY = Math.round(dependencyElementPosition.y + dependencyElementPosition.height / 2) + yPad;

  const dependantElementX = parameters.dependantElement.x + xPad;
  const dependantElementY = Math.round(parameters.dependantElement.y + parameters.dependantElement.height / 2) + yPad;

  const dependencyStartingPoint: LandscapeConnectorPosition = {
    x: dependencyElementX,
    y: dependencyElementY,
  };

  const dependencySpacer: LandscapeConnectorPosition = {
    x: dependencyElementX + SPACER_SIZE,
    y: dependencyElementY,
  };

  const dependantSpacer: LandscapeConnectorPosition = {
    x: dependantElementX - SPACER_SIZE,
    y: dependantElementY,
  };

  const dependantStartingPoint: LandscapeConnectorPosition = {
    x: dependantElementX,
    y: dependantElementY,
  };

  return new LandscapeConnector(
    [dependencyStartingPoint, dependencySpacer, dependantSpacer, dependantStartingPoint],
    parameters.dependantElementSlug,
    parameters.dependencyElementSlug
  );
};
