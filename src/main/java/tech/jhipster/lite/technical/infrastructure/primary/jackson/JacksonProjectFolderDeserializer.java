package tech.jhipster.lite.technical.infrastructure.primary.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.domain.JHipsterProjectFolderFactory;
import tech.jhipster.lite.module.infrastructure.primary.InvalidProjectFolderException;

@Component
public class JacksonProjectFolderDeserializer extends JsonDeserializer<String> {

  @Autowired
  private JHipsterProjectFolderFactory jHipsterProjectFolderFactory;

  @Override
  public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    String text = jsonParser.getText();
    if (jHipsterProjectFolderFactory != null && jHipsterProjectFolderFactory.isInvalid(text)) {
      throw new InvalidProjectFolderException();
    }
    return text;
  }
}
