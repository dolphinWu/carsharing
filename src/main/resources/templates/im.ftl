<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base id="base" href="">
    <title>websocket通讯</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column" style="padding: 30px 20px;">
            <div class="jumbotron" style="max-height: 600px;height: 600px;overflow-y: auto">
            </div>
        </div>
        <div class="col-md-6 column" style="padding: 30px 20px;">
            <div style="margin: auto;height: 600px;">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form class="bs-example bs-example-form" role="form">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">yourId</span>
                                <input id="userId" type="text" class="form-control" placeholder="请填写你的id">
                            </div>
                            <!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
                            <button type="button" onclick="openSocket()" class="btn btn-primary btn-lg" style="margin-top: 10px;">连接</button>
                        </form>
                    </div>
                </div>
                <div class="row clearfix" style="margin-top: 10px;">
                    <div class="col-md-12 column">
                        <form class="bs-example bs-example-form" role="form">
                            <div class="input-group input-group-lg">
                                <span class="input-group-addon">@</span>
                                <input id="toUserId" type="text" class="form-control" placeholder="接收人id">
                            </div>
                            <div class="form-group">
                                <label for="name">发送内容：</label>
                                <textarea id="contentText" class="form-control" rows="15"></textarea>
                            </div>
                            <!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
                            <button type="button" onclick="sendMessage()" class="btn btn-primary btn-lg" style="margin-top: 10px;">发送</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script type="text/javascript">
    var socket;
    function openSocket() {
        if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            //等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
            var socketUrl = window.location.origin + "/im/" + $("#userId").val();
            socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
            console.log(socketUrl)
            socket = new WebSocket(socketUrl);
            //打开事件
            socket.onopen = function () {
                console.log("websocket已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            socket.onmessage = function (msg) {
                var message = JSON.parse(msg.data);
                if (message.code == 0) {
                    console.log(message);
                    if (message.data != null && message.data != undefined) {
                        var panel = '<div class="panel panel-default">' +
                            '<div class="panel-heading">' +
                            '<h3 class="panel-title">' +
                            '来自：' + message.data.fromUserId +
                            '</h3>' +
                            '</div>' +
                            '<div class="panel-body">' +
                            message.data.contentText +
                            '</div>' +
                            '</div>';
                        $('.jumbotron').append(panel);
                    }
                } else {
                    console.log(msg.data);
                }
            };
            //关闭事件
            socket.onclose = function () {
                console.log("websocket已关闭");
            };
            //发生了错误事件
            socket.onerror = function () {
                console.log("websocket发生了错误");
            }
        }
    }

    function sendMessage() {
        if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        } else {
            console.log("您的浏览器支持WebSocket");
            console.log('[{"toUserId":"' + $("#toUserId").val() + '","contentText":"' + $("#contentText").val() + '"}]');
            socket.send('[{"toUserId":"' + $("#toUserId").val() + '","contentText":"' + $("#contentText").val() + '"}]');
        }
    }
</script>
</body>

</html>