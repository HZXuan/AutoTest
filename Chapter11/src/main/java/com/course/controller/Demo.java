package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "v1",description = "这是我的第一个版本的demo")
@RequestMapping("v1")
public class Demo {
    //首先获取一个执行sql语句的对象,加上注解，启动就加载赋值
    @Autowired
    private SqlSessionTemplate template;
    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "这是可以获取到用户数",httpMethod = "GET")
    public int getUserCount(){
       return template.selectOne("getUserCount");
    }
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public int addUser(@RequestBody User user){
        int result = template.insert("addUser",user);
        return result;
    }//Jmeter测，Post，路径/v1/addUser，记得请求头Content-Type:application/json,bodyData里面写：
    /*
    {
        "id":4,
        "name":"zhaoliu",
        "sex":"男",
        "age":80
    }
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public int updateUser(@RequestBody User user){
        return template.update("updateUser",user);
    }
    /*
    update就是改，和前面新增的模式一样
    BodyData:
    {
         "id":4,
        "name":"hahahaha",
        "sex":"男",
        "age":80
    }
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET )
    public int delUser(@RequestParam int id){
       return template.delete("delUser",id);
    }
    /*
    路径：/v1/delUser
    关闭Http请求头
    添加Http请求的Parameters id 为 4
     */
}
