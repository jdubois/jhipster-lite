import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';

const SPACER_SIZE = 9;

export class LandscapeConnector {
  public readonly points: string;
  constructor(
    public readonly positions: LandscapeConnectorPosition[],
    public readonly startingElement: LandscapeElementId,
    public readonly endingElement: LandscapeElementId
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

interface BuildConnectorsParameters {
  dependencyElementSlug: LandscapeElementId;
  dependantElementSlug: LandscapeElementId;
  dependencyElement: HTMLElement;
  dependantElement: HTMLElement;
}

export const buildConnector = (parameters: BuildConnectorsParameters): LandscapeConnector => {
  const { dependencyElement, dependantElement } = parameters;

  const dependencyElementX = dependencyElement.offsetLeft + dependencyElement.offsetWidth;
  const dependencyElementY = Math.round(dependencyElement.offsetTop + dependencyElement.offsetHeight / 2);

  const dependantElementX = dependantElement.offsetLeft;
  const dependantElementY = Math.round(dependantElement.offsetTop + dependantElement.offsetHeight / 2);

  const dependencyStartingPoint: LandscapeConnectorPosition = {
    x: dependencyElementX,
    y: dependencyElementY,
  };

  const dependencySpacer: LandscapeConnectorPosition = {
    x: dependencyElementX + SPACER_SIZE,
    y: dependencyElementY,
  };

  const dependantSpacer: LandscapeConnectorPosition = {
    x: dependencyElementX + SPACER_SIZE,
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
