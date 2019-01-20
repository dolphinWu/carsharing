<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags-->
    <meta charset="utf-8">
    <!--<meta http-equiv="Content-Security-Policy"
          content="default-src * 'self' 'unsafe-inline' 'unsafe-eval' data: gap: content:">-->
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui, viewport-fit=cover">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <meta name="theme-color" content="#2196f3">
    <meta name="format-detection" content="telephone=no">
    <meta name="msapplication-tap-highlight" content="no">
    <!-- Your app title -->
    <title>My App</title>
    <link rel="stylesheet" href="https://a.amap.com/jsapi_demos/static/demo-center/css/demo-center.css"/>
    <!-- Path to Framework7 Library CSS, Material Theme -->
    <link rel="stylesheet" href="/framework7/css/framework7.min.css">
    <link rel="stylesheet" href="/framework7/css/framework7-icons.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="/framework7/css/my-app.css">
</head>
<body>
<!-- App root element -->
<div id="app">
    <!-- Status bar overlay for fullscreen mode-->
    <div class="statusbar"></div>
    <div class="panel panel-left panel-cover">
        <div class="page">
            <div class="page-content">
                <div class="block-title">Left Panel</div>
                <div class="block">
                    <p>This is a left side panel. You can close it by clicking outsite or on this link: <a href="#"
                                                                                                           class="panel-close">close
                        me</a>. You can put here anything, even another isolated view like in <a href="#"
                                                                                                 data-panel="right"
                                                                                                 class="panel-open">Right
                        Panel</a></p>
                </div>
                <div class="block-title">Main View Navigation</div>
                <div class="list links-list">
                    <ul>
                        <li>
                            <a href="/accordion/" class="panel-close">Accordion</a>
                        </li>
                        <li>
                            <a href="/action-sheet/" class="panel-close">Action Sheet</a>
                        </li>
                        <li>
                            <a href="/badge/" class="panel-close">Badge</a>
                        </li>
                        <li>
                            <a href="/buttons/" class="panel-close">Buttons</a>
                        </li>
                        <li>
                            <a href="/cards/" class="panel-close">Cards</a>
                        </li>
                        <li>
                            <a href="/checkbox/" class="panel-close">Checkbox</a>
                        </li>
                        <li>
                            <a href="/chips/" class="panel-close">Chips/Tags</a>
                        </li>
                        <li>
                            <a href="/contacts-list/" class="panel-close">Contacts List</a>
                        </li>
                        <li>
                            <a href="/data-table/" class="panel-close">Data Table</a>
                        </li>
                    </ul>
                </div>
                <div class="block">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse faucibus mauris leo, eu
                        bibendum neque congue non. Ut leo mauris, eleifend eu commodo a, egestas ac urna. Maecenas in
                        lacus faucibus, viverra ipsum pulvinar, molestie arcu. Etiam lacinia venenatis dignissim.
                        Suspendisse non nisl semper tellus malesuada suscipit eu et eros. Nulla eu enim quis quam
                        elementum vulputate. Mauris ornare consequat nunc viverra pellentesque. Aenean semper eu massa
                        sit amet aliquam. Integer et neque sed libero mollis elementum at vitae ligula. Vestibulum
                        pharetra sed libero sed porttitor. Suspendisse a faucibus lectus.</p>
                    <p>Duis ut mauris sollicitudin, venenatis nisi sed, luctus ligula. Phasellus blandit nisl ut lorem
                        semper pharetra. Nullam tortor nibh, suscipit in consequat vel, feugiat sed quam. Nam risus
                        libero, auctor vel tristique ac, malesuada ut ante. Sed molestie, est in eleifend sagittis, leo
                        tortor ullamcorper erat, at vulputate eros sapien nec libero. Mauris dapibus laoreet nibh quis
                        bibendum. Fusce dolor sem, suscipit in iaculis id, pharetra at urna. Pellentesque tempor congue
                        massa quis faucibus. Vestibulum nunc eros, convallis blandit dui sit amet, gravida adipiscing
                        libero.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Views/Tabs container -->
    <div class="views tabs ios-edges">
        <!-- Tabbar for switching views-tabs -->
        <div class="toolbar tabbar-labels toolbar-bottom-md">
            <div class="toolbar-inner">
                <a href="#view-home" id="homeBtn" class="tab-link tab-link-active">
                    <i class="icon f7-icons ios-only icon-ios-fill">home_fill</i>
                    <span class="tabbar-label">首页</span>
                </a>
                <a href="#view-order" id="orderBtn" class="tab-link">
                    <i class="icon f7-icons ios-only icon-ios-fill">list_fill</i>
                    <span class="tabbar-label">订单</span>
                </a>
                <a href="#view-message" id="messageBtn" class="tab-link">
                    <i class="icon f7-icons ios-only icon-ios-fill">chats_fill</i>
                    <span class="tabbar-label">消息</span>
                </a>
                <a href="#view-friend" id="friendBtn" class="tab-link">
                    <i class="icon f7-icons ios-only icon-ios-fill">persons_fill</i>
                    <span class="tabbar-label">好友</span>
                </a>˙
            </div>
        </div>
        <div id="view-home" class="view view-main tab tab-active">
            <div class="page" data-name="home">
                <div class="navbar">
                    <div class="navbar-inner sliding">
                        <div class="left">
                            <a href="#" class="link icon-only panel-open" data-panel="left">
                                <i class="icon f7-icons ios-only">menu</i>
                            </a>
                        </div>
                        <div class="title">首页</div>
                        <div class="right">
                            <a class="link icon-only searchbar-enable" data-searchbar=".searchbar-components">
                                <i class="icon f7-icons ios-only">search_strong</i>
                            </a>
                        </div>
                        <form data-search-container=".components-list" data-search-in="a"
                              class="searchbar searchbar-expandable searchbar-components searchbar-init">
                            <div class="searchbar-inner">
                                <div class="searchbar-input-wrap">
                                    <input type="search" placeholder="搜索"/>
                                    <i class="searchbar-icon"></i>
                                    <span class="input-clear-button"></span>
                                </div>
                                <span class="searchbar-disable-button">取消</span>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Scrollable page content-->
                <div class="page-content">
                    <div id="container" tabindex="0"></div>
                    <div id="pickerBox">
                        <input id="pickerInput" placeholder="输入关键字选取地点"/>
                        <div id="poiInfo"></div>
                    </div>
                    <div id="indexButtons">
                        <div class="patternBtn">
                            <input type="button" class="button color-green" onclick="togglePattern(1)" value="上班"/>
                        </div>
                        <div class="patternBtn">
                            <input type="button" class="button color-green button-active " onclick="togglePattern(0)" value="下班"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="view-order" class="view view-order tab"></div>
        <div id="view-message" class="view view-message tab"></div>
        <div id="view-friend" class="view view-friend tab"></div>
    </div>
</div>
<script type="text/javascript" src="/framework7/js/framework7.min.js"></script>
<script type="text/javascript" src="/framework7/js/routes.js"></script>
<script type="text/javascript" src="/framework7/js/my-app.js"></script>
<script src="https://webapi.amap.com/maps?v=1.4.12&key=4098c600aed5b4cef3a9f57b298acaff"></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/underscore-min.js"></script>
<script type="text/javascript" src="https://a.amap.com/jsapi_demos/static/demo-center/js/backbone-min.js"></script>
<script type="text/javascript" src='https://a.amap.com/jsapi_demos/static/demo-center/js/prety-json.js'></script>
<script src="https://webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
<script type="text/javascript" src="/framework7/js/my-map.js"></script>
</body>
</html>