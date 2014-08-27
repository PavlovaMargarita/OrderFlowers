angular.module("OrderFlowers", [])
    .controller("AuthorizationController", function ($scope, $http) {
        $scope.authorization = {};
        $scope.authorization.doClick = function (item, event) {
            var response = $http({
                method: "post",
                url: "/OrderFlowers/authorize",
                data: {
                    login: document.getElementById("login").value,
                    password: document.getElementById("login").value
                }
            });
            response.success(function (data) {
                $scope.authorization.info = data.login;
            });
        }
    });