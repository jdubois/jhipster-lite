package tech.jhipster.lite.error.infrastructure.primary;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.error.domain.GeneratorException;

@RestController
@RequestMapping("/api/exception-translator-test")
public class ExceptionTranslatorTestController {

  @PostMapping("/method-argument")
  public void methodArgument(@Valid @RequestBody TestDTO testDTO) {}

  @GetMapping("/missing-servlet-request-part")
  public void missingServletRequestPartException(@RequestPart String part) {}

  @GetMapping("/missing-servlet-request-parameter")
  public void missingServletRequestParameterException(@RequestParam String param) {}

  @GetMapping("/response-status")
  public void exceptionWithResponseStatus() {
    throw new TestResponseStatusException();
  }

  @GetMapping("/internal-server-error")
  public void internalServerError() {
    throw new RuntimeException();
  }

  @GetMapping("/bad-request-error")
  public void badRequestError() {
    throw new BadRequestAlertException("Bad request error", "beer", "http.400");
  }

  @GetMapping("/null-pointer-exception")
  public void nullPointerException() {
    throw new NullPointerException("java.lang.NullPointerException");
  }

  @GetMapping("/generator-exception-test")
  public void generatorException() {
    throw new GeneratorException("Test generator exception");
  }

  @GetMapping("/http-message-conversion-exception")
  public void httpMessageConversionException() {
    throw new HttpMessageConversionException("beer");
  }

  public static class TestDTO {

    @NotNull
    private String test;

    public String getTest() {
      return test;
    }

    public void setTest(String test) {
      this.test = test;
    }
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "test response status")
  public static class TestResponseStatusException extends RuntimeException {}
}
