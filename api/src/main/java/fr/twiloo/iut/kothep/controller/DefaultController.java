package fr.twiloo.iut.kothep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
public final class DefaultController {

    @GetMapping("/default")
    public EntityResponse<?> getDefault() {
        return null;
    }
}
