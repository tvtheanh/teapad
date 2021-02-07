(function (angular) {
	"use strict";
	
	/**
	 * Controller for showing employee list in a table (with the help of ngTable)
	 */
	angular.module("myapp")
		.controller("ListInvoiceCtrl", ListInvoiceCtrl);
	
	function ListInvoiceCtrl($scope, $http, $location, NgTableParams) {
		
		// ui-bootstrap date picker
		$scope.today = function() {
		    $scope.fromdate = new Date();
		    $scope.tilldate = new Date();
		};
		$scope.today();
		
		$scope.dateOptions = {
		    /*dateDisabled: disabled,
		    formatYear: "yy",
		    maxDate: (new Date()).setDate((new Date()).getDate() + 2),
		    minDate: new Date(), */
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
		
		$scope.popup2 = {
		    opened: false
		};
		
		$scope.open2 = function() {
		    $scope.popup2.opened = true;
		};
		
		$scope.format = "dd/MM/yyyy";
		
		// list all invoices
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
		
		
		$scope.searchInvoice = function () { 
			var querydate= {
					fromdate: $scope.fromdate, 
					tilldate: $scope.tilldate
			};
			
			$http({
				method: "POST",
				url: GLOBAL_URL.searchBaseUrl + "invoice",
				data: querydate
			})
			.then(function success(response) {
				console.log(querydate);
				self.tableParams = new NgTableParams({}, { dataset: response.data });
				self.countAll = response.data.length;
			}, function error(response) {
				console.log(querydate);
				console.log(response);
			});
		};
		
		// click event for edit detail of invoice
		$scope.editDetailInvoice = function (invoiceId) {
			$location.path("/edit-detail-invoice/" + invoiceId);
		};
		
		// click event for edit invoice
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
		$scope.invoice.discount = 0;
		$scope.alert = undefined;       // model for 'uib-alert' directive
		// for loading combobox customer
		$scope.customer = {};
		$scope.customers = undefined;
		// for loading combobox giveaway
		$scope.giveaway = {};
		$scope.giveaways = undefined;
		
		
		// click event for adding invoice
		$scope.submitInvoiceForm = function () {
			
			$scope.invoice.customer_id = $scope.customer.selected.id;
			$scope.invoice.giveaway_id = $scope.giveaway.selected.id;
			
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
			$scope.invoice.giveaway_id = $scope.giveaway.selected.id;
			
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceBaseUrl,
				data: $scope.invoice
			})
			.then(function success(response) {
				$location.path("/edit-detail-invoice/" + response.data);
				$route.reload();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		}
		
		// click event for reseting form
		$scope.resetForm = function () {
			$scope.invoice.customer_id = undefined;
			$scope.invoice.giveaway_id = undefined;
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
		}, function error(response) {
			console.log(response);
		});
		
		// get the giveaway list
		$http({
			method: "GET",
			url: GLOBAL_URL.giveawayBaseUrl
		})
		.then(function success(response) {
			$scope.giveaways = response.data;
			console.log(response);
		}, function error(response) {
			console.log(response);
		});
		
	}
	
	AddInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$route"];
	
	
	/**
	 * Controller for adding customer invoice
	 */
	angular.module("myapp")
		.controller("AddCustomerInvoiceCtrl", AddCustomerInvoiceCtrl);
	
	function AddCustomerInvoiceCtrl($scope, $http, $location, $routeParams, $route) {
		$scope.invoice = {};            // model for form submit
		$scope.invoice.customer_id = $routeParams.id;
		$scope.invoice.discount = 0;
		$scope.alert = undefined;       // model for 'uib-alert' directive
		// for loading combobox giveaway
		$scope.giveaway = {};
		$scope.giveaways = undefined;
		
		// get the selected customer
		$http({
			method: "GET",
			url: GLOBAL_URL.customerBaseUrl + $scope.invoice.customer_id
		})
		.then(function success(response) {
			$scope.customer = response.data;
			$scope.invoice.customerName = $scope.customer.name;
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được khách hàng" };
		});
		
		
		// click event for adding invoice
		$scope.submitInvoiceForm = function () {
			$scope.invoice.giveaway_id = $scope.giveaway.selected.id;
			
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
			$scope.invoice.giveaway_id = $scope.giveaway.selected.id;
			
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceBaseUrl,
				data: $scope.invoice
			})
			.then(function success(response) {
				$location.path("/edit-detail-invoice/" + response.data);
				$route.reload();
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error: " + response.status + ") Thêm mới thất bại" };
			});
		}
		
		// click event for reseting form
		$scope.resetForm = function () {
			$scope.invoice.customer_id = undefined;
			$scope.invoice.giveaway_id = undefined;
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
		}, function error(response) {
			console.log(response);
		});
		
		// get the giveaway list
		$http({
			method: "GET",
			url: GLOBAL_URL.giveawayBaseUrl
		})
		.then(function success(response) {
			$scope.giveaways = response.data;
			console.log(response);
		}, function error(response) {
			console.log(response);
		});
	}
	
	AddCustomerInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
	
	/**
	 * Controller for editing an invoice
	 */
	angular.module("myapp")
		.controller("EditInvoiceCtrl", EditInvoiceCtrl);
	
	function EditInvoiceCtrl($scope, $http, $location, $routeParams, $route) {
		$scope.invoice_id = $routeParams.id;
		
		$scope.invoice = {};            // model for form submit
		$scope.alert = undefined;       // model for 'uib-alert' directive
		// for loading combobox giveaway
		$scope.giveaway = {};
		$scope.giveaways = undefined;
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
		
		// get the giveaway list
		$http({
			method: "GET",
			url: GLOBAL_URL.giveawayBaseUrl
		})
		.then(function success(response) {
			$scope.giveaways = response.data;
			console.log(response);
		}, function error(response) {
			console.log(response);
		});
		
		$http({
			method: "GET",
			url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id
		})
		.then(function success(response) {
			$scope.invoice = response.data;
			// choose the right selected giveaway in dropdown-control
			for (let i=0; i<$scope.giveaways.length; i++) {
				if ($scope.invoice.giveaway_id == $scope.giveaways[i].id) {
					$scope.giveaway.selected = $scope.giveaways[i];
					break;
				}
			}
		}, function error(response) {
			$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được khách hàng" };
		});
		
		$scope.submitEditInvoice = function () {
			$scope.invoice.giveaway_id = $scope.giveaway.selected.id;
			
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
		
		// click event for changing to adding details
		$scope.addInvoiceDetail = function () {
			$location.path("/edit-detail-invoice/" + $scope.invoice_id);
		}
	}
	
	EditInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
	
	
	/**
	 * Controller for editing an invoice, adding invoice details
	 */
	angular.module("myapp")
		.controller("EditDetailInvoiceCtrl", EditDetailInvoiceCtrl);
	
	function EditDetailInvoiceCtrl($scope, $http, $location, $routeParams, $route) {
		
		$scope.invoice_id = $routeParams.id;
		
		// click event for edit invoice
		$scope.editInvoice = function () {
			$location.path("/edit-invoice/" + $scope.invoice_id);
		};
		
		$scope.alert = undefined;
		$scope.saleProducts = [];
		$scope.invoiceTotal = undefined;
		$scope.customers = undefined;
		$scope.products = undefined;
		$scope.prices = undefined;
		$scope.customer = {};        // selected customer
		$scope.product = {};         // selected product
		$scope.selectPrice = {};         // selected price
		
		
		// get the invoice by id and its details
		var loadInvoice = function () {
			$http({
				method: "GET",
				url: GLOBAL_URL.invoiceBaseUrl + $scope.invoice_id
			})
			.then(function success(response) {
				$scope.invoice = response.data;
				$scope.currentDebt = response.data.debt;
			}, function error(response) {
				$scope.alert = { errorMessage: "(Error " + response.status + ") Không tìm được đơn hàng" };
			});
		};
		loadInvoice();
		
		// get all invoice-details of this invoice
		$scope.showDetail = function () {
			$http({
				method: "GET",
				url: GLOBAL_URL.invoiceDetailBaseUrl + "byinvoice?invoiceid=" + $scope.invoice_id
			})
			.then(function success(response) {
				$scope.saleProducts = response.data;
				loadInvoice();
				
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
				
		
		$scope.closeAlert = function() {
			$scope.alert = undefined;
		};
	
		
		$scope.addProductToList = function (product) {
			$http({
				method: "POST",
				url: GLOBAL_URL.invoiceDetailBaseUrl,
				data: product
			})
			.then(function success(response) {
				$scope.showDetail();
				$scope.product.selected = undefined;
				$scope.selectPrice.selected = undefined;
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
	
	EditDetailInvoiceCtrl.$inject = ["$scope", "$http", "$location", "$routeParams", "$route"];
	
}(angular));