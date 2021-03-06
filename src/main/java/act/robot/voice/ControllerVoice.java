package act.robot.voice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by wave on 17-2-24.
 */
@Controller
@RequestMapping("/api/voice")
public class ControllerVoice {

    private ServiceIflytek serviceIflytek = new ServiceIflytek();

    @RequestMapping(value = "/speak", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String speak(HttpServletRequest request) throws IOException {
        String sentence = request.getParameter("s");
        //你好
        //欢迎来到A C T实验室
        serviceIflytek.speak(sentence);
        return "yes";
    }

    @RequestMapping(value = "/listen", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String listen() throws IOException {
        serviceIflytek.listen();
        return "yes";
    }
}
