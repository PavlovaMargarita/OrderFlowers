app.controller("authorizationController", function ($scope, $http) {
        $scope.navbarVisibility = true; //флаг видимости меню
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
                $scope.authorization.info = data.login;
            });
            response.error(function (data) {
                $scope.authorization.info = "error";
            });
        }
    });