app.controller("authorizationController", function ($scope, $http, $location, $rootScope) {
    $scope.authorization = {};

    $scope.authorization.doClick = function (item, event) {
        var response = $http({
            method: "post",
            url: "/OrderFlowers/authorize",
            data: {
                login: $scope.login,
                password: $scope.password
            }
        });
        response.success(function (data) {
            $rootScope.role = data.role;
            if($rootScope.role == 'RECEIVING_ORDERS_MANAGER' || $rootScope.role == 'SUPERVISOR' || $rootScope.role == 'ADMIN'){
                $location.path('/contactList');
            } else {
                $location.path('/orderList');
            }
            $location.replace();
            $rootScope.menuVisibility = true;

        });
        response.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});