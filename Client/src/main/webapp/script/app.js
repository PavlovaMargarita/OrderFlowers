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

app.service('MailService', function($http, $q){

    this.getMailTemplates = function(){
        var emails;
        var deferred = $q.defer();
        var showTemplate = $http({
            method: "get",
            url: "/OrderFlowers/showTemplate"
        });
        showTemplate.success(function(data) {
            deferred.resolve(data);
        });
        showTemplate.error(function (reason) {
            deferred.reject(reason);
        });
        return deferred.promise;
    }

    this.getEmails = function(checkContacts){
        var deferred = $q.defer();
        var showEmail = $http({
            method: "get",
            url: "/OrderFlowers/showEmail",
            params: {
                checkId: checkContacts
            }
        });
        showEmail.success(function (data) {
            deferred.resolve(data);
        });
        showEmail.error(function (data) {
            deferred.reject(reason);
        });
        return deferred.promise;
    }

    this.sendMail = function(emails, text, topic){
        var deferred = $q.defer();
        var emailSend = $http({
            method: "post",
            url: "/OrderFlowers/sendEmail",
            data: {
                emails: emails,
                text: text,
                topic: topic
            }
        });
        emailSend.success(function (data) {
            deferred.resolve(true);
        });
        emailSend.error(function (reason) {
            deferred.reject(reason);
        });
        return deferred.promise;
    }
});

app.service('ContactsCommonService', function(PagerService, MailService, $route, $http) {

    this.isPrevDisabled = function(currentPage){
        return PagerService.isPrevDisabled(currentPage);
    }

    this.isNextDisabled = function(currentPage, totalPages){
        return PagerService.isNextDisabled(currentPage, totalPages);
    }

    this.isFirstDisabled = function(currentPage){
        return PagerService.isFirstDisabled(currentPage);
    }

    this.isLastDisabled = function(currentPage, totalPages){
        return PagerService.isLastDisabled(currentPage, totalPages);
    }

    this.getMailTemplates = function(){
        var dataPromise = MailService.getMailTemplates();
        return dataPromise;
    }

    this.getEmails = function(checkContacts){
        var dataPromise = MailService.getEmails(checkContacts);
        return dataPromise;
    }

    this.sendMail = function(emails, text, topic){
        return MailService.sendMail(emails, text, topic);

    }

    this.deleteContacts = function(checkContacts){
        if (checkContacts.length != 0) {
            var contactDelete = $http({
                method: "post",
                url: "/OrderFlowers/contactDelete",
                data: {
                    checkId: checkContacts
                }
            });
            contactDelete.success(function (data) {
                $route.reload();
            });
        }
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

