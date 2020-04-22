var GLOBAL_URL = {
	get customerBaseUrl() {
		return "rest/customer/";
	},
	get employeeBaseUrl() {
		return "rest/employee/";
	},
	get productBaseUrl() {
		return "rest/product/";
	},
	get priceBaseUrl() {
		return "rest/price/";
	},
	get providerBaseUrl() {
		return "rest/provider/";
	},
	get invoiceBaseUrl() {
		return "rest/invoice/";
	},
	get invoiceDetailBaseUrl() {
		return "rest/invoicedetail/";
	},
	get customerTemplatePath() {
		return "template/customer/";
	},
	get employeeTemplatePath() {
		return "template/employee/";
	},
	get productTemplatePath() {
		return "template/product/";
	},
	get priceTemplatePath() {
		return "template/price/";
	},
	get providerTemplatePath() {
		return "template/provider/";
	},
	get invoiceTemplatePath() {
		return "template/invoice/";
	},
	get invoiceDetailTemplatePath() {
		return "template/invoicedetail/";
	}
};


(function (angular) {
	"use strict";
	
	/**
	 * init the root module
	 */ 
	angular.module("myapp", ["ngRoute", "ngResource", "ngTable", "ngSanitize", "ui.select", "ui.bootstrap"]);
	
	// controller for the menu navigation
	angular.module("myapp")
	.controller("menuController", 
			["$scope", "$location", "$route", 
				function ($scope, $location, $route) {
				
		$scope.selectedView = "home";
		$scope.content = "";
		$scope.menulist = [
			{view: "home", name: ""},
			{view: "provider", name: "Nhà cung cấp"},
			{view: "customer", name: "Khách hàng"},
			{view: "product", name: "Sản phẩm"},
			{view: "price", name: "Giá cả"},
			{view: "invoice", name: "Đơn hàng"},
			{view: "employee", name: "Nhân sự"}
		];
		
		$scope.changeview = function (menu) {
			$scope.selectedView = menu.view;
			$scope.content = $scope.selectedView + ".html";
			$location.path("/home-" + $scope.selectedView);
			$route.reload();
		}
		
	}]);
	

	/**
	 * config for routing
	 */
	angular.module("myapp")
		.config(["$routeProvider", function ($routeProvider) {
	
			$routeProvider
				.when("/home-provider", {
					templateUrl: GLOBAL_URL.providerTemplatePath + "home-provider.html"
				})
				.when("/list-provider", {
					templateUrl: GLOBAL_URL.providerTemplatePath + "list-provider.html",
					controller: "ListProviderCtrl",
					controllerAs: "vm"
				})
				.when("/add-provider", {
					templateUrl: GLOBAL_URL.providerTemplatePath + "add-provider.html",
					controller: "AddProviderCtrl"
				})
				.when("/edit-provider/:id", {
					templateUrl: GLOBAL_URL.providerTemplatePath + "edit-provider.html",
					controller: "EditProviderCtrl"
				})
				.when("/home-customer", {
					templateUrl: GLOBAL_URL.customerTemplatePath + "home-customer.html"
				})
				.when("/list-customer", {
					templateUrl: GLOBAL_URL.customerTemplatePath + "list-customer.html",
					controller: "ListCustomerCtrl",
					controllerAs: "vm"
				})
				.when("/add-customer", {
					templateUrl: GLOBAL_URL.customerTemplatePath + "add-customer.html",
					controller: "AddCustomerCtrl"
				})
				.when("/edit-customer/:id", {
					templateUrl: GLOBAL_URL.customerTemplatePath + "edit-customer.html",
					controller: "EditCustomerCtrl"
				})
				.when("/home-employee", {
					templateUrl: GLOBAL_URL.employeeTemplatePath + "home-employee.html"
				})
				.when("/list-employee", {
					templateUrl: GLOBAL_URL.employeeTemplatePath + "list-employee.html",
					controller: "ListEmployeeCtrl",
					controllerAs: "vm"
				})
				.when("/add-employee", {
					templateUrl: GLOBAL_URL.employeeTemplatePath + "add-employee.html",
					controller: "AddEmployeeCtrl"
				})
				.when("/edit-employee/:id", {
					templateUrl: GLOBAL_URL.employeeTemplatePath + "edit-employee.html",
					controller: "EditEmployeeCtrl"
				})
				.when("/home-product", {
					templateUrl: GLOBAL_URL.productTemplatePath + "home-product.html"
				})
				.when("/list-product", {
					templateUrl: GLOBAL_URL.productTemplatePath + "list-product.html",
					controller: "ListProductCtrl",
					controllerAs: "vm"
				})
				.when("/add-product", {
					templateUrl: GLOBAL_URL.productTemplatePath + "add-product.html",
					controller: "AddProductCtrl"
				})
				.when("/edit-product/:id", {
					templateUrl: GLOBAL_URL.productTemplatePath + "edit-product.html",
					controller: "EditProductCtrl"
				})
				.when("/home-price", {
					templateUrl: GLOBAL_URL.priceTemplatePath + "home-price.html"
				})
				.when("/list-price", {
					templateUrl: GLOBAL_URL.priceTemplatePath + "list-price.html",
					controller: "ListPriceCtrl",
					controllerAs: "vm"
				})
				.when("/add-price", {
					templateUrl: GLOBAL_URL.priceTemplatePath + "add-price.html",
					controller: "AddPriceCtrl"
				})
				.when("/edit-price/:id", {
					templateUrl: GLOBAL_URL.priceTemplatePath + "edit-price.html",
					controller: "EditPriceCtrl"
				})
				.when("/home-invoice", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "home-invoice.html"
				})
				.when("/list-invoice", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "list-invoice.html",
					controller: "ListInvoiceCtrl",
					controllerAs: "vm"
				})
				.when("/add-invoice", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "add-invoice.html",
					controller: "AddInvoiceCtrl"
				})
				.when("/edit-invoice/:id", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "edit-invoice.html",
					controller: "EditInvoiceCtrl"
				});
	
		}]);
	
	
	/**
	 * filter for ui-select
	 * 
	 * AngularJS ui-select default filter with the following expression:
	 * "person in people | filter: {name: $select.search, age: $select.search}"
	 * performs a AND between "name: $select.search" and "age: $select.search".
	 * 
	 * We want customize propsFilter to perform an OR.
	 */
	angular.module("myapp")
		.filter("propsFilter", function () {
			return function (items, props) {
				var out = [],
					keys = Object.keys(props);
				
				if (angular.isArray(items)) {
					items.forEach(function (item) {
						var itemMatches = false;
						for (var i = 0; i < keys.length; i++) {
							var prop = keys[i];
							var textSearch = props[prop].toLowerCase();
							if (item[prop].toString().toLowerCase().indexOf(textSearch) !== -1) {
								itemMatches = true;
								break;
							}
						}
						if (itemMatches) {
							out.push(item);
						}
					});
				} else {
					out = items;
				}
				
				return out;
			};
		});
	
	
	/**
	 * directive for replacing Enter by Tab
	 * To be used in form as an attribute
	 */
	angular.module("myapp")
		.directive("enterToTab", function () {
			
			function link(scope, element, attrs) {
				
				// Enter is replaced by Tab
				element.on('keydown', function (event) {
				  if (event.keyCode === 13 && event.target.nodeName === 'INPUT') {
				    var form = event.target.form;
				    var index = Array.prototype.indexOf.call(form, event.target);
				    form.elements[index + 1].focus();
				    event.preventDefault();
				  }
				});
				
			}
			
			return {
				link: link
			};
			
		});

}(angular));
