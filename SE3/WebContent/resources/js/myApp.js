

var app= angular.module('myApp',["ngRoute","firebase","angular-md5"]);
app.config(function($routeProvider){
	$routeProvider
	.when("/",{
		templateUrl: "views/itemList.html",
		controller: "myAppListController",
		resolve: {
	      "currentAuth": ["Auth", function(Auth) {
	        return Auth.$waitForSignIn();
	      }]
	    }
	})
	.when("/login",{
		templateUrl: "views/login.html",
		controller: "AuthCtrl",
		resolve: {
	      "currentAuth": ["Auth", function(Auth) {
	        return Auth.$waitForSignIn();
	      }]
	    }
	})
	.when("/register",{
		templateUrl: "views/register.html",
		controller: "AuthCtrl",
		resolve: {
	      "currentAuth": ["Auth", function(Auth) {
	        return Auth.$waitForSignIn();
	      }]
	    }
	})
	.when("/addItem",{
		templateUrl: "views/addItem.html",
		controller: "myAppListController",
		resolve: {
	      "currentAuth": ["Auth", function(Auth) {
	        return Auth.$waitForSignIn();
	      }]
	    }
	})
	.when("/addItem/:id",{
		templateUrl: "views/addItem.html",
		controller: "myAppListController",
		resolve: {
	      "currentAuth": ["Auth", function(Auth) {
	        return Auth.$waitForSignIn();
	      }]
	    }
	})
	.otherwise({
		redirectTo : "/"
		});

	var config = {
	    apiKey: "AIzaSyAU9I7K3pi4ADvurM9HguZ1He-H-foPKoY",
	    authDomain: "groccerylist.firebaseapp.com",
	    databaseURL: "https://groccerylist.firebaseio.com",
	    projectId: "groccerylist",
	    storageBucket: "groccerylist.appspot.com",
	    messagingSenderId: "1056196848570"
	  };
  	firebase.initializeApp(config);
});

app.controller('myAppHome', ['$scope','Auth', function($scope, Auth){
	$scope.title="Grocery List App";
	$scope.authObj = Auth;
	$scope.email = $scope.authObj.$getAuth();

	$scope.signOut = function(){
		Auth.$signOut();
	}
}]);

//**************************AUTHENTICATIONSERVICE *************************

app.controller('myAppListController', ['$scope','$filter','$location','$routeParams','myService', 'md5','currentAuth','Auth',
	function($scope,$filter,$location,$routeParams,myService, md5,currentAuth, Auth){

	$scope.authObj = Auth;
	$scope.authObj.$onAuthStateChanged(function(firebaseUser) {
	  if (!firebaseUser) {
	    $location.path("/login");
	  }
	});

	if($routeParams.id){
		var temp = _.clone(myService.findById($routeParams.id));
		$scope.currentItem={"id": $routeParams.id, "itemChecked": temp.itemChecked, "itemName": temp.itemName, "itemCreated": temp.itemCreated};
	}else{
		var date = $filter('date')(new Date(),'yyyy-MM-dd');
		$scope.currentItem={id: 0, itemChecked: false, itemName: "ButterNaan", itemCreated: date};
	}
	
	//myService.groceryItems.$bind($scope,"groceryItems");
	$scope.groceryItems = myService.groceryItems;
	
	$scope.changeChecked = function(item){
		myService.changeChecked(item);
	}

	
	$scope.addItem = function(){
		if($routeParams.id){
			var temp = myService.findById($routeParams.id);
			temp.itemName= $scope.currentItem.itemName;
			myService.saveItem(temp);

		}else{
		myService.addItem($scope.currentItem);	
		}
		console.log($scope.currentItem);
		$location.path("/");
	}

	$scope.removeItem = function(item){
		myService.removeItem(item);
	}

	/*$scope.signOut = function(){
	
		$scope.authObj.$signOut();
	}
	
	$scope.createList = function(){
		myService.createList();
	}*/
}]);

app.service('myService',['$filter', 'md5', '$firebaseObject','$firebaseArray',
	 function($filter,md5,$firebaseObject,$firebaseArray){
	
	var $myScope = [];
	$myScope.email = null;
	var ref2 = firebase.database().ref();
	
	$myScope.initiateList = function(){
		
	$myScope.userEmailHash = md5.createHash("$myScope.email");
	$myScope.groceryItems =  $firebaseArray(ref2.child('items/'+$myScope.userEmailHash));
	
	}

	
	$myScope.findById = function(id){
		var xItem = _.find($myScope.groceryItems, function(entry){return entry.id == id});
		return xItem;
	}

	$myScope.getNewId = function(){
		if($myScope.newId){
			$myScope.newId++;
		}else{
			var maxId = _.max($myScope.groceryItems, function(entry){ return entry.id});
			$myScope.newId = maxId.id + 1;
		}
		console.log("new Id"+$myScope.newId);
		return $myScope.newId;
	}
	$myScope.addItem =function(item){
		
		item.id= $myScope.getNewId();
		console.log("List: "+JSON.stringify(item));
		$myScope.groceryItems.$add(item);
	}

	$myScope.saveItem =function(item){
		var index = $myScope.groceryItems.indexOf(item);
		
		$myScope.groceryItems.$save(index);
	}

	$myScope.removeItem = function(item){
		var index = $myScope.groceryItems.indexOf(item);
		$myScope.groceryItems.$remove(index);
	}

	$myScope.changeChecked = function(item){
		var index = $myScope.groceryItems.indexOf(item);
		item.itemChecked = !item.itemChecked;
		$myScope.groceryItems.$save(index);

	}

	return $myScope;
}]);

app.directive("dGroceryList",function(){
	return{
		restrict: "E",
		templateUrl: "views/itemListView.html"
	};
});


app.controller("AuthCtrl", ["currentAuth","$scope","Auth","$location","$firebaseArray","myService","md5",
	 function(currentAuth,$scope,Auth,$location,$firebaseArray,myService,md5) {
	$scope.username = null;
	$scope.password = null;
	$scope.currentAuth = currentAuth;
	$scope.authObj = Auth;

	$scope.authObj.$onAuthStateChanged(function(firebaseUser) {
	  if (firebaseUser) {
	    $location.path("/");
	  } else {
	    $location.path("/login");
	  }
	});

	$scope.signIn = function(){
		$scope.authObj.$signInWithEmailAndPassword($scope.username,$scope.password)
		.then(function(fireBaseUser){
			myService.email = fireBaseUser.email;
			myService.initiateList();
		})
		.catch(function(error) {
		  $scope.errorMessage = error.message;
		  
		});
	}

	$scope.signInWithGoogle = function(){
		$scope.authObj.$signInWithPopup("google").then(function(firebaseUser) {
		    myService.userEmailHash = md5.createHash($scope.currentAuth.email);
			myService.groceryItems =  $firebaseArray(ref2.child('items/'+myService.userEmailHash));
		    console.log("Signed in as:", firebaseUser.uid);
		  }).catch(function(error) {
		    console.log("Authentication failed:", error);
		  });
	}
}]);

//*****************************AUTHENTICATION FACTORY**************************

app.factory("Auth", ["$firebaseAuth",function($firebaseAuth) {
    return $firebaseAuth();
  }
]);

app.run(["$rootScope", "$location", function($rootScope, $location) {
  
  $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
		$rootScope.animation = currRoute.animation;
	});

  $rootScope.$on("$routeChangeError", function(event, next, previous, error) {
    if (error === "AUTH_REQUIRED") {
      $location.path("/");
    }
  });
}]);