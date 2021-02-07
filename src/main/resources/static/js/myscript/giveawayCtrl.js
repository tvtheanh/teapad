(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing giveaway list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListGiveawayCtrl", ListGiveawayCtrl);
	
	function ListGiveawayCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		var dataset = undefined;
		
		var listGiveaway = function () {
			$http({
				method: "GET",
				url: GLOBAL_URL.giveawayBaseUrl
			})
			.then(function success(response) {
				dataset = response.data;
				self.tableParams = new NgTableParams({}, { dataset: dataset });
				self.countAll = dataset.length;
				
			}, function error(response) {
				console.log(response);
			});
		};
		listGiveaway();
		
		
		// click event for edit
		$scope.editGiveaway = function (giveawayId) {
			$location.path("/edit-giveaway/" + giveawayId);
		};
		
		// click event for delete
		$scope.deleteGiveaway = function (giveawayId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.giveawayBaseUrl + giveawayId
			})
			.then(function success(response) {
				listGiveaway();
				
			}, function error(response) {
				console.log(response);
			});
		};
	}
	
	ListGiveawayCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new giveaway
	 */
	angular.module("myapp")
		.controller("AddGiveawayCtrl", AddGiveawayCtrl);
	
	function AddGiveawayCtrl($scope, $http) {
		$scope.giveaway = undefined;   // model for form submit
		$scope.alert = undefined;      // model for 'uib-alert' directive
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		$scope.submitGiveawayForm = function () {
			console.log($scope.giveaway);
			$http({
				method: "POST",
				url: GLOBAL_URL.giveawayBaseUrl,
				data: $scope.giveaway
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới Chương trình tặng phẩm thành công!" };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		$scope.resetForm = function () {
			$scope.giveaway = undefined;
		};
	}
	
	AddGiveawayCtrl.$inject = ["$scope", "$http"];
	
	
	/**
	 * Controller for editing a giveaway
	 */
	angular.module("myapp")
		.controller("EditGiveawayCtrl", EditGiveawayCtrl);
	
	function EditGiveawayCtrl($scope, $http, $location, $routeParams, $route) {
		
		$scope.id = $routeParams.id;
		$scope.alert = undefined;
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		$http({
			method: "GET",
			url: GLOBAL_URL.giveawayBaseUrl + $scope.id
		})
		.then(function success(response) {
			$scope.giveaway = response.data;
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được chương trình tặng phẩm" };
		});
		
		$scope.submitGiveawayForm = function () {
			$http({
				method: "PUT",
				url: GLOBAL_URL.giveawayBaseUrl + $scope.id,
				data: $scope.giveaway
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
	}
	
	EditGiveawayCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));