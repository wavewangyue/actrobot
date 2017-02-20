package act.robot.vision;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wave on 17-2-8.
 */

@Controller
@RequestMapping("/api/vision")
public class ControllerFace {

    private OwlFacepp owlFacepp = new OwlFacepp();

    @RequestMapping(value = "/face_add", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String add_a_face(HttpServletRequest request) throws IOException{
        String image_base64 = request.getParameter("base64");
        String user_name = request.getParameter("name");
        String filepath = save_a_face(image_base64);
        //添加人脸
        if (owlFacepp.face_add(filepath, user_name))
            return "success";
        else
            return "failed";
    }

    @RequestMapping(value = "/face_rec", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody
    public String rec_a_face(HttpServletRequest request) throws IOException{
        String image_base64 = request.getParameter("base64");
        String filepath = save_a_face(image_base64);
        //人脸搜索
        return owlFacepp.search(filepath);
    }

    //保存照片
    private String save_a_face(String image_base64) throws IOException{
        //Base64解码
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(image_base64);
        for(int i=0;i<b.length;++i)
        {
            if(b[i]<0)
            {//调整异常数据
                b[i]+=256;
            }
        }
        //生成图片
        String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filepath = "/home/robot/photos/"+dateString+".png";
        //String filepath = dateString+".png";
        OutputStream out = new FileOutputStream(filepath);
        out.write(b);
        out.flush();
        out.close();
        return filepath;
    }
}
