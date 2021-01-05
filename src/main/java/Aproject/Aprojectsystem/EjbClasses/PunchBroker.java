package Aproject.Aprojectsystem.EjbClasses;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PunchBroker {

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

        return "Hi "+name+" !";

    }

}
