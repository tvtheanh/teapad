(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing employee list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListEmployeeCtrl", ListEmployeeCtrl);
	
	function ListEmployeeCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		
		var listEmployee = function () { 
			$http({
				method: "GET",
				url: GLOBAL_URL.employeeBaseUrl
			})
			.then(function success(response) {
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
			}, function error(response) {
				console.log(response);
			});
		};
		listEmployee();
		
		// click event for edit
		$scope.editEmployee = function (employeeId) {
			$location.path("/edit-employee/" + employeeId);
		};
		
		// click event for delete
		$scope.deleteEmployee = function (employeeId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.employeeBaseUrl + employeeId
			})
			.then(function success(response) {
				console.log(response);
				listEmployee();
				
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	ListEmployeeCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new employee
	 */
	angular.module("myapp")
		.controller("AddEmployeeCtrl", AddEmployeeCtrl);
	
	function AddEmployeeCtrl($scope, $http) {
		$scope.employee = undefined;   // model for form submit
		$scope.alert = undefined;      // model for 'uib-alert' directive
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		$scope.submitEmployeeForm = function () {
			$http({
				method: "POST",
				url: GLOBAL_URL.employeeBaseUrl,
				data: $scope.employee
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới Nhân viên thành công!" };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		$scope.resetForm = function () {
			$scope.employee = undefined;
		};
	}
	
	AddEmployeeCtrl.$inject = ["$scope", "$http"];
	
	
	/**
	 * Controller for editing a customer
	 */
	angular.module("myapp")
		.controller("EditEmployeeCtrl", EditEmployeeCtrl);
	
	function EditEmployeeCtrl($scope, $http, $location, $routeParams, $route) {
		
		$scope.id = $routeParams.id;
		$scope.alert = undefined;
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		$http({
			method: "GET",
			url: GLOBAL_URL.employeeBaseUrl + $scope.id
		})
		.then(function success(response) {
			$scope.employee = response.data;
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được nhân viên" };
		});
		
		$scope.submitEmployeeForm = function () {
			$http({
				method: "PUT",
				url: GLOBAL_URL.employeeBaseUrl + $scope.id,
				data: $scope.employee
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
	}
	
	EditEmployeeCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));