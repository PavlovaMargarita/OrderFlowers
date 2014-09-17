app.controller("orderListController", function ($scope, AuthService) {
    $scope.message = "Order List";

    $scope.checkIfAuthenticated = function(){
        return AuthService.isAuthenticated();
    }


});

app.controller("orderCreateController", function ($scope, $http) {
    $scope.message = "orderCreateController";
});

app.controller("orderSearchController", function ($scope, $http) {
    $scope.message = "orderSearchController";
});
