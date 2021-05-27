package com.dten.punchinghole.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dten.punchinghole.utils.JwtUtil;
import com.dten.punchinghole.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v1")
public class AuthorizeController {

    @Autowired
    private JwtUtil jwtHelper;

    @CrossOrigin
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public Object login(@RequestBody Map<String, Object> loginInfo) throws Exception {
        Map<String, Object> claims = new HashMap<String, Object>();
        String loginName = loginInfo.get("loginName").toString();
        String password = loginInfo.get("password").toString();
        System.out.printf("\n loginName: %s, password: %s", loginName, password);

        claims.put("loginName", loginName);
        // to do list
        if ("123456".equals(password)) {
            return new JSONResult<JSONObject>().ok(jwtHelper.createToken(claims));
        } else {
            return new JSONResult<String>().errorTokenMsg("帐号或密码错误!");
        }
    }
}
