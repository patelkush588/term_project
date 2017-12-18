angular.module("aModule",[])

.controller("myController",["$scope", "MyService", function($scope, MyService){

$scope.object = [];	
$scope.object.fname= "niks";
$scope.object.lname= "pans";
$scope.object.binding= 4;

$scope.double = function(){
	$scope.object.binding = MyService.makeDouble($scope.object.binding);
}

}])

.controller("myController2",["$scope", function($scope){

$scope.object = [];	
$scope.object.str= "OLA AMIGOOSSS!!";

}])

.filter("camelCase",function(){

	var camel = function( input ){
		var word = input.split(' ');

		for(var i=0; len = word.length, i < len; i++)
			word[i] = word[i].charAt(0).toUpperCase() + word[i].slice(1);
		return word.join('');
	};

	return camel;
})

.factory("MyService", function(){
	var calc = [];

	calc.makeDouble = function(a){
		return a * 2;
	}

	return calc;
});