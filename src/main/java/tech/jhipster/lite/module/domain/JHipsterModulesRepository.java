package tech.jhipster.lite.module.domain;

public interface JHipsterModulesRepository {
  void apply(JHipsterModuleChanges changes);

  void applied(JHipsterModuleApplied moduleApplied);
}
