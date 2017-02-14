/**
 * Created by wave on 17-2-14.
 */

function dataURLtoBlob(dataUrl) {
    //decode
    var binary = atob(dataUrl.split(',')[1]);
    // 8-bit unsigned array
    var array = [];
    for (var i = 0;i < binary.length;i++){
        array.push(binary.charCodeAt(i));
    }
    //return blob
    return new Blob([new Uint8Array(array)],{
        type:'image/png'
    });
}