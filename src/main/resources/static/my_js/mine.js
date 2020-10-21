var me = null;
var message;
var webSocket;

function Message(id, type, from, content) {
    this.id = id;
    this.type = type;
    this.from = from;
    this.content = content;
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
    var textView = document.createElement("div");
    textView.style.cssText = "width: 240px; display: block;word-break: break-all;white-space: normal;";
    var fromDiv = document.createElement("div");
    var contentDiv = document.createElement("div")
    var alias = document.createElement("b");
    var contentP = document.createElement("p");
    if (me === from) {
        textView.style.marginLeft = "66%";

        contentP.id = id;
        contentP.innerHTML = content;
        contentDiv.style.cssText = "border:1px gray solid;width:200px;float:left;border-top-left-radius:5px;border-top-right-radius:5px;border-bottom-left-radius:5px;border-bottom-right-radius:5px;";
        contentDiv.appendChild(contentP);

        alias.innerText = from;
        fromDiv.style.cssText = "width:30px;float:left;"
        fromDiv.appendChild(alias);

        textView.appendChild(contentDiv);
        textView.appendChild(fromDiv);
    } else {
        alias.innerText = from;
        fromDiv.style.cssText = "width:30px;float:left;"
        fromDiv.appendChild(alias);


        contentP.id = id;
        contentP.innerHTML = content;
        contentDiv.style.cssText = "border:1px gray solid;width:200px;float:left;border-radius:50%;border-top-left-radius:5px;border-top-right-radius:5px;border-bottom-left-radius:5px;border-bottom-right-radius:5px;"
        contentDiv.appendChild(contentP);

        textView.appendChild(fromDiv);
        textView.appendChild(contentDiv);
    }

    const displayDiv = document.getElementById("display_div");
    displayDiv.appendChild(textView);
    var l = document.createElement("div");
    var br = document.createElement("br");
    l.appendChild(br);
    displayDiv.appendChild(l);
    displayDiv.scrollTop = displayDiv.scrollHeight;
}


function send() {
    webSocket.send(JSON.stringify(message));
}

function upload() {
    //change(false);
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
            //change(true)
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






