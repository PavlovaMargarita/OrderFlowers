app.controller("orderListController", function ($scope, $rootScope, $http, $location) {
    //получаем список заказов при загузке страницы order_list.html
    var response = $http({
        method: "get",
        url: "/OrderFlowers/orderList"
    });
    response.success(function (data) {
        $scope.orders = data;
    });
});




app.controller("orderCreateController", function ($scope, $http) {


});




app.controller("orderCorrectController", function ($scope, $routeParams, $http) {
    var id = $routeParams.id;
    var order = $http ({
        method: "get",
        url: "/OrderFlowers/showOrder",
        params: {
            id: id
        }
    });
    order.success(function (data) {
        $scope.order = data;
    });

});




app.controller("orderSearchController", function ($scope, $http) {
    $scope.message = "orderSearchController";
});
