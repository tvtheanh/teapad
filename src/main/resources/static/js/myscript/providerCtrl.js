(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing provider list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListProviderCtrl", ListProviderCtrl);
	
	function ListProviderCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		
		var listProvider = function () { 
			$http({
				method: "GET",
				url: GLOBAL_URL.providerBaseUrl
			})
			.then(function success(response) {
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
			}, function error(response) {
				console.log(response);
			});
		};
		listProvider();
		
		// click event for edit
		$scope.editProvider = function (providerId) {
			$location.path("/edit-provider/" + providerId);
		};
		
		// click event for delete
		$scope.deleteProvider = function (providerId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.providerBaseUrl + providerId
			})
			.then(function success(response) {
				listProvider();
				
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	ListProviderCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new provider
	 */
	angular.module("myapp")
		.controller("AddProviderCtrl", AddProviderCtrl);
	
	function AddProviderCtrl($scope, $http) {
		$scope.provider = undefined;       // model for form submit
		$scope.alert = undefined;       // model for 'uib-alert' directive
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		$scope.submitProviderForm = function () {
			
			$http({
				method: "POST",
				url: GLOBAL_URL.providerBaseUrl,
				data: $scope.provider
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới Nhà cung cấp thành công!" };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		$scope.resetForm = function () {
			$scope.provider = undefined;
		};
	}
	
	AddProviderCtrl.$inject = ["$scope", "$http"];
	
	
	/**
	 * Controller for editing a provider
	 */
	angular.module("myapp")
		.controller("EditProviderCtrl", EditProviderCtrl);
	
	function EditProviderCtrl($scope, $http, $location, $routeParams, $route) {
		$scope.id = $routeParams.id;
		
		$scope.provider = undefined;     // model for form submit
		$scope.alert = undefined;
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		$http({
			method: "GET",
			url: GLOBAL_URL.providerBaseUrl + $scope.id
		})
		.then(function success(response) {
			$scope.provider = response.data;

		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được sản phẩm" };
		});
		
		$scope.submitProviderForm = function () {
			
			$http({
				method: "PUT",
				url: GLOBAL_URL.providerBaseUrl + $scope.id,
				data: $scope.provider
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
	}
	
	EditProviderCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));