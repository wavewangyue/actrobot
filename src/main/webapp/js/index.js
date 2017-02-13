/**
 * Created by wave on 17-2-9.
 */

showCamera();

function showCamera() {
    var camera = $("#camera");
    var window_width = $(document.body).width();
    var window_height = $(document.body).height();

    camera.photobooth();
    camera.data( "photobooth" ).resize(window_width/2,window_height/2);
}