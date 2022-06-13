package tech.jhipster.lite.generator.module.domain.postaction;

@FunctionalInterface
public interface RunnableInContext {
  void run(JHipsterModuleExecutionContext context);
}
