app.controller("orderListController", function ($scope, $http, $cookieStore) {
    $scope.message = "Order List";

//    var response = $http({
//        method: "get",
//        url: "/OrderFlowers/userInfo"
//    });
//    response.success(function (data) {
//        $cookieStore.put("userInfo", data);
//        alert($cookieStore.get("userInfo").login);
//    });
//    response.error(function (data) {
//        alert("ALERT");
//    });
});

app.controller("orderCreateController", function ($scope, $http) {
    $scope.message = "orderCreateController";
});

app.controller("orderSearchController", function ($scope, $http) {
    $scope.message = "orderSearchController";
});
