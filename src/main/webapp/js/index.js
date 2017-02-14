/**
 * Created by wave on 17-2-9.
 */

var window_width = $(document.body).width();
var window_height = $(document.body).height();
var font_size_half = 20;
var body = d3.select("body");
var text = $("#text_area");
var camera_area = $("#camera_area");
var colorstate = 0;
var colorset = d3.scale.category10();
var user = "";
var state = 0;
//0 -- 睡眠
//1 -- 身份验证中
//2 -- 身法验证完毕

body.style("background-color",colorset(colorstate));
text.css("margin-top",window_height/2-font_size_half);

//定义状态转移事件
body.on("click",function(){
    if (state == 0){
        state = 1;
        face_rec();
    }
});
$.ajaxSetup({
    error: function (e) {
        console.log(e);
        alert("后台错误");
        return false;
    }
});

//进行人脸识别
function face_rec() {
    text_change("请正对我进行拍摄");
    text.animate({"margin-top":window_height/2-font_size_half-camera_area.height()/2},function(){
        var camera = $("#camera");
        camera_area.show();
        camera.photobooth();
        camera.data( "photobooth" ).resize(camera.width(),camera.height());
        camera.on("image",function(event, dataUrl){
            upload_face(dataUrl);
            camera_area.hide();
            text_change("正在进行身份识别...");
            text.animate({"margin-top":window_height/2-font_size_half});
        });
        $(".photobooth ul").hide();
        $("#take_picture").on("click",function () {
            $(".trigger").click();
        });
    });
}

//文字改变
function text_change(text_target) {
    text.text(text_target);
    colorstate = (colorstate+1)%10;
    body.transition()
        .duration(1500)
        .style("background-color",colorset(colorstate));
}

//将图片上传进行人脸识别
function upload_face(dataUrl) {
    var file = dataURLtoBlob(dataUrl);
    var fd = new FormData();
    fd.append('photo',file);
    $.ajax({
        type:'POST',
        url:'api/vision/face_rec',
        dataType:'json',
        data:fd ,
        processData:false,
        contentType:false,
        complete: function(data){
            console.log(data);
            if (state == 1){
                state = 2;
                if ((data.statusCode() == 200)&&(data.responseText != ""))
                    user = data.responseText;
                else
                    user = "陌生人";
                text_change("你好，"+user+"！");
            }
        }
    });
}
