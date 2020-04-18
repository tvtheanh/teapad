(function (angular) {
	"use strict";
		
	/**
	 * Controller for showing employee list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListInvoiceCtrl", ListInvoiceCtrl);
	
	function ListInvoiceCtrl($scope, $http, $location, NgTableParams) {
		var self = this;
		
		var listInvoice = function () { 
			$http({
				method: "GET",
				url: "rest/offinvoices/"
			})
			.then(function success(response) {
				INVOICES = response.data;
				self.tableParams = new NgTableParams({}, { dataset: INVOICES });
				self.countAll = INVOICES.length;
			}, function error(response) {
				console.log(response);
			});
		};
		listInvoice();
		
		// click event for edit
		$scope.editInvoice = function (invoiceId) {
			$location.path("/edit-invoice/" + invoiceId);
		};
		
		// click event for delete invoice
		$scope.deleteInvoice = function (invoiceId) {
			$http({
				method: "DELETE",
				url: GLOBAL_URL.invoiceBaseUrl + invoiceId
			})
			.then(function success(response) {
				listInvoice();
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	ListInvoiceCtrl.$inject = ["$scope", "$http", "$location", "NgTableParams"];
	
	
	/**
	 * Controller for adding new invoice
	 */
	angular.module("myapp")
		.controller("AddInvoiceCtrl", AddInvoiceCtrl);
	
	function AddInvoiceCtrl($scope, $http, $location, $route) {
		$scope.invoice = {};            // model for form submit
		$scope.alert = undefined;       // model for 'uib-alert' directive
		$scope.customer = {};
		$scope.customers = undefined;
		$scope.details = [];
		$scope.product = {};
		
		// get the customer list
		$http({
			method: "GET",
			url: GLOBAL_URL.customerBaseUrl
		})
		.then(function success(response) {
			$scope.customers = response.data;
			console.log(response);
		}, function error(response) {
			console.log(response);
		});
		
		// get the product list
		$http({
			method: "GET", 
			url: GLOBAL_URL.productBaseUrl
		})
		.then(function success(response) {
			$scope.products = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		
		// click event for s invoice
		$scope.submitInvoiceForm = function () {
			$scope.invoice.id = INVOICES.length;
			$scope.invoice.customer_id = $scope.customer.selected.id;
			$scope.invoice.customer_name = $scope.customer.selected.name;
			
			INVOICES.push($scope.invoice);
			console.log(INVOICES);
			
			caches.open(cacheName)
				.then(function (cache) {
					let dataHeaders = { "status": 200, headers: { "Content-Type": "application/json" } };
					cache.put('rest/offinvoices/', 
							new Response(JSON.stringify(INVOICES), dataHeaders));
				});
		};
		
		// click event for reseting form
		$scope.resetForm = function () {
			$scope.invoice.customer_id = undefined;
		};
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
		
		$scope.addProductToList = function (product) {
			console.log(product);
			$scope.details.push(product);
		};
		
		$scope.deleteDetail = function (detailId) {
			$scope.details.splice(detailId, 1);
			for (var i = detailId; i < $scope.details.length; i++) {
				$scope.details[i].id = $scope.details[i].id - 1; 
			}
		};
		
	}
	
	AddInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$route"];
	
	
	/**
	 * Controller for editing an invoice, adding invoice details
	 */
	angular.module("myapp")
		.controller("EditInvoiceCtrl", EditInvoiceCtrl);
	
	function EditInvoiceCtrl($scope, $http, $location, $routeParams, $route) {
		
		$scope.invoice_id = $routeParams.id;
		$scope.alert = undefined;
		$scope.customers = undefined;
		$scope.saleProducts = [];
		$scope.product = {};
		
		
		// get the invoice by id and its details
		$http({
			method: "GET",
			url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id
		})
		.then(function success(response) {
			$scope.invoice = response.data;
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được đơn hàng" };
		});
		
		$scope.showDetail = function () {
			$http({
				method: "GET",
				url: GLOBAL_URL.invoiceDetailBaseUrl + "byinvoice?invoiceid=" + $scope.invoice_id
			})
			.then(function success(response) {
				$scope.saleProducts = response.data;
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Lỗi không lấy được chi tiết đơn hàng" };
			});
		};
		$scope.showDetail();
		
		
		// get the customer list
		$http({
			method: "GET",
			url: GLOBAL_URL.customerBaseUrl
		})
		.then(function success(response) {
			$scope.customers = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		
		// get the product list
		$http({
			method: "GET", 
			url: GLOBAL_URL.productBaseUrl
		})
		.then(function success(response) {
			$scope.products = response.data;
		}, function error(response) {
			console.log(response);
		});
		
		
		$scope.onSelected = function (selectedItem) {
			console.log(selectedItem.id);
		}
		
		// ui-bootstrap date picker
		$scope.today = function() {
			$scope.saledate = new Date();
		};
		$scope.today();
		
		$scope.dateOptions = {
		    dateDisabled: disabled,
		    formatYear: "yy",
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
		
		$scope.format = "dd/MM/yyyy";
		
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		$scope.submitInvoiceForm = function () {
			$http({
				method: "PUT",
				url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id,
				data: $scope.invoice
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Cập nhật thất bại" };
			});
		};
		
		$scope.addProductToList = function (product) {
			console.log(product);
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceDetailBaseUrl,
				data: product
			})
			.then(function success(response) {
				$scope.showDetail();
				$scope.product.selected = undefined;
				$scope.price = undefined;
				$scope.quantity = undefined;
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Lỗi không thêm được chi tiết đơn hàng" };
			});
		};
		
		$scope.deleteDetail = function (detailId) {
			console.log(detailId);
			$http({
				method: "DELETE",
				url: GLOBAL_URL.invoiceDetailBaseUrl + detailId
			})
			.then(function success(response) {
				$scope.alert = { message: "Cập nhật thành công!" };
				$scope.showDetail();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Lỗi không xóa được chi tiết đơn hàng" };
			});
		};
		
	}
	
	EditInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));