(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing product list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListProductCtrl", ListProductCtrl);
	
	function ListProductCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		
		var listProduct = function () { 
			$http({
				method: "GET",
				url: GLOBAL_URL.productBaseUrl
			})
			.then(function success(response) {
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
			}, function error(response) {
				console.log(response);
			});
		};
		listProduct();
		
		// click event for edit
		$scope.editProduct = function (productId) {
			$location.path("/edit-product/" + productId);
		};
		
		// click event for delete
		$scope.deleteProduct = function (productId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.productBaseUrl + productId
			})
			.then(function success(response) {
				console.log(response);
				listProduct();
				
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	ListProductCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new product
	 */
	angular.module("myapp")
		.controller("AddProductCtrl", AddProductCtrl);
	
	function AddProductCtrl($scope, $http) {
		$scope.product = undefined;    // model for form submit
		$scope.alert = undefined;       // model for 'uib-alert' directive
		$scope.provider = {};			// selected provider
		$scope.providers = undefined;
		
		// get the provider list for dropdown-control
		$http({
			method: "GET",
			url: GLOBAL_URL.providerBaseUrl
		})
		.then(function success(response) {
			$scope.providers = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		$scope.submitProductForm = function () {
			$scope.product.provider_id = $scope.provider.selected.id;
			
			$http({
				method: "POST",
				url: GLOBAL_URL.productBaseUrl,
				data: $scope.product
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới Sản phẩm thành công!" };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		$scope.resetForm = function () {
			$scope.product = undefined;
		};
	}
	
	AddProductCtrl.$inject = ["$scope", "$http"];
	
	
	/**
	 * Controller for editing a product
	 */
	angular.module("myapp")
		.controller("EditProductCtrl", EditProductCtrl);
	
	function EditProductCtrl($scope, $http, $location, $routeParams, $route) {
		$scope.id = $routeParams.id;
		
		$scope.product = undefined;    // model for form submit
		$scope.alert = undefined;
		$scope.provider = {};			// selected provider
		$scope.providers = undefined;
		
		// get the provider list for dropdown-control
		$http({
			method: "GET",
			url: GLOBAL_URL.providerBaseUrl
		})
		.then(function success(response) {
			$scope.providers = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		$http({
			method: "GET",
			url: GLOBAL_URL.productBaseUrl + $scope.id
		})
		.then(function success(response) {
			$scope.product = response.data;
			// choose the right selected provider in dropdown-control
			for (let i=0; i<$scope.providers.length; i++) {
				if ($scope.product.provider_id == $scope.providers[i].id) {
					$scope.provider.selected = $scope.providers[i];
					break;
				}
			}
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được sản phẩm" };
		});
		
		$scope.submitProductForm = function () {
			$scope.product.provider_id = $scope.provider.selected.id;
			
			$http({
				method: "PUT",
				url: GLOBAL_URL.productBaseUrl + $scope.id,
				data: $scope.product
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
	}
	
	EditProductCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));