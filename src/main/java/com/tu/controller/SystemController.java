package com.tu.controller;

import com.tu.pojo.Admin;
import com.tu.pojo.LoginForm;
import com.tu.pojo.Student;
import com.tu.pojo.Teacher;
import com.tu.service.AdminService;
import com.tu.service.StudentService;
import com.tu.service.TeacherService;
import com.tu.util.CreateVerifiCodeImage;
import com.tu.util.JwtHelper;
import com.tu.util.Result;
import com.tu.util.ResultCodeEnum;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;


    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("文件二进制数据") @RequestPart("multipartFile") MultipartFile multipartFile
    ){

        //使用UUID随机生成文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //生成新的文件名字
        String filename = uuid.concat(multipartFile.getOriginalFilename());
        //生成文件的保存路径(实际生产环境这里会使用真正的文件存储服务器)
        String portraitPath ="D:/Java项目实战/myzhxy/target/classes/public/upload/".concat(filename);
        //保存文件
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headerImg = "public/upload/" +filename;
        return Result.ok(headerImg);
    }


    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader String token){
        boolean expiration = JwtHelper.isExpiration(token);
        //判断token过期
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出用户类型和用户ID
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new LinkedHashMap<>();

        switch (userType){
            case 1:
                Admin admin = adminService.getById(userId);
                map.put("userType",userType);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getById(userId);
                map.put("userType",userType);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId);
                map.put("userType",userType);
                map.put("user",teacher);
                break;
        }

        return Result.ok(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        //session中的验证码
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        //用户登录输入的验证码
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode) || sessionVerifiCode == null ){
            //验证码失效
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if(!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)){
            //验证码错误
            return Result.fail().message("验证码错误，请刷新后重试");
        }
        //从session移除验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验
        //map集合存放响应数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                //管理员类型
                try {
                    Admin admin = adminService.login(loginForm);
                    if(admin != null){
                        //用户的类型的用户Id转为密文，以token的名称向客户端反应
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                    }else{
                        throw new RuntimeException("没有这个用户");
                    }

                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                //学生类型
                try {
                    Student student = studentService.login(loginForm);
                    if(student != null){
                        //用户的类型的用户Id转为密文，以token的名称向客户端反应
                        map.put("token", JwtHelper.createToken(student.getId().longValue(),2));
                    }else{
                        throw new RuntimeException("没有这个用户");
                    }
                    return  Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                //教师类型
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if(teacher != null){
                        //用户的类型的用户Id转为密文，以token的名称向客户端反应
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(),3));
                    }else{
                        throw new RuntimeException("没有这个用户");
                    }
                    return  Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }

        return  Result.fail().message("没有这个用户");
    }

    //获取验证码图片
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request,HttpServletResponse response){

        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();

        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());

        //将验证码文本放入session域中，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);

        //把验证码响应诶浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
