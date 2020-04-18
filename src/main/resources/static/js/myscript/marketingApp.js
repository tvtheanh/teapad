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
	get providerBaseUrl() {
		return "rest/provider/";
	},
	get invoiceBaseUrl() {
		return "rest/invoice/";
	},
	get invoiceDetailBaseUrl() {
		return "rest/invoicedetail/";
	},
	get invoiceTemplatePath() {
		return "template/marketing/";
	},
};

var INVOICES = [];

(function (angular) {
	"use strict";
	
	/**
	 * init the root module
	 */ 
	angular.module("myapp", ["ngRoute", "ngResource", "ngTable", "ngSanitize", "ui.select", "ui.bootstrap"]);
	

	/**
	 * config for routing
	 */
	angular.module("myapp")
		.config(["$routeProvider", function ($routeProvider) {
	
			$routeProvider
				.when("/marketing-list-invoice", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "list-invoice.html",
					controller: "ListInvoiceCtrl",
					controllerAs: "vm"
				})
				.when("/marketing-add-invoice", {
					templateUrl: GLOBAL_URL.invoiceTemplatePath + "add-invoice.html",
					controller: "AddInvoiceCtrl"
				})
				.when("/marketing-edit-invoice/:id", {
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
