package act.robot.brain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by wave on 17-2-14.
 */
@Controller
@RequestMapping("/api/brain")
public class ControllerBrain {

    private int State = 0;
    //0 -- 睡眠
    //1 -- 身份验证中
    //2 -- 身法验证完毕

    @RequestMapping(value = "/get_state", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public int get_state() throws IOException {
        return State;
    }

    @RequestMapping(value = "/change_state", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public void change_state(int to) throws IOException {
        State = to;
    }
}
