package system;

import com.zw.rule.po.User;
import com.zw.rule.service.LoginService;
import com.zw.rule.service.SysOrganizationService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/5/3.
 */
public class SysOrganizationServiceTest {

    ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context.xml");

    /*
    *组织管理service删除（假删）测试
    * */
    @Test
    public void doTest() throws Exception {
        SysOrganizationService sysOrganizationService = (SysOrganizationService) cxt.getBean("sysOrganizationServiceImpl");
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("10");
        ids.add("11");
        ids.add("12");
        sysOrganizationService.deleteSysOrganization(ids);
    }
}