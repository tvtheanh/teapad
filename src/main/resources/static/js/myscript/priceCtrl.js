(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing price list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListPriceCtrl", ListPriceCtrl);
	
	function ListPriceCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		
		var listPrice = function () { 
			$http({
				method: "GET",
				url: GLOBAL_URL.priceBaseUrl
			})
			.then(function success(response) {
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
			}, function error(response) {
				console.log(response);
			});
		};
		listPrice();
		
		// click event for edit
		$scope.editPrice = function (priceId) {
			$location.path("/edit-price/" + priceId);
		};
		
		// click event for delete
		$scope.deletePrice = function (priceId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.priceBaseUrl + priceId
			})
			.then(function success(response) {
				listPrice();
				
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	ListPriceCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new price
	 */
	angular.module("myapp")
		.controller("AddPriceCtrl", AddPriceCtrl);
	
	function AddPriceCtrl($scope, $http) {
		$scope.price = undefined;       // model for form submit
		$scope.alert = undefined;       // model for 'uib-alert' directive
		$scope.product = {};			// selected product
		$scope.products = undefined;
		
		// get the product list for dropdown-control
		$http({
			method: "GET",
			url: GLOBAL_URL.productBaseUrl
		})
		.then(function success(response) {
			$scope.products = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		$scope.submitPriceForm = function () {
			$scope.price.product_id = $scope.product.selected.id;
			
			$http({
				method: "POST",
				url: GLOBAL_URL.priceBaseUrl,
				data: $scope.price
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới Giá thành công!" };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		$scope.resetForm = function () {
			$scope.price = undefined;
		};
	}
	
	AddPriceCtrl.$inject = ["$scope", "$http"];
	
	
	/**
	 * Controller for editing a price
	 */
	angular.module("myapp")
		.controller("EditPriceCtrl", EditPriceCtrl);
	
	function EditPriceCtrl($scope, $http, $location, $routeParams, $route) {
		$scope.id = $routeParams.id;
		
		$scope.price = undefined;    // model for form submit
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
			url: GLOBAL_URL.priceBaseUrl + $scope.id
		})
		.then(function success(response) {
			$scope.price = response.data;
			// choose the right selected provider in dropdown-control
			for (let i=0; i<$scope.providers.length; i++) {
				if ($scope.price.provider_id == $scope.providers[i].id) {
					$scope.provider.selected = $scope.providers[i];
					break;
				}
			}
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được sản phẩm" };
		});
		
		$scope.submitPriceForm = function () {
			$scope.price.provider_id = $scope.provider.selected.id;
			
			$http({
				method: "PUT",
				url: GLOBAL_URL.priceBaseUrl + $scope.id,
				data: $scope.price
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
	}
	
	EditPriceCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));