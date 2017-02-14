package act.robot.vision;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
        //System.out.println(owlFacepp.search("/home/wave/Pictures/webwxgetmsgimg.jpg"));
        return "";
    }

    @RequestMapping(value = "/face_rec", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String rec_a_face(HttpServletRequest request) throws IOException, ServletException{
        request.getPart("photo");
        /*
        InputStream image_input_stream = request.getInputStream();
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File image = new File("/home/robot/photos/"+dateString+".png");
        OutputStream image_output_stream = new FileOutputStream(image);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = image_input_stream.read(buffer, 0, 8192)) != -1) {
            image_output_stream.write(buffer, 0, bytesRead);
        }
        image_input_stream.close();
        image_output_stream.close();
        */
        return "";
    }
}
