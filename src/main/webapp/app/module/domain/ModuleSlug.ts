export class ModuleSlug {
  constructor(private readonly slug: string) {}

  public get(): string {
    return this.slug;
  }

  public normalized(): string {
    return this.slug.toLowerCase();
  }
}
