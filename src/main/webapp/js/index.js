/**
 * Created by wave on 17-2-9.
 */

var state = 0;
//0 -- 睡眠
//1 -- 身份验证

var window_width = $(document.body).width();
var window_height = $(document.body).height();
var body = d3.select("body");
var colorstate = 0;
var colorset = d3.scale.category20();
body.style("background-color",colorset(colorstate));

//定义状态转移条件
body.on("click",function(){
    if (state == 0){
        state = 1;
        back_color_change();
        showCamera();
    }
});

function showCamera() {
    var camera = $("#camera");
    camera.photobooth();
    camera.data( "photobooth" ).resize(window_width/2,window_height/2);
}

function back_color_change() {
    colorstate = (colorstate+1)%20;
    body.transition()
        .duration(1500)
        .style("background-color",colorset(colorstate));

}