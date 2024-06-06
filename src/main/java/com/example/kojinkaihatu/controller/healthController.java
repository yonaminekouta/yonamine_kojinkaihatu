package com.example.kojinkaihatu.controller;

import com.example.kojinkaihatu.entity.User;
import com.example.kojinkaihatu.form.*;
import com.example.kojinkaihatu.form.DataForm;
import com.example.kojinkaihatu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class healthController {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(@ModelAttribute("loginForm") LoginForm loginForm){return "index";}

    @PostMapping("/index")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        Model model){
        System.out.println(loginForm);
        //System.out.println(userService.findAllUser());
        var users=userService.findAllUser();
        for(var user : users){
            if(loginForm.getLoginId().equals(user.login_id())
                && loginForm.getPassword().equals(user.pass())){
                session.setAttribute("user",user);
                System.out.println(session.getAttribute("user"));
                return "menu";
            }
        }

        if(loginForm.getLoginId().isEmpty()
            || loginForm.getPassword().isEmpty()){
            if(loginForm.getLoginId().isEmpty()){
                model.addAttribute("IDError","IDは必須入力です");
            }
            if(loginForm.getPassword().isEmpty()){
                model.addAttribute("passError","パスワードは必須入力です");
            }
        }else{
            model.addAttribute("missError","IDかパスワードが間違っています");
        }
        return "index";
    }

    @GetMapping("/menu")
    public String menu(){return "menu";}

    @GetMapping("/newInsert")
    public String newInsert(){return "newInsert";}

    @PostMapping("/insert")
    public String insert(@ModelAttribute("insertForm") InsertForm insertForm,
                         Model model){
        System.out.println(insertForm);
        if(insertForm.getLoginId().isEmpty()
            || insertForm.getPassword().isEmpty()
            || insertForm.getName().isEmpty()
            || insertForm.getHeight()==null
            || insertForm.getWeight()==null){
            if(insertForm.getLoginId().isEmpty()){model.addAttribute("IDError","IDを入力してください");}
            if(insertForm.getPassword().isEmpty()){model.addAttribute("passError","パスワードを入力してください");}
            if(insertForm.getName().isEmpty()){model.addAttribute("nameError","名前を入力してください");}
            if(insertForm.getHeight()==null){model.addAttribute("heightError","身長を入力してください");}
            if(insertForm.getWeight()==null){model.addAttribute("weightError","体重を入力してください");}
            return "newInsert";
        }
        userService.insertNewUser(new User(0,insertForm.getLoginId(),insertForm.getPassword(),insertForm.getName(),
                                            insertForm.getHeight(),insertForm.getWeight(),insertForm.getGender()));
        return "redirect:index";
    }

    @GetMapping("/BMI")
    public String BMI(Model model){
        User user=(User)session.getAttribute("user");
        double height= (float) user.height() /100;
        var BMI=user.weight()/(height*height);
        BMI=Math.floor(BMI*100)/100;//小数点第3位切り捨て
        String judge="";
        //日本肥満学会による肥満度判定の基準
        if(BMI<18.5){
            judge="低体重";
        }else if(BMI>=18.5 && BMI<25){
            judge="普通体重";
        }else if(BMI>=25 && BMI<30){
            judge="肥満(1度)";
        }else if(BMI>=30 && BMI<35){
            judge="肥満(2度)";
        }else if(BMI>=35 && BMI<40){
            judge="肥満(3度)";
        }else if(BMI>=40){
            judge="肥満(4度)";
        }
        model.addAttribute("BMI",BMI);
        model.addAttribute("judge",judge);
        return "BMI";
    }

    @GetMapping("/avg")
    public String avg(Model model){
        var man=userService.findAllManData();
        var woman=userService.findAllWomanData();
        model.addAttribute("man",man);
        model.addAttribute("woman",woman);
        return "avg";
    }

    @GetMapping("/kcal")
    public String kcal(Model model){
        var training_list=userService.findAllTraining();
        model.addAttribute("training",training_list);
        return "kcal";
    }

    @GetMapping("/update")
    public String update(Model model){
        User user=(User)session.getAttribute("user");
        model.addAttribute("old_height",user.height());
        model.addAttribute("old_weight",user.weight());
        return "update";
    }

    @PostMapping("/update")
    public String update_result(@ModelAttribute("dataForm") DataForm dataForm,
                                Model model){
        if(dataForm.getHeight()==null
            || dataForm.getWeight()==null){
            if(dataForm.getHeight()==null) {
                model.addAttribute("heightError", "※身長を入力してください");
            }
            if(dataForm.getWeight()==null) {
                model.addAttribute("weightError", "※体重を入力してください");
            }
        }else{
            User user=(User)session.getAttribute("user");
            var id=user.id();
            userService.updateUser(user.id(),dataForm.getHeight(),dataForm.getWeight());
            session.removeAttribute("user");
            var newUser=userService.findByIdUser(id);
            session.setAttribute("user",newUser.get(0));
            model.addAttribute("success","更新しました。");
        }
        return "update";
    }

    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "logout";
    }

    @GetMapping("/taikai")
    public String taikai(){
        return "taikai";
    }

    @PostMapping("/taikai")
    public String delete(@ModelAttribute("logoutForm") LogoutForm logoutForm
                         ,Model model){
        System.out.println(logoutForm);
        User user=(User)session.getAttribute("user");
        if(logoutForm.getPassword().isEmpty()){
            model.addAttribute("passError","パスワードを入力してください");
            return "taikai";
        }
        if(!logoutForm.getPassword().equals(user.pass())){
            model.addAttribute("passError","パスワードが違います");
            return "taikai";
        }
        session.removeAttribute("user");
        userService.deleteUser(user.id());
        return "redirect:index";
    }
}
