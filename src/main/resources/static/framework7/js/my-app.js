// Dom7
var $$ = Dom7;

// Framework7 App main instance
var app = new Framework7({
    root: '#app', // App root element
    id: 'ziroom', // App bundle ID
    name: 'carSharing', // App name
    theme: 'ios', // Automatic theme detection
    // App root data
    data: function () {
        return {
            user: {
                firstName: 'John',
                lastName: 'Doe',
            },
        };
    },
    // App root methods
    methods: {},
    // App routes
    routes: routes,
});

//全局
//方法引用
//弹出提示消息
var alertMessage = app.dialog.alert;
//弹出确认消息
var confirmMessage = app.dialog.confirm;
//地图中心点位置
var currentPosition = [116.397428, 39.90923];

// Init/Create main view
var mainView = app.views.create('.view-main');
var mainRouter = mainView.router;
var orderView = app.views.create('.view-order', {
});
var orderRouter = orderView.router;
var friendView = app.views.create('.view-friend');
var friendRouter = friendView.router;
var messageView = app.views.create('.view-message');
var messageRouter = messageView.router;

//全局方法
/**
 * 给页面添加下拉刷新操作
 * @param page
 * @param func
 */
function pageAddRefresh(page, func) {
    var $ptrContent = page.$el.find('.ptr-content');
    if (func == undefined || func == null) {
        func =  function (e2) {
            setTimeout(function () {
                page.router.refreshPage();
                app.ptr.done();
            }, 200)
        }
    }
    $ptrContent.on('ptr:refresh', func);
}

/**
 * 加载页面
 * @param url
 * @param param
 * @param viewName
 */
function loadPage(url, param, viewName) {
    if (viewName == undefined || viewName == null || viewName == '') {
        viewName = 'mainView';
    }

    var currentView;
    var currentRouter;
    switch (viewName) {
        case 'orderView':
            currentView = orderView;
            currentRouter = orderRouter;
            break;
        case 'messageView':
            currentView = messageView;
            currentRouter = messageRouter;
            break;
        case 'friendView':
            currentView = friendView;
            currentRouter = friendRouter;
            break;
        default:
            currentView = mainView;
            currentRouter = mainRouter;
    }

    currentRouter.navigate(url, {
        animate: true,
        ignoreCache: true
    });
}

function togglePattern(type) {
    if (type == 0) {
        $('#indexButtons').find('input:eq(0)').removeClass('button-active');
        $('#indexButtons').find('input:eq(1)').addClass('button-active');
    } else if (type == 1) {
        $('#indexButtons').find('input:eq(1)').removeClass('button-active');
        $('#indexButtons').find('input:eq(0)').addClass('button-active');
    }

    $$.ajax({
        url: 'somepage.html',
        method: 'GET',
        cache: false,
        success: function(data) {

        }
    })
}


//toolBar
$$('#orderBtn').on('click', function () {
    loadPage('/order/orderPage', null, 'orderView');
})

$$('#messageBtn').on('click', function () {
    loadPage('/message/messageList', null, 'messageView');
})


//mainView


//orderView
$$('#view-order').on('page:init', '.page[data-name="orderPage"]', function (e) {
    var page = e.detail;
    pageAddRefresh(page);
})

//friendView


//messageView
$$('#view-message').on('page:init', '.page[data-name="messageList"]', function (e) {
    var page = e.detail;
    pageAddRefresh(page, function() {
        setTimeout(function () {
            loadPage('/message/messageList', null, 'messageView');
            app.ptr.done();
        }, 200)
    });
})


