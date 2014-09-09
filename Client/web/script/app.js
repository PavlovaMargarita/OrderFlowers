var app = angular.module("OrderFlowers", ['ngRoute']);

app.run(function($rootScope){
    $rootScope.menuVisibility = false; //Отображение меню. True - видим.
    $rootScope.role = false; //Можем хранить роль пользователя, не используя cookie. $rootScope работает по принципу глобальной переменной
});

app.config(function($routeProvider){
    $routeProvider
        .when('/', {
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
            templateUrl: 'pages/order_list.html',
            controller: 'orderCreateController'
        } )
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