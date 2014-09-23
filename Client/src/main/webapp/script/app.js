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

app.service('PagerService', function() {
    this.totalPageNumber = function(pageRecords, totalRecords) {
        return Math.floor((totalRecords + pageRecords - 1) / pageRecords);
    }

    this.buildRange = function(totalPageNumber) {
        var pages = [];
        for(var i = 1;i <= totalPageNumber; i++) {
            pages.push(i);
        }
        return pages;
    }

    this.isPrevDisabled = function(currentPage){
        return currentPage === 1 ? "disabled" : "";
    }

    this.isNextDisabled = function(currentPage, totalPageCountt){
        return currentPage === totalPageCountt ? "disabled" : "";
    }

    this.isFirstDisabled = function(currentPage){
        return currentPage === 1 ? "disabled" : "";
    }

    this.isLastDisabled = function(currentPage, totalPageCount){
        return currentPage === totalPageCount ? "disabled" : "";
    }
});

app.service('Validation', function(){
   this.validationName = function(value){
       var re = /^[A-zА-яЁё -]+$/;
       return re.test(value);
   }
    this.validationPatronymic = function(value){
        var re = /^[A-zА-яЁё]+$/;
        return re.test(value);
    }
    this.validationEmail = function(value){
        if(value == undefined){
            return true;
        }
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(value);
    }
    this.validationCity = function(value){
        var re = /^[A-zА-яЁё -]+$/;
        return re.test(value);
    }
    this.validationStreet = function(value){
        var re = /^[A-zА-яЁё0-9 -]+$/;
        return re.test(value);
    }
    this.validationInt = function(value){
        if(value == undefined){
            return true;
        }
        var re = /^\d+$/;
        return re.test(value);
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

