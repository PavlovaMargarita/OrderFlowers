app.controller("orderHistoryController", function ($scope, $http, $rootScope, PagerService) {
    $scope.orderHistory = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    var getOrderHistory = $http({
        method: "get",
        url: "/OrderFlowers/getOrderHistory",
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    getOrderHistory.success(function (data){
        $scope.orderHistory = data.pageableContacts;
        $scope.totalRecords = data.totalCount;
        $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
        $scope.range = PagerService.buildRange($scope.totalPages);
    });

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/OrderFlowers/getOrderHistory",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.contacts = data.pageableContacts;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);

        });
    }

    $scope.isPrevDisabled = function () {
        return PagerService.isPrevDisabled($scope.currentPage);
    }

    $scope.isNextDisabled = function () {
        return PagerService.isNextDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.isFirstDisabled = function () {
        return PagerService.isFirstDisabled($scope.currentPage);
    }

    $scope.isLastDisabled = function () {
        return PagerService.isLastDisabled($scope.currentPage, $scope.totalPages);
    }
});
