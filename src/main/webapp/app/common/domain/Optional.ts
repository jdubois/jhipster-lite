export abstract class Optional<Value> {
  public static empty<Value>(): Optional<Value> {
    return new EmptyOptional();
  }

  public static of<Value>(value: Value): Optional<Value> {
    return new ValuatedOptional(value);
  }

  static ofUndefinable<Value>(value: Value | undefined): Optional<Value> {
    if (value === undefined) {
      return Optional.empty();
    }

    return Optional.of(value);
  }

  abstract map<Output>(mapper: (value: Value) => Output): Optional<Output>;
  abstract flatMap<Output>(mapper: (feature: Value) => Optional<Output>): Optional<Output>;
  abstract orElse(value: Value): Value;
  abstract isEmpty(): boolean;
  abstract ifPresent(consumer: (feature: Value) => void): void;
}

class EmptyOptional<Value> extends Optional<Value> {
  map<Output>(): Optional<Output> {
    return Optional.empty();
  }

  flatMap<Output>(): Optional<Output> {
    return Optional.empty();
  }

  orElse(value: Value): Value {
    return value;
  }

  isEmpty(): boolean {
    return true;
  }

  ifPresent(): void {
    // Nothing to do
  }
}

class ValuatedOptional<Value> extends Optional<Value> {
  constructor(private readonly value: Value) {
    super();
  }

  map<Output>(mapper: (value: Value) => Output): Optional<Output> {
    return Optional.of(mapper(this.value));
  }

  flatMap<Output>(mapper: (feature: Value) => Optional<Output>): Optional<Output> {
    return mapper(this.value);
  }

  orElse(): Value {
    return this.value;
  }

  isEmpty(): boolean {
    return false;
  }

  ifPresent(consumer: (feature: Value) => void): void {
    consumer(this.value);
  }
}
