var app = angular.module("app", ["xeditable", "ui.bootstrap"]);

app.run(function (editableOptions) {
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'bs3', 'default'
});

app.controller('Ctrl', function ($scope, $filter, $http) {

    $scope.dayRange = ['d01','d02', 'd03', 'd04', 'd05', 'd06', 'd07', 'd08', 'd09', 'd10', 
    	'd11', 'd12', 'd13', 'd14', 'd15', 'd16', 'd17', 'd18', 'd19','d20', 
    	'd21', 'd22', 'd23', 'd24', 'd25', 'd26', 'd27', 'd28', 'd29', 'd30', 'd31'];

    $scope.timesheet = undefined;
    $scope.statuses = undefined;
    
    // get the list of statuses
    $http({
    	method: "GET",
    	url: "/rest/status/"
    })
    .then(function success(response) {
    	$scope.statuses = response.data;
    }, function error(response) {
    	console.log(response);
    });
    
    // show current month and year
    var d = new Date();
    var m = d.getMonth() + 1;
    var y = d.getFullYear();
    var month = m.toString();
    if (month.length < 2) {
    	month = '0' + month;
    }
    var year = y.toString();
    console.log(month, year);
    $scope.acc_month = month;
    $scope.acc_year = year;
    
    $scope.showTimesheet = function (month, year) {
    	console.log(month, year);
    	$http({
        	method: "GET",
        	url: "/rest/chamcong/" + month + "/" + year
        })
        .then(function success(response) {
        	$scope.timesheet = response.data;
        }, function error(response) {
        	console.log(response);
        }); 
    };
    
    /*
    $scope.statuses = [
        {value: 0, display: '', description: ' '},
        {value: 1, display: 'Ô', description: 'Ốm, điều dưỡng'},
        {value: 2, display: 'Cô', description: 'Con ốm'},
        {value: 3, display: 'T', description: 'Tai nạn'},
        {value: 4, display: 'TS', description: 'Thai sản'},
        {value: 5, display: 'CN', description: 'Chủ nhật'},
        {value: 6, display: 'NL', description: 'Nghỉ lễ'},
        {value: 7, display: 'NB', description: 'Nghỉ bù'},
        {value: 8, display: '1/2K', description: 'Nghỉ nửa ngày không lương'},
        {value: 9, display: 'K', description: 'Nghỉ không lương'},
        {value: 10, display: 'N', description: 'Ngừng việc'},
        {value: 11, display: 'P', description: 'Nghỉ phép'},
        {value: 12, display: '1/2P', description: 'Nghỉ nửa ngày tính phép'},
        {value: 13, display: 'NN', description: 'Làm nửa ngày công'}
    ];
    */

    $scope.showStatus = function (ts, day) {
        var selected = $filter('filter')($scope.statuses, {value: ts[day]});
        return (ts[day] && selected.length) ? selected[0].display : "<>";
    };

    $scope.updateDb = function (ts, day) {
    	var url = "/rest/chamcong?employee_id=" + ts.employee_id 
    		+ "&day=" + day 
    		+ "&newValue=" + ts[day];
    	console.log(url);
        $http({
        	method: "GET",
        	url: url
        })
        .then (function success(response) {
        	console.log(response);
        }, function error(response) {
        	console.log(response);
        }); 
    };
    
    $scope.deleteTimesheet = function (ts) {
    	console.log(ts.employee_id, ts.acc_month, ts.acc_year);
    	var url = "/rest/chamcong/" + ts.employee_id 
    		+ "?month=" + ts.acc_month 
    		+ "&year=" + ts.acc_year;
    	$http({
    		method: "DELETE",
    		url: url
    	})
    	.then (function success(response) {
        	console.log(response);
        	$scope.showTimesheet($scope.acc_month, $scope.acc_year);
        }, function error(response) {
        	console.log(response);
        });
    	
    }
});
