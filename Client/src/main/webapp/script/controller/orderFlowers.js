app.controller("orderListController", function ($scope, $rootScope, $http, PagerService) {
    $scope.orders = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    //получаем список заказов при загузке страницы order_list.html
    var response = $http({
        method: "get",
        url: "/OrderFlowers/orderList",
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.orders = data.pageableData;
        $scope.totalRecords = data.totalCount;
        $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
        $scope.range = PagerService.buildRange($scope.totalPages);
    });

    $scope.getRecords = {};
    $scope.getRecords.doClick = function(pageNumber){
        var response = $http({
            method: "get",
            url: "/OrderFlowers/orderList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function(data){
            $scope.orders = data.pageableData;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);

        });
    }

    $scope.isPrevDisabled = function(){
        return PagerService.isPrevDisabled($scope.currentPage);
    }

    $scope.isNextDisabled = function(){
        return PagerService.isNextDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.isFirstDisabled = function(){
        return PagerService.isFirstDisabled($scope.currentPage);
    }

    $scope.isLastDisabled = function(){
        return PagerService.isLastDisabled($scope.currentPage, $scope.totalPages);
    }
});

app.controller("orderSearchResultController", function ($scope, $rootScope, $http, PagerService) {
    $scope.orders = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    //получаем список заказов при загузке страницы order_list.html
    if ($rootScope.isSearchOrder) {
        var searchRequest = $rootScope.orderSearchRequest;
        var response = $http({
            method: "post",
            url: "/OrderFlowers/orderSearch",
            data: searchRequest,
            params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.orders = data.pageableData;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
        response.error(function (data) {
            $scope.authorization.info = "error";
        });
        $rootScope.isSearchOrder = false;
    }

    $scope.getRecords = {};
    $scope.getRecords.doClick = function(pageNumber){
        var response = $http({
            method: "post",
            url: "/OrderFlowers/orderSearch",
            data: searchRequest,
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function(data){
            $scope.orders = data.pageableData;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);

        });
    }

    $scope.isPrevDisabled = function(){
        return PagerService.isPrevDisabled($scope.currentPage);
    }

    $scope.isNextDisabled = function(){
        return PagerService.isNextDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.isFirstDisabled = function(){
        return PagerService.isFirstDisabled($scope.currentPage);
    }

    $scope.isLastDisabled = function(){
        return PagerService.isLastDisabled($scope.currentPage, $scope.totalPages);
    }
});

app.controller("orderCreateController", function ($scope, $http, $location, Validation) {
    $scope.order = {};
    $scope.order.russianCurrentState = "новый";
    $scope.order.receiveManager = {};
    $scope.order.receiveManager.surname = "текущий менеджер";
    $scope.possibleStatesHide = true;

    document.getElementById('customer-id').value = "";
    document.getElementById('recipient-id').value = "";

    var getManagers = $http({
        method: "get",
        url: "/OrderFlowers/getUsersByRole",
        params: {
            role: ['ROLE_PROCESSING_ORDERS_SPECIALIST', 'ROLE_SERVICE_DELIVERY_MANAGER']
        }
    });
    getManagers.success(function (data) {
        $scope.handlerManagers = data['ROLE_PROCESSING_ORDERS_SPECIALIST'];
        $scope.deliveryManagers = data['ROLE_SERVICE_DELIVERY_MANAGER'];
    });

    $scope.correctOrder = {};
    $scope.correctOrder.doClick = function () {
        var currentDate = new Date();
        if (validateOrderSave($scope, Validation)) {
            var correctOrder = $http({
                method: "post",
                url: "/OrderFlowers/createOrder",
                data: {
                    orderDescription: $scope.order.orderDescription,
                    sum: $scope.order.sum,
                    deliveryManager: {
                        id: $scope.deliveryManager.id
                    },
                    handlerManager: {
                        id: $scope.handlerManager.id
                    },
                    customer: {
                        id: document.getElementById('customer-hidden-id').value
                    },
                    recipient: {
                        id: document.getElementById('recipient-hidden-id').value
                    },
                    date: currentDate.getFullYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDay()
                }
            });

            correctOrder.success(function (data) {
                $location.path('/orderList');
                $location.replace();
            });
            correctOrder.error(function (data) {
                alert("Не все поля заполнены");
            });
        }
    };
});

app.controller("orderCorrectController", function ($scope, $routeParams, $rootScope, $location, $cookieStore, $http, Validation) {
    var id = $routeParams.id;
    var order = $http({
        method: "get",
        url: "/OrderFlowers/showOrder",
        params: {
            id: id
        }
    });
    order.success(function (data) {
        $scope.order = data;

        var possibleOrderStates = $http({
            method: "get",
            url: "/OrderFlowers/getResolvedOrderState",
            params: {
                currentState: $scope.order.currentState
            }
        });
        possibleOrderStates.success(function (data) {
            $scope.possibleStatesHide = false;
            $scope.disabledOnTheRole = true;

            if (data == "") {
                $scope.possibleStatesHide = true;
            }
            $scope.possibleStates = data;

            if ($rootScope.hasAuthority(['ROLE_SUPERVISOR', 'ROLE_RECEIVING_ORDERS_MANAGER'])) {
                $scope.disabledOnTheRole = false;
                var getManagers = $http({
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
                    function selectHandlerManager(element, index) {
                        if (element.id == $scope.order.handlerManager.id) {
                            $scope.handlerManager = $scope.handlerManagers[index];
                        }
                    }

                    $scope.deliveryManagers.forEach(selectDeliveryManager);
                    function selectDeliveryManager(element, index) {
                        if (element.id == $scope.order.deliveryManager.id) {
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

    $scope.correctOrder = {};
    $scope.correctOrder.doClick = function () {
        var currentDate = new Date();
        if (validateOrderSave($scope, Validation)) {
            var correctOrder = $http({
                method: "post",
                url: "/OrderFlowers/correctOrder",
                data: {
                    id: $routeParams.id,
                    russianCurrentState: $scope.order.newState,
                    statusComment: $scope.order.statusComment,
                    orderDescription: $scope.order.orderDescription,
                    sum: $scope.order.sum,
                    date: currentDate.getFullYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDay(),
                    deliveryManager: {
                        id: $scope.deliveryManager.id
                    },
                    handlerManager: {
                        id: $scope.handlerManager.id
                    },
                    customer: {
                        id: document.getElementById('customer-hidden-id').value
                    },
                    recipient: {
                        id: document.getElementById('recipient-hidden-id').value
                    }

                }
            });
            correctOrder.success(function (data) {
                $location.path('/orderList');
                $location.replace();
            });
            correctOrder.error(function (data) {
                alert("Не все поля заполнены");
            });
        }
    };
});

app.controller("orderSearchController", function ($scope, $rootScope, $location, $http, Validation) {
    $scope.search = {};
    $scope.search.doClick = function (){
        if (validateOrderSearch($scope.order, Validation)) {
            var searchRequest = {
                customerSurname: $scope.order.customerSurname,
                recipientSurname: $scope.order.recipientSurname,
                lowerOrderDate: $scope.order.lowerOrderDate,
                upperOrderDate: $scope.order.upperOrderDate
            };
            $rootScope.orderSearchRequest = searchRequest;
            $rootScope.isSearchOrder = true;
            $location.path('/orderSearchResult');
            $location.replace();
        }
    }
});


function showModalNewOrderStatus() {
    if (document.getElementById('new-order-status').value != "") {
        document.getElementById('status-comment').value = "";
        $('#modal-order-status').modal('show');
    }
}

function hideModalNewOrderStatus() {
    document.getElementById('status-comment').value = "";
    $('#modal-order-status').modal('hide');
}

function saveModalOrderStatus() {
    $('#modal-order-status').modal('hide');
}

function validateOrderSave(value, Validation) {
    var hasError = "input-group has-error";
    var noError = "input-group";
    var ok = true;
    if (document.getElementById('customer-id').value == "") {
        ok = false;
        document.getElementById('div-customer').className = hasError;
    } else {
        document.getElementById('div-customer').className = noError;
    }

    if (document.getElementById('recipient-id').value == "") {
        ok = false;
        document.getElementById('div-recipient').className = hasError;
    } else {
        document.getElementById('div-receive').className = noError;
    }
    if (value.order.sum == undefined || value.order.sum == '' || !Validation.validationInt(value.order.sum) || value.order.sum == 0) {
        ok = false;
        document.getElementById('div-sum').className = hasError;
    } else {
        document.getElementById('div-sum').className = noError;
    }

    if (value.deliveryManager == undefined) {
        ok = false;
        document.getElementById('div-delivery').className = hasError;
    } else {
        document.getElementById('div-delivery').className = noError;
    }
    if (value.handlerManager == undefined) {
        ok = false;
        document.getElementById('div-handler').className = hasError;
    } else {
        document.getElementById('div-handler').className = noError;
    }
    if (value.order.orderDescription == undefined || value.order.orderDescription.trim() == '') {
        ok = false;
        document.getElementById('div-description').className = hasError;
    } else {
        document.getElementById('div-description').className = noError;
    }
    return ok;
}

function validateOrderSearch(value, Validation) {
    var hasError = "input-group has-error";
    var noError = "input-group";
    var fullInput = 0;
    var ok = true;
    if (value == undefined) {
        document.getElementById('div-customer-surname').className = hasError;
        document.getElementById('div-recipient-surname').className = hasError;
        document.getElementById('div-date-from').className = hasError;
        document.getElementById('div-date-to').className = hasError;
        return false;
    } else {
        document.getElementById('div-customer-surname').className = noError;
        document.getElementById('div-recipient-surname').className = noError;
        document.getElementById('div-date-from').className = noError;
        document.getElementById('div-date-to').className = noError;
    }
    if (value.customerSurname != undefined) {
        if (!Validation.validationName(value.customerSurname.trim())) {
            ok = false;
            document.getElementById('div-customer-surname').className = hasError;
        } else {
            document.getElementById('div-customer-surname').className = noError;
            fullInput++;
        }
    }
    if(value.recipientSurname != undefined) {
        if (!Validation.validationName(value.recipientSurname.trim())) {
            ok = false;
            document.getElementById('div-recipient-surname').className = hasError;
        } else {
            document.getElementById('div-recipient-surname').className = noError;
            fullInput++;
        }
    }
    if (value.lowerOrderDate != undefined) {
        fullInput++;
    }
    if (value.upperOrderDate != undefined) {
        fullInput++;
    }
    if (ok == true && fullInput == 0) {
        document.getElementById('div-customer-surname').className = hasError;
        document.getElementById('div-recipient-surname').className = hasError;
        document.getElementById('div-date-from').className = hasError;
        document.getElementById('div-date-to').className = hasError;
        return false;
    }
    return ok;
}
