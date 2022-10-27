export class Statistics {
  constructor(private readonly appliedModules: number) {
    if (appliedModules < 0) {
      throw new Error('Statistics cannot be negative');
    }
  }

  get(): number {
    return this.appliedModules;
  }
}
