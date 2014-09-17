app.controller("authorizationController", function ($scope, $http, $location, $rootScope, $cookieStore) {
    $scope.authorization = {};

    var isSuccess = ($location.search()).success;
    if(isSuccess){
        var response = $http({
            method: "get",
            url: "/OrderFlowers/userInfo"
        });
        response.success(function (data) {
            $cookieStore.put("userInfo", data);
            $scope.successRedirect(data);
        });
    }

    $scope.processAuthorization = function(){
    }
    $scope.processSuccess = function(){
        var success = ($location.search()).success;
        if(success != null){
            $scope.storeCurrentUserInfo();
        }
    }

    $scope.processError = function(){
        var isError = ($location.search()).error;
        if(isError){
            return "Ошибка авторизации!";
        }
    }

    $scope.storeCurrentUserInfo = function(){
        var response = $http({
            method: "get",
            url: "/OrderFlowers/userInfo"})
        .success(function (data) {
            $cookieStore.put("userInfo", data);
            $scope.successRedirect(data);})
        .error(function (data) {
            alert("ALERT");
        });
    }

    $scope.successRedirect = function(data){
        if(data.role == 'RECEIVING_ORDERS_MANAGER' || data.role == 'SUPERVISOR' || data.role == 'ADMIN'){
            $location.path('/contactList');
        } else {
            $location.path('/orderList');
        }
        $location.replace();
    }
});