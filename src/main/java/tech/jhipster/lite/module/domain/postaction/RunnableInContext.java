package tech.jhipster.lite.module.domain.postaction;

@FunctionalInterface
public interface RunnableInContext {
  void run(JHipsterModuleExecutionContext context);
}
