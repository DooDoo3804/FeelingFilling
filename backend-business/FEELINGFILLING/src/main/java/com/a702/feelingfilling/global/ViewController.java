package com.a702.feelingfilling.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
  @GetMapping("/loginForm")
  public String login() {
    return "loginForm";
  }
}

