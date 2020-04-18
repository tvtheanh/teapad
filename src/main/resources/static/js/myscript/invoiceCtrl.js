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
				url: GLOBAL_URL.invoiceBaseUrl
			})
			.then(function success(response) {
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
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
		
		// ui-bootstrap date picker
		$scope.today = function() {
		    $scope.invoice.saledate = new Date();
		};
		$scope.today();
		
		$scope.dateOptions = {
		    dateDisabled: disabled,
		    formatYear: "yy",
		    maxDate: (new Date()).setDate((new Date()).getDate() + 2),
		    minDate: new Date(),
		    startingDay: 1
		};
		// Disable weekend selection
		function disabled(data) {
		    var date = data.date,
		      	mode = data.mode;
		    return mode === "day" && date.getDay() === 6;
		}
		  
		$scope.popup1 = {
		    opened: false
		};
		
		$scope.open1 = function() {
		    $scope.popup1.opened = true;
		};
		
		$scope.format = "dd/MM/yyyy";
		
		
		// click event for adding invoice
		$scope.submitInvoiceForm = function () {
			
			$scope.invoice.customer_id = $scope.customer.selected.id;
			$scope.invoice.saledate.setTime( $scope.invoice.saledate.getTime() - $scope.invoice.saledate.getTimezoneOffset()*60*1000 );
			
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceBaseUrl,
				data: $scope.invoice
			})
			.then(function success(response) {
				$scope.alert = { message: "Thêm mới thành công! Mã đơn hàng: " + response.data };
				$scope.resetForm();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		};
		
		// click event for adding invoice then changing to adding details
		$scope.addInvoiceDetail = function () {
			$scope.invoice.customer_id = $scope.customer.selected.id;
			$scope.invoice.saledate.setTime( $scope.invoice.saledate.getTime() - $scope.invoice.saledate.getTimezoneOffset()*60*1000 );
			
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceBaseUrl,
				data: $scope.invoice
			})
			.then(function success(response) {
				$location.path("/edit-invoice/" + response.data);
				$route.reload();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		}
		
		// click event for reseting form
		$scope.resetForm = function () {
			$scope.invoice.customer_id = undefined;
		};
		
		$scope.closeAlert = function () {
			$scope.alert = undefined;
		};
		
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
		$scope.saleProducts = [];
		$scope.customers = undefined;
		$scope.products = undefined;
		$scope.prices = undefined;
		$scope.customer = {};        // selected customer
		$scope.product = {};         // selected product
		
		
		// get the invoice by id and its details
		$scope.loadInvoice = function () {
			$http({
				method: "GET",
				url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id
			})
			.then(function success(response) {
				$scope.invoice = response.data;
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được đơn hàng" };
			});
		};
		$scope.loadInvoice();
		
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
		
		
		// get prices for the selected product
		$scope.selectProduct = function ($item) {
			$scope.price = undefined;   // reset value for the dropdown
			$http({
				method: "GET",
				url: GLOBAL_URL.priceBaseUrl + "byproduct?productid=" + $item.id
			})
			.then(function success(response) {
				$scope.prices = response.data;
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Lỗi không lấy được giá của sản phẩm này" };
			});
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
		
		// click event for update invoice information
		$scope.submitInvoiceForm = function () {
			$scope.invoice.customer_id = $scope.customer.selected.id;
			
			$http({
				method: "PUT",
				url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id,
				data: $scope.invoice
			})
			.then(function success(response) {
				$scope.loadInvoice();     
				$scope.customer = {};    // reset the dropdown control
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
				$scope.alert = { message: "Thêm chi tiết đơn hàng thành công!" };
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