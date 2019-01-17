// Dom7
var $$ = Dom7;

// Framework7 App main instance
var app  = new Framework7({
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
    methods: {
        helloWorld: function () {
            app.dialog.alert('Hello World!');
        },
    },
    // App routes
    routes: routes,
});

// Init/Create main view
var mainView = app.views.create('.view-main', {
    url: '/'
});
