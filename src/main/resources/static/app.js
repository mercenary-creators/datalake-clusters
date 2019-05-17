var app = angular.module("lakeApplication", []);
app.controller("lakeController", function($scope, $http) {
	$scope.tstatus = "[READY]";
	$scope.results = "";
	$scope.working = false;
	$scope.timestamp = function() {
		if ((performance) && (performance.now)) {
			return performance.now();
		}
		if (!Date.now) {
			return new Date().getTime();
		}
		return Date.now();
	};
	$scope.putJSON = function(data) {
		var json = JSON.stringify(data);
		sessionStorage.setItem('json', json);
		return data;
	};
	$scope.getJSON = function() {
		var json = sessionStorage.getItem('json');
		return JSON.parse(json);
	};
	$scope.statusREADY = function(path, response) {
		var dt = ($scope.timestamp() - $scope.tstarts);
		$scope.tstatus = "[READY] " + path + " " + dt.toFixed(3) + ".ms";
		$scope.results = JSON.stringify($scope.putJSON(response.data), null, 2);
		$scope.working = false;
	};
	$scope.statusERROR = function(path, response) {
		var dt = ($scope.timestamp() - $scope.tstarts);
		$scope.tstatus = "[ERROR] " + path + " " + dt.toFixed(3) + ".ms";
		var text = "";
		if (response.status < 0) {
			text = "NO NETWORK";
		} else {
			text = "CODE[" + response.status + "][" + response.statusText + "]";
		}
		$scope.results = " " + text;
		$scope.putJSON({oops: text});
		$scope.working = false;
	};
	$scope.doServiceGET = function(path) {
		$scope.working = true;
		$scope.tstatus = "[WAITING]";
		$scope.results = "";
		$scope.tstarts = $scope.timestamp();
		$http.get(path).then(function(response) {
			$scope.statusREADY(path, response);
		}, function(response) {
			$scope.statusERROR(path, response);
		});
	};
	$scope.doServicePOST = function(path) {
		$scope.working = true;
		$scope.tstatus = "[WAITING]";
		$scope.results = "";
		$scope.tstarts = $scope.timestamp();
		$http.post(path, $scope.getJSON()).then(function(response) {
			$scope.statusREADY(path, response);
		}, function(response) {
			$scope.statusERROR(path, response);
		});
	};
	$scope.doServiceGET('/open/clusters');
});