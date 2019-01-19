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

// Init/Create main view
var mainView = app.views.create('.view-main');
var mainRouter = mainView.router;
var orderView = app.views.create('.view-order');
var orderRouter = orderView.router;
var friendView = app.views.create('.view-friend');
var friendRouter = friendView.router;
var messageView = app.views.create('.view-message');
var messageRouter = messageView.router;

//全局
//方法引用
//弹出提示消息
var alertMessage = app.dialog.alert;
//弹出确认消息
var confirmMessage = app.dialog.confirm;

//全局方法
function loagPage(url, param, viewName) {
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

//toolBar
$$('#orderBtn').on('click', function () {
    loagPage('/order/orderPage', null, 'orderView');
})


//mainView


//orderView


//friendView


//message


