package system;

import com.zw.rule.po.User;
import com.zw.rule.service.LoginService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/5/3.
 */
public class LoginServiceTest {

    ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context.xml");

    /*
    * 正常登录测试
    *
    * */
    @Test
    public void dologinTest() throws Exception {
        LoginService loginService = (LoginService) cxt.getBean("loginServiceImpl");
        User u =loginService.doLogin("test","123");
        assertEquals("测试员",u.getTrueName());
    }






}