app.controller("userListController", function ($scope, $http, $location) {
    $scope.usersToDelete = [];
    var response = $http({
        method: "get",
        url: "/OrderFlowers/userList"
    });
    response.success(function (data) {
        $scope.users = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });

    $scope.delete = {};
    $scope.delete.doClick = function(){
        var userDelete = $http({
            method: "post",
            url: "/OrderFlowers/userDelete",
            data: {
                deleteId: $scope.usersToDelete
            }
        });
        userDelete.success(function (data) {
            var userList = $http({
                method: "get",
                url: "/OrderFlowers/userList"
            });
            userList.success(function (data) {
                $scope.users = data;
                $location.path('/userList');
                $location.replace();
            });
            userList.error(function (data) {
                $scope.authorization.info = "error";
            });
        });
        userDelete.error(function (data) {
            $scope.authorization.info = "error";
        });
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
            var userList = $http({
                method: "get",
                url: "/OrderFlowers/userList"
            });
            userList.success(function (data) {
                $scope.users = data;
                $location.path('/userList');
                $location.replace();
            });
            userList.error(function (data) {
                $scope.authorization.info = "error";
            });
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
            if(element == $scope.user.role){
                $scope.user.role = $scope.roles[index];
            }
        }
    });
    roleEnum.error(function (data) {
        $scope.authorization.info = "error";
    });

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
            var userList = $http({
                method: "get",
                url: "/OrderFlowers/userList"
            });
            userList.success(function (data) {
                $scope.users = data;
                $location.path('/userList');
                $location.replace();
            });
            userList.error(function (data) {
                $scope.authorization.info = "error";
            });
        });
        user.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});