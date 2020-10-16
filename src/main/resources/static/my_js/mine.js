/**
 * 改变按钮的状态
 * @param bool == false ? disable
 */
function change(bool) {
    const upload = $("#upload");
    if (bool) {
        upload.removeAttr("disabled");
    } else {
        upload.attr('disabled', "true")
    }
}


/**
 *
 * @param isMine
 * @param content
 */
function draw(isMine, content) {
    console.log("content=" + content);
    if (""===content.trim()) {
        console.log("空或空白字符串");
        alert("发送内容不能为空")
        return;
    }
    const textView = document.createElement("div");
    textView.style.cssText = "border: 1px black solid; width: 200px; display: block;word-break: break-all;white-space: normal;";
    if (isMine) {
        textView.style.marginLeft = "50%";
    }
    const p = document.createElement("p");
    p.innerHTML = content;
    textView.appendChild(p);
    const displayDiv = document.getElementById("display_div");
    displayDiv.appendChild(textView);
}


function send(ws, content, roomId, me) {
    const message = "{\"type\":1,\"from\":\"" + me + "\",\"content\":\"" + content + "\",\"roomId\":\"" + roomId + "\"}";
    ws.send(message);
}

/**
 * 包含 draw()和ws.send()
 * @param ws
 * @param content
 * @param roomId
 * @param me
 */
function upload(content, me) {
    change(false);
    const formData = new FormData($('#form')[0]);
    formData.append("userId",me);
    $.ajax({
        type: 'POST',
        url: '/upload',
        data: formData,
        async: true,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log(data)
            draw(true, content, JSON.parse(data)["fileName"]);
        },
        error: function () {
            alert("failed")
        },
        complete: function () {
            $("#file").val(null);
            change(true)
        }
    });
}

function getFileName(o){
    var pos=o.lastIndexOf("\\");
    return o.substring(pos+1);
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}