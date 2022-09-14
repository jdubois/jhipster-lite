export class LandscapeFeatureSlug {
  constructor(private readonly slug: string) {}

  public get(): string {
    return this.slug;
  }
}
