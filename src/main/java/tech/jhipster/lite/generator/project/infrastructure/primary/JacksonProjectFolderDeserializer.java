package tech.jhipster.lite.generator.project.infrastructure.primary;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.infrastructure.primary.InvalidProjectFolderException;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

@Component
public class JacksonProjectFolderDeserializer extends JsonDeserializer<String> {

  @Autowired
  private ProjectFolder jHipsterProjectFolderFactory;

  @Override
  public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    String text = jsonParser.getText();
    if (jHipsterProjectFolderFactory != null && jHipsterProjectFolderFactory.isInvalid(text)) {
      throw new InvalidProjectFolderException();
    }
    return text;
  }
}
