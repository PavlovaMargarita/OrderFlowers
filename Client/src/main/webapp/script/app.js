var app = angular.module("OrderFlowers", ['ngRoute', 'checklist-model', 'ngCookies']);

app.run(function($rootScope, $cookieStore){

    $rootScope.recordsOnPage = 10;

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

app.service('ErrorPopupService', function($timeout) {
    this.showErrorMessage = function(message){
        document.getElementById("errorOverlay").style.visibility = "visible";
        document.getElementById('errorMessage').innerHTML = message;
        $timeout(function() {
            document.getElementById("errorOverlay").style.visibility = "hidden";
        }, 3000);
    }

});

app.factory('ServerHttpResponseInterceptor', function($q, ErrorPopupService) {
    return function (promise) {
        return promise.then(function (response) {
                return response;
            },
            function (response) {
                var responseStatus = response.status;
                switch(responseStatus){
                    case 500:{
                        ErrorPopupService.showErrorMessage("Произошла серверная ошибка.\r\n" +
                            "Приносим свои извинения за неудобства.");
                        break;
                    }
                    case 403:{
                        ErrorPopupService.showErrorMessage("Произошла серверная ошибка.\r\n" +
                            "Приносим свои извинения за неудобства.");
                        break;
                    }
                    case 401:{
                        ErrorPopupService.showErrorMessage("У вас нет прав доступа, или вы не авторизированы");
                        break;
                    }
                    case 400:{
                        ErrorPopupService.showErrorMessage("Введены некорректные данные.");
                        break;
                    }
                }
                return $q.reject(response);
            });
    };
});


app.service('PagerService', function() {
    this.totalPageNumber = function(pageRecords, totalRecords) {
        var totalPageNumber = 1;
        if(typeof totalRecords != 'undefined'){
            totalPageNumber = Math.floor((totalRecords + pageRecords - 1) / pageRecords);
        }
        return (totalPageNumber == 0) ? 1 : totalPageNumber;
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
        emailSend.success(function () {
            deferred.resolve(true);
        });
        emailSend.error(function (reason) {
            deferred.reject(reason);
        });
        return deferred.promise;
    }
});

app.service('ContactsCommonService', function(PagerService, MailService, $route, $http, $q) {

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
            var deferred = $q.defer();
            var contactDelete = $http({
                method: "post",
                url: "/OrderFlowers/contactDelete",
                data: {
                    checkId: checkContacts
                }
            });
            contactDelete.success(function (data) {
                deferred.resolve(data == "true");
;            });
            contactDelete.error(function(reason){
                deferred.reject(reason);
            });
            return deferred.promise;
        }
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
        if(value == undefined || value == ""){
            return true;
        }
        var re = /^\d+$/;
        return re.test(value);
    }
    this.validationRole = function(value){
        var re = /^[A-Z_]+$/;
        return re.test(value);
    }
});

app.config(function($routeProvider, $httpProvider){
    $routeProvider
        .when('/login', {
            templateUrl: 'pages/authorization.html',
            controller: 'authorizationController'
        } )
        .when('/contactList', {
            templateUrl: 'pages/contact_list.html',
            controller: 'contactListController'
        } )
        .when('/contactSearchResult', {
            templateUrl: 'pages/contact_search_result.html',
            controller: 'contactSearchResultController'
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
        } ).when('/orderSearchResult', {
            templateUrl: 'pages/order_search_result.html',
            controller: 'orderSearchResultController'
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


        .when('/settings', {
            templateUrl: 'pages/settings.html',
            controller: 'orderHistoryController'
        })


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

    $httpProvider.responseInterceptors.push('ServerHttpResponseInterceptor');
});

