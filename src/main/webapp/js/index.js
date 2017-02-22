/**
 * Created by wave on 17-2-9.
 */

var window_height = $(document.body).height();
var font_size_half = 20;
var body = d3.select("body");
var text = $("#text_area");
var colorstate = parseInt(Math.random()*20);
var colorset = d3.scale.category20().domain(d3.range(20));
var user = "";
var dataUrl_remember = "";
var state = 0;
//0 -- 未身份验证
//1 -- 已身份验证


$.ajaxSetup({
    error: function (e) {
        console.log(e);
        alert("后台错误");
        return false;
    }
});
body.style("background-color",colorset(colorstate));
text.css("margin-top",window_height/2-font_size_half);
//定义此页面的监听
body.on('click', function(){
    if (state == 0){
        state = 1;
        face_rec();
    }
});
$("#record_new_face").on('click', function(){
    if (user == "")
        face_add();
    else
        text.text("我已经认得你了，"+user+"。还有什么可以帮你的吗？");
});


//人脸识别
function face_rec() {
    show_camera();
    $("#take_picture").on("click",function(){
        $(".trigger").click();
        $("#camera_area").hide();
        text_change("正在进行身份识别...");
        text.animate({"margin-top":window_height/2-font_size_half});
        upload_face_rec();
    });
}


//显示照相机
function show_camera(){
    text_change("请正对我进行拍摄");
    var camera_area = $("#camera_area");
    text.animate({"margin-top":window_height/2-font_size_half-camera_area.height()/2},function(){
        var camera = $("#camera");
        camera_area.show();
        camera.photobooth();
        camera.data( "photobooth" ).resize(camera.width(),camera.height());
        camera.on("image",function(event, dataUrl){
            dataUrl_remember = dataUrl;
        });
        $(".photobooth ul").hide();
    });
}


//人脸识别---图片上传
function upload_face_rec() {
    var base64 = dataUrl_remember.split(',')[1];
    $.ajax({
        type:'POST',
        url:'api/vision/face_rec',
        data:{'base64':base64},
        success: function(data){
            console.log(data);
            if (state == 1){
                state = 2;
                user = data;
                if (data != "")
                    text_change("你好，"+user+"！有什么可以帮你的吗？");
                else
                    text_change("你好，陌生人！有什么可以帮你的吗？");
                var text_select_area = $('#text_select_area');
                text_select_area.fadeIn();
                text.animate({"margin-top":window_height/2-font_size_half-text_select_area.height()});
            }
        }
    });
}


//人脸添加
function face_add() {
    $('#text_select_area').fadeOut();
    text_change("请告诉我你的名字");
    text.animate({"margin-top":window_height/2-font_size_half*2});
    var photo_name_input = $('#photo_name_input');
    photo_name_input.show();
    photo_name_input.keydown(function(event){
        if(event.keyCode==13){
            photo_name_input.hide();
            text_change("正在上传人脸...");
            text.animate({"margin-top":window_height/2-font_size_half});
            upload_face_add(photo_name_input.val());
        }
    });
}


//人脸添加---图片上传
function upload_face_add(name){
    var base64 = dataUrl_remember.split(',')[1];
    $.ajax({
        type:'POST',
        url:'api/vision/face_add',
        data:{'base64':base64, 'name':name},
        success: function(data){
            console.log(data);
            user = name;
            var text_select_area = $('#text_select_area');
            text_select_area.fadeIn();
            text_change(user+"，我已经记下了你的样子，很高兴认识你！还有什么可以帮你的吗？");
            text.animate({"margin-top":window_height/2-font_size_half-text_select_area.height()});
        }
    });
}


//文字改变
function text_change(text_target) {
    text.text(text_target);
    colorstate = parseInt(Math.random()*20);
    body.transition()
        .duration(1500)
        .style("background-color",colorset(colorstate));
}


