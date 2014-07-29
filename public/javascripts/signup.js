angular.module('SignupApp', [])

.controller('SignupController', function($scope, $http) {
$scope.user = {};

$scope.submit = function() {
 $http.post('/signup', $scope.user).success(function(successData) {
    console.log("server responded success with",successData);
 }).error(function(errorData) {
     console.log("server responded error with",errorData);
 });}
});