function change() {
    var pic = document.getElementById("preview");
    var file = document.getElementById("missingPhoto");
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
    //gif在ie上無法顯示
    if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
        alert("文件必須為圖片！"); return;
    }
    //ie用
    if (document.all) {

        file.select();
        var reallocalpath = document.selection.createRange().text;
        var ie6 = /msie 6/i.test(navigator.userAgent);
        //ie6設置的img的src為本地路徑可以直接顯示
        if (ie6) pic.src = reallocalpath;
        else {
            //非ie6的須透過filter來處理
            pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
            
            pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
        }
    }else{
        html5Reader(file);
    }
}

function html5Reader(file){
    var file = file.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        var pic = document.getElementById("preview");
        pic.src=this.result;
    }
}