"use strict";

angular.module("myapp")
.controller("addkiemkeController", function ($scope, $http, $location, $route) {
	
	$scope.ccdc = {};     // the selected ccdc in combobox 
	$scope.ngayketoan;
	$scope.ngaykiemke;
	$scope.dmccdc = [];
	$scope.kiemke = {};
	
	// ui-bootstrap
	$scope.today = function() {
	    $scope.dt = new Date();
	};
	$scope.today();
	
	$scope.dateOptions = {
	    dateDisabled: disabled,
	    formatYear: "yy",
	    maxDate: new Date(2020, 5, 30),
	    minDate: new Date(),
	    startingDay: 1
	};
	// Disable weekend selection
	function disabled(data) {
	    var date = data.date,
	      	mode = data.mode;
	    return mode === "day" && (date.getDay() === 0 || date.getDay() === 6);
	}
	  
	$scope.popup1 = {
	    opened: false
	};
	
	$scope.open1 = function() {
	    $scope.popup1.opened = true;
	};
	
	$scope.popup2 = {
		opened: false
	};
	
	$scope.open2 = function() {
	    $scope.popup2.opened = true;
	};
	
	$scope.format = "dd/MM/yyyy";
	
	// get all ccdc[maccdc, tenccdc] for the combobox
	$http({
		method: "GET",
		url: GLOBAL_URL.ccdcMatenUrl
	}).then(function success(response) {
		$scope.dmccdc = response.data;
		
	});
	
	console.log($scope.dmccdc);   // null because of async?
	
	$scope.submitKiemkeForm = function () {
		$scope.kiemke.maccdc = $scope.ccdc.selected.maccdc;
		$scope.kiemke.ngayketoan = $scope.ngayketoan.toLocaleDateString("en-GB");
		$scope.kiemke.ngaykiemke = $scope.ngaykiemke.toLocaleDateString("en-GB");
		
		console.log($scope.kiemke);
		return;   // test only
		
		$http({
			method: "POST",
			url: GLOBAL_URL.kiemkeBaseUrl,
			data: $scope.kiemke
		}).then(function success(response) {
			$location.path("/list-kiemke");
			$route.reload();
		}, function error(response) {
			$scope.errorMessage = "Error: " + response.status;
		});
	};
	
	$scope.resetForm = function() {
		$scope.kiemke = null;
	};
	
});


angular.module("myapp")
.controller("listkiemkeController", function ($scope, $http, $location, NgTableParams) {
	var self = this;
	$http({
		method: "GET",
		url: GLOBAL_URL.kiemkeBaseUrl
	}).then(function success(response) {
		$scope.dskiemke = response.data;
		self.tableParams = new NgTableParams({}, { dataset: response.data});
	});
	
	// click event for Edit button
	$scope.editkiemke = function(userId) {
		$location.path("/update-kiemke/" + userId);
	}
});


angular.module("myapp")
.controller("updatekiemkeController", function ($scope, $http, $location, $routeParams, $route) {
	$route.makiemke = $routeParams.id;
	
	$http({
		method: "GET",
		url: GLOBAL_URL.kiemkeBaseUrl + $route.makiemke
	}).then(function success(response) {
		$scope.kiemke = response.data;
	});
	
	$scope.submitUserForm = function () {
		$http({
			method: "PUT",
			url: GLOBAL_URL.kiemkeBaseUrl + $route.makiemke,
			data: $scope.kiemke
		}).then(function success(response) {
			$location.path("/list-kiemke");
			$route.reload();
		}, function error(response) {
			$scope.errorMessage = "Error: " + response.status;
		});
	}
});