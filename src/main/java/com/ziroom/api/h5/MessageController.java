package com.ziroom.api.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {

    @RequestMapping("/messageList")
    public String messageList() {
        return "message/messageList";
    }

}
