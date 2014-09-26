app.controller("orderHistoryController", function ($scope, $http) {
    var getOrderHistory = $http({
        method: "get",
        url: "/OrderFlowers/getOrderHistory"
    });
    getOrderHistory.success(function (data){
        $scope.orderHistory = data;
    });
});
