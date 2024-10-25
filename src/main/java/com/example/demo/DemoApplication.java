package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.openai.OpenAiChatModel;

@SpringBootApplication
public class DemoApplication {

  @Value("${NAME:World}")
  String name;

  @RestController
  class HelloworldController {

    OpenAiChatModel chatModel = null;

    @GetMapping("/")
    String hello() {
      return "Hello " + name + "!";
    }

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

      this.chatModel = OpenAiChatModel.builder()
      .apiKey(this.apiKey)
      .baseUrl("https://api.groq.com/openai/v1")
      .modelName("llama-3.1-70b-versatile")
      .temperature(0.4)
      .maxTokens(200)
      .build();


        return Map.of("generation", this.chatModel.generate(message));
    }
  }




  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
