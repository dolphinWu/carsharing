package com.ziroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author wudaoping
 * @Date 19-1-25 下午2:19
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/uploadPage")
    public String uploadPage() {
        return "common/uploadPage";
    }


    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        file.transferTo(new File("/home/wudaoping/文档/" + file.getOriginalFilename()));
        return "success";
    }
}
