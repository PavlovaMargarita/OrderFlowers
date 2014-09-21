var app = angular.module("OrderFlowers", ['ngRoute', 'checklist-model', 'ngCookies']);

app.run(function($rootScope, $cookieStore){

    $rootScope.recordsOnPage = 5;

    $rootScope.isAuth = function(){
        var user = $cookieStore.get("userInfo");
        return !(angular.isUndefined && user == null);
    }

    $rootScope.hasAuthority = function(roles){
        var user = $cookieStore.get("userInfo");
        if (typeof(user) == "undefined"){
            return false;
        }
        var userRole = user.role;
        return (roles.indexOf(userRole) > -1);
    }
});

app.config(function($routeProvider){
    $routeProvider
        .when('/login', {
            templateUrl: 'pages/authorization.html',
            controller: 'authorizationController'
        } )
        .when('/contactList', {
            templateUrl: 'pages/contact_list.html',
            controller: 'contactListController'
        } )
        .when('/contactCreate', {
            templateUrl: 'pages/contact_create.html',
            controller: 'contactCreateController'
        } )
        .when('/contactSearch', {
            templateUrl: 'pages/contact_search.html',
            controller: 'contactSearchController'
        } )
        .when('/contactCorrect/:id', {
            templateUrl: 'pages/contact_create.html',
            controller: 'contactCorrectController'
        } )



        .when('/orderList', {
            templateUrl: 'pages/order_list.html',
            controller: 'orderListController'
        } )
        .when('/orderCreate', {
            templateUrl: 'pages/order_create.html',
            controller: 'orderCreateController'
        } )
        .when('/orderCorrect/:id', {
            templateUrl: 'pages/order_create.html',
            controller: 'orderCorrectController'
        })
        .when('/orderSearch', {
            templateUrl: 'pages/order_search.html',
            controller: 'orderSearchController'
        } )



        .when('/userList', {
            templateUrl: 'pages/user_list.html',
            controller: 'userListController'
        } )
        .when('/userCreate', {
            templateUrl: 'pages/user_create.html',
            controller: 'userCreateController'
        } )
        .when('/userCorrect/:id', {
            templateUrl: 'pages/user_create.html',
            controller: 'userCorrectController'
        } )
});

