var me;
var message;
var webSocket;
var roomId;

function Message(id, type, from, content, roomId) {
    this.id = id;
    this.type = type;
    this.from = from;
    this.content = content;
    this.roomId = roomId;
}

/**
 * 改变按钮的状态
 * @param bool == false ? disable
 */
function change(bool) {
    const upload = $("#send");
    if (bool) {
        upload.removeAttr("disabled");
    } else {
        upload.attr('disabled', "true")
    }
}


/**
 *
 * @param messageString
 */
function draw(messageString) {
    var messageJSON = JSON.parse(messageString);
    var id = messageJSON["id"];
    var content = messageJSON["content"];
    var from = messageJSON["from"];
    const textView = document.createElement("div");
    textView.style.cssText = "border: 1px black solid; width: 200px; display: block;word-break: break-all;white-space: normal;";
    if (me === from) {
        textView.style.marginLeft = "50%";
    }
    const p = document.createElement("p");
    p.id = id;
    p.innerHTML = content;
    textView.appendChild(p);
    const displayDiv = document.getElementById("display_div");
    displayDiv.appendChild(textView);
}


function send() {
    webSocket.send(message);
}

function upload() {
    change(false);
    const formData = new FormData($('#form')[0]);
    formData.append("message", JSON.stringify(message));
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
            var dataJSONObject = JSON.parse(data);
            if ("success" === dataJSONObject["result"]) {
                document.getElementById(dataJSONObject["messageId"]).lastElementChild.innerHTML = "[上传成功]";
            }
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

function getFileName(o) {
    let position;
    if (navigator.userAgent.indexOf("Windows") > -1) {
        position = o.lastIndexOf("\\");
    } else {
        position = o.lastIndexOf("/");
    }
    return o.substring(position + 1);
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}






