app.controller("orderListController", function ($scope, $rootScope, $http) {
    //получаем список заказов при загузке страницы order_list.html
    if (!$rootScope.isSearchOrder){
        var response = $http({
            method: "get",
            url: "/OrderFlowers/orderList"
        });
        response.success(function (data) {
            $scope.orders = data;
        });
    }
    else {
        $scope.orders = $rootScope.data;
        $rootScope.isSearchOrder = false;
    }
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




app.controller("orderSearchController", function ($scope, $rootScope, $location, $http) {
    $scope.search = {};
    $scope.search.doClick = function (){
        var response = $http({
            method: "post",
            url: "/OrderFlowers/orderSearch",
            data: {
                customerSurname: $scope.order.customerSurname,
                recipientSurname: $scope.order.recipientSurname,
                lowerOrderDate: $scope.order.lowerOrderDate,
                upperOrderDate: $scope.order.upperOrderDate
            }
        });
        response.success(function (data){
            $rootScope.isSearchOrder = true;
            $rootScope.data = data;
            $location.path('/orderList');
            $location.replace();
        });
    }
});
