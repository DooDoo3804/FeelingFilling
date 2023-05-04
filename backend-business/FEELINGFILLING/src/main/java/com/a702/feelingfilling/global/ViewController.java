package com.a702.feelingfilling.global;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = {"test API"})
@Controller
@RequestMapping("/api/test")
public class ViewController {
  @GetMapping("/loginForm")
  public String login() {
    return "loginForm";
  }
}

