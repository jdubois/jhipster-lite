package tech.jhipster.lite.frontend.infrastructure.primary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class RedirectionResource {

  @GetMapping(value = "/{path:[^\\.]*}")
  public String redirectApi() {
    return "forward:/";
  }
}
