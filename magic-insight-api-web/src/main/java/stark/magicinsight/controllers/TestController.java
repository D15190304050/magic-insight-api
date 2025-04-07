package stark.magicinsight.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stark.magicinsight.service.kafka.ConsumerService;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController
{
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/hi")
    public String test(){
        consumerService.handleMessage();
        return "hi";
    }
}
