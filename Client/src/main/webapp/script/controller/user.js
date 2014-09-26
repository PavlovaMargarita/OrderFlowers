var loginList;
var oldLogin;
app.controller("userListController", function ($scope, $http, $location, $rootScope, $route, PagerService) {
    $scope.users = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    $scope.usersToDelete = [];
    var response = $http({
        method: "get",
        url: "/OrderFlowers/userList",
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.users = data.pageableData;
        $scope.totalRecords = data.totalCount;
        $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
        $scope.range = PagerService.buildRange($scope.totalPages);
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/OrderFlowers/userList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.users = data.pageableData;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
    }

    $scope.delete = {};
    $scope.delete.doClick = function() {
        if ($scope.usersToDelete.length != 0) {
            var userDelete = $http({
                method: "post",
                url: "/OrderFlowers/userDelete",
                data: {
                    checkId: $scope.usersToDelete
                }
            });
            userDelete.success(function (data) {
                $route.reload();
            });
            userDelete.error(function (data) {
                $scope.authorization.info = "error";
            });
        }
    }
});

app.controller("userCreateController", function ($scope, $http, $location) {
    var contacts = $http({
        method: "get",
        url: "/OrderFlowers/contactListForUser",
        params: {
            id: 0
        }
    });
    contacts.success(function (data) {
        $scope.contacts = data;
    });
    contacts.error(function (data) {
        $scope.authorization.info = "error";
    });
    var roleEnum = $http({
        method: "get",
        url: "/OrderFlowers/roleEnum"
    });
    roleEnum.success(function (data) {
        $scope.roles = data;
    });
    roleEnum.error(function (data) {
        $scope.authorization.info = "error";
    });

    var login = $http({
        method: "post",
        url: "/OrderFlowers/getLogin"
    });
    login.success(function (data) {
        loginList = data;
    });
    login.error(function (data) {
        $scope.authorization.info = "error";
    });

    $scope.isCorrectLogin = {};
    $scope.isCorrectLogin.onkeyup = function(){
        if(loginList.indexOf($scope.user.login) == -1 || $scope.user.login == oldLogin){
            document.getElementById('login').style.color = "black";
        } else{
            document.getElementById('login').style.color = "#ff0000";
        }
    }

    $scope.save = {};
    $scope.save.doClick = function(){
        var user = $http({
            method: "post",
            url: "/OrderFlowers/saveUserCreate",
            data: {
                idContact: $scope.contact.id,
                role: $scope.user.role,
                login: $scope.user.login,
                password: $scope.user.password
            }
        });
        user.success(function (data) {
            $location.path('/userList');
            $location.replace();
        });
        user.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});

app.controller("userCorrectController", function ($scope, $http, $routeParams, $location) {
    var id = $routeParams.id;
    var user = $http({
        method: "get",
        url: "/OrderFlowers/userCorrect",
        params: {
            id: id
        }
    });
    user.success(function (data) {
        $scope.user = data;
        oldLogin = data.login;
    });
    user.error(function (data) {
        $scope.authorization.info = "error";
    });
    var contacts = $http({
        method: "get",
        url: "/OrderFlowers/contactListForUser",
        params: {
            id: id
        }
    });
    contacts.success(function (data) {
        $scope.contacts = data;
        data.forEach(selectContact);
        function selectContact(element, index){
            if(element.id == $scope.user.idContact){
                $scope.contact = $scope.contacts[index];
            }
        }
    });
    contacts.error(function (data) {
        $scope.authorization.info = "error";
    });
    var roleEnum = $http({
        method: "get",
        url: "/OrderFlowers/roleEnum"
    });
    roleEnum.success(function (data) {
        $scope.roles = data;
        data.forEach(selectRole);
        function selectRole(element, index){
            if(element.role == $scope.user.role){
                $scope.user.role = $scope.roles[index].role;
            }
        }
    });
    roleEnum.error(function (data) {
        $scope.authorization.info = "error";
    });

    var login = $http({
        method: "post",
        url: "/OrderFlowers/getLogin"
    });
    login.success(function (data) {
        loginList = data;
    });
    login.error(function (data) {
        $scope.authorization.info = "error";
    });

    $scope.isCorrectLogin = {};
    $scope.isCorrectLogin.onkeyup = function(){
        if(loginList.indexOf($scope.user.login) == -1 || $scope.user.login == oldLogin){
            document.getElementById('login').style.color = "black";
        } else{
            document.getElementById('login').style.color = "#ff0000";
        }
    }

    $scope.save = {};
    $scope.save.doClick = function(){
        var user = $http({
            method: "post",
            url: "/OrderFlowers/saveUserCorrect",
            data: {
                id: id,
                idContact: $scope.contact.id,
                role: $scope.user.role,
                login: $scope.user.login,
                password: $scope.user.password
            }
        });
        user.success(function (data) {
//            var userList = $http({
//                method: "get",
//                url: "/OrderFlowers/userList"
//            });
//            userList.success(function (data) {
//                $scope.users = data;
//                $location.path('/userList');
//                $location.replace();
//            });
//            userList.error(function (data) {
//                $scope.authorization.info = "error";
//            });

            $location.path('/userList');
            $location.replace();
        });
        user.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});