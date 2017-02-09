package act.robot.vision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by wave on 17-2-8.
 */

@Controller
@RequestMapping("/api/vision")
public class ControllerFace {

    private OwlFacepp owlFacepp = new OwlFacepp();

    @RequestMapping(value = "/face_add", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String add_a_face() throws IOException{
        //owlFacepp.face_add("王玥", owlFacepp.detect("/home/wave/Pictures/webwxgetmsgimg.jpg"));
        System.out.println(owlFacepp.search("/home/wave/Pictures/webwxgetmsgimg.jpg"));
        return "";
    }
}
