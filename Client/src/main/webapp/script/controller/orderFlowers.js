app.controller("orderListController", function ($scope, $http) {
    /*$scope.checkIfAuthenticated = function(){
        return AuthService.isAuthenticated();
    }*/

    var response = $http({
        method: "get",
        url: "/OrderFlowers/getOrderList"
    });
    response.success(function (data) {
        $scope.orders = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });


});

app.controller("orderCreateController", function ($scope, $http) {
    $scope.message = "orderCreateController";
});

app.controller("orderSearchController", function ($scope, $http) {
    $scope.message = "orderSearchController";
});
