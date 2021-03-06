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
        url: "/OrderFlowers/user/userList",
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.users = data.pageableData;
        $scope.totalRecords = data.totalCount;
        $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
        $scope.range = PagerService.buildRange($scope.totalPages);
    });

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/OrderFlowers/user/userList",
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
                url: "/OrderFlowers/user/userDelete",
                data: {
                    checkId: $scope.usersToDelete
                }
            });
            userDelete.success(function (data) {
                $route.reload();
            });
        }
    }
});

app.controller("userCreateController", function ($scope, $http, $location, Validation) {
    var contacts = $http({
        method: "get",
        url: "/OrderFlowers/contact/contactListForUser",
        params: {
            id: 0
        }
    });
    contacts.success(function (data) {
        $scope.contacts = data;
    });

    var roleEnum = $http({
        method: "get",
        url: "/OrderFlowers/user/roleEnum"
    });
    roleEnum.success(function (data) {
        $scope.roles = data;
    });

    var login = $http({
        method: "post",
        url: "/OrderFlowers/user/getLogin"
    });
    login.success(function (data) {
        loginList = data;
    });

    $scope.isCorrectLogin = {};
    $scope.isCorrectLogin.onkeyup = function(){
        if(loginList.indexOf($scope.user.login) == -1 || $scope.user.login == oldLogin){
            document.getElementById('div-login').className = "input-group";
        } else{
            document.getElementById('div-login').className = "input-group has-error";
        }
    }

    $scope.save = {};
    $scope.save.doClick = function(){
        if(validateUserSave($scope, Validation)) {
            var user = $http({
                method: "post",
                url: "/OrderFlowers/user/saveUserCreate",
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
        }
    }
});

app.controller("userCorrectController", function ($scope, $http, $routeParams, $location, Validation) {
    $scope.user = [];
    var id = $routeParams.id;
    var user = $http({
        method: "get",
        url: "/OrderFlowers/user/userCorrect",
        params: {
            id: id
        }
    });
    user.success(function (data) {
        $scope.user = data;
        oldLogin = data.login;
    });
    var contacts = $http({
        method: "get",
        url: "/OrderFlowers/contact/contactListForUser",
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
    var roleEnum = $http({
        method: "get",
        url: "/OrderFlowers/user/roleEnum"
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

    var login = $http({
        method: "post",
        url: "/OrderFlowers/user/getLogin"
    });
    login.success(function (data) {
        loginList = data;
    });

    $scope.isCorrectLogin = {};
    $scope.isCorrectLogin.onkeyup = function(){
        if(loginList.indexOf($scope.user.login) == -1 || $scope.user.login == oldLogin){
            document.getElementById('div-login').className = "input-group";
        } else{
            document.getElementById('div-login').className = "input-group has-error";
        }
    }

    $scope.save = {};
    $scope.save.doClick = function(){
        if(validateUserSave($scope,Validation)) {
            var user = $http({
                method: "post",
                url: "/OrderFlowers/user/saveUserCorrect",
                data: {
                    id: id,
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
        }
    }
});

function validateUserSave(value, Validation){
    var ok = true;
    var hasError = "input-group has-error";
    var noError = "input-group";
    var validContact = false;
    var validRole = false;
    var validLogin = false;
    var validPassword = false;
    if(value.contact!= undefined && Validation.validationInt(value.contact.id)){
        document.getElementById('div-contact').className = noError;
        validContact = true;
        document.getElementById('label-contact').style.display = 'none';
    }
    if(!validContact){
        document.getElementById('div-contact').className = hasError;
        document.getElementById('label-contact').style.display = 'inline';
        ok = false;
    }

    if(value.user!= undefined && Validation.validationRole(value.user.role)){
        document.getElementById('div-role').className = noError;
        document.getElementById('label-role').style.display = 'none';
        validRole = true;
    }
    if(!validRole){
        document.getElementById('div-role').className = hasError;
        document.getElementById('label-role').style.display = 'inline';
        ok = false;
    }
    if(loginList.indexOf(value.user.login) == -1 || value.user.login == oldLogin){
        if(value.user!= undefined && value.user.login != undefined && value.user.login.trim() != ''){
            document.getElementById('div-login').className = noError;
            document.getElementById('label-login').style.display = 'none';
            validLogin = true;
        }
    } else{
        document.getElementById('div-login').className = hasError;
        document.getElementById('label-login').style.display = 'inline';
        ok = false;
    }

    if(!validLogin){
        document.getElementById('div-login').className = hasError;
        document.getElementById('label-login').style.display = 'inline';
        ok = false;
    }
    if(value.user!= undefined && value.user.password != undefined && value.user.password.trim() != ''){
        document.getElementById('div-password').className = noError;
        document.getElementById('label-password').style.display = 'none';
        validPassword = true;
    }
    if(!validPassword){
        document.getElementById('div-password').className = hasError;
        document.getElementById('label-password').style.display = 'inline';
        ok = false;
    }
    if(!validContact && !validLogin && !validRole && !validPassword){
        document.getElementById('label-require').style.display = 'inline';
    } else{
        document.getElementById('label-require').style.display = 'none';
    }
    return ok;
}
