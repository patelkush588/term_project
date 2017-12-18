var app= angular.module('myApp',[]);

app.controller('myController', ['$scope','myService', function($scope,myService){
	//$scope.abc = myService.def;
	$scope.pqr = myService.def;
	
	$scope.plusOne = function(){
		myService.def++;
		alert(myService.def);
	}
}])

app.service('myService', [function(){
	var srvc = [];

	srvc.def = 1;

	return srvc;
}]);