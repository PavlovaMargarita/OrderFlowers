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




app.controller("orderCorrectController", function ($scope, $routeParams, $rootScope, $cookieStore, $http) {
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

        var possibleOrderStates = $http ({
            method: "get",
            url: "/OrderFlowers/getResolvedOrderState",
            params: {
                currentState: $scope.order.currentState
            }
        });
        possibleOrderStates.success(function (data) {
            $scope.possibleStatesHide = false;
            $scope.disabledManagersFields = true;

            if (data == ""){
                $scope.possibleStatesHide = true;
            }
            $scope.possibleStates = data;

            if ($rootScope.hasAuthority(['ROLE_SUPERVISOR','ROLE_RECEIVING_ORDERS_MANAGER'])){
                $scope.disabledManagersFields = false;
                var getManagers = $http ({
                    method: "get",
                    url: "/OrderFlowers/getUsersByRole",
                    params: {
                        role: ['ROLE_PROCESSING_ORDERS_SPECIALIST', 'ROLE_SERVICE_DELIVERY_MANAGER']
                    }
                })
                getManagers.success(function (data) {
                    $scope.handlerManagers = data['ROLE_PROCESSING_ORDERS_SPECIALIST'];
                    $scope.deliveryManagers = data['ROLE_SERVICE_DELIVERY_MANAGER'];

                    $scope.handlerManagers.forEach(selectHandlerManager);
                    function selectHandlerManager(element, index){
                        if(element.id == $scope.order.handlerManager.id){
                            $scope.handlerManager = $scope.handlerManagers[index];
                        }
                    }

                    $scope.deliveryManagers.forEach(selectDeliveryManager);
                    function selectDeliveryManager(element, index){
                        if(element.id == $scope.order.deliveryManager.id){
                            $scope.deliveryManager = $scope.deliveryManagers[index];
                        }
                    }
                });

            }
            else {
                $scope.deliveryManagers = [$scope.order.deliveryManager];
                $scope.deliveryManager = $scope.order.deliveryManager;
                $scope.handlerManagers = [$scope.order.handlerManager];
                $scope.handlerManager = $scope.order.handlerManager;
            }
        });
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
