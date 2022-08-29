package tech.jhipster.lite.statistic.infrastructure.secondary;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import tech.jhipster.lite.common.domain.Generated;

@ChangeUnit(id = "create-applied-modules-collection", order = "002", author = "cdamon")
@Generated(reason = "Rollback not called in a normal lifecycle and an implementation detail")
public class AppliedModulesChangeUnit {

  @Execution
  public void createCollection(MongoTemplate mongo) {
    mongo.createCollection(AppliedModuleDocument.class);
  }

  @RollbackExecution
  public void dropCollection(MongoTemplate mongo) {
    mongo.dropCollection(AppliedModuleDocument.class);
  }
}
