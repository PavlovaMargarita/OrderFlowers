app.controller("contactCreateController", function ($scope, $http, $location) {
    $scope.message = "contactCreateController";
});

app.controller("contactListController", function ($scope, $http, $location) {
    var response = $http({
        method: "get",
        url: "/OrderFlowers/contactList"
    });
    response.success(function (data) {
        $scope.contacts = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });
//    $scope.message = "contactListController";
});

app.controller("contactSearchController", function ($scope, $http) {
    $scope.message = "contactSearchController";
});

app.controller("contactCorrectController", function ($scope, $http, $routeParams) {
    var id = $routeParams.id;
    var response = $http({
        method: "get",
        url: "/OrderFlowers/contactCorrect",
        params: {
            id: id
        }
    });
    response.success(function (data) {
        $scope.contact = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });
});