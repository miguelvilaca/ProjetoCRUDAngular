(function() {
	
	var app = angular.module("projetoCRUDAngular", ['angularUtils.directives.dirPagination']);
	
	app.controller("PeopleHttpCtrl", function($scope, $http) {
		var app = this;
		$scope.title = 'People Registration';
		$scope.id = null;
		$scope.isUpdate = null;
		
		$scope.people = [];
		$http.get("/projetoCRUDAngular/api/people/").success(function(response){
			$scope.people = response;
		});
		
		$scope.sort = function(keyname){
			$scope.sortKey = keyname;
			$scope.reverse = !$scope.reverse;
		}
		
		$scope.edit = function(id) {
			$scope.clearForm();
			var response = $http.get('/projetoCRUDAngular/api/people/'+ id );
			$scope.id = id;
			$scope.isUpdate = true;
			
			response.success(function(data) {
				$scope.person = data;
				$scope.isError = false;
				$('#modal-cadastro').modal('show');
				$scope.btNew = false;
		    })

			response.error(function(data, status, headers, config) {
				$scope.modalFeedBack = "Error to edit a Person. " + status;
				$scope.isError = true;
				$('#modal-cadastro').modal('hide');
				$('#modal-feedBack').modal('show');
			})
		};
		
		$scope.cancel = function(id){
			$(id).modal('hide');
			$scope.id = null;
		}
		
		$scope.clearForm = function() {
			$scope.person = {
					name:'',
					cpf:'',
					age:''
			};
			$scope.id = null;
		}
		
		$scope.add = function() {
			$scope.clearForm();
			$('#modal-cadastro').modal('show');
			$scope.btNew = true;
			$scope.isUpdate = false;
		}
		
		$scope.exclusion = function(id){
			$scope.id = id;
			$('#modal-exclusao').modal('show');
		}
		
		$scope.save = function() {
			$scope.jsonObj = angular.toJson($scope.person, false);
			if ($scope.jsonObj != null){
				if ($scope.id != null){
					var response = $http.put('/projetoCRUDAngular/api/people/' + $scope.id, $scope.jsonObj);
					response.success(function(data, status, headers, config) {
						$scope.resetSearch();
						$scope.modalFeedBack = "Person Updated with Success";
						$scope.isError = false;
						$('#modal-cadastro').modal('hide');
						$('#modal-feedBack').modal('show');
						$scope.id = null;
				    });
					
					
					response.error(function(data, status, headers, config) {
						$scope.modalFeedBack = "Error to update a Person. " + status;
						$scope.isError = true;
						$('#modal-cadastro').modal('hide');
						$('#modal-feedBack').modal('show');
					})
				}else{
					var response = $http.post('/projetoCRUDAngular/api/people/add', $scope.jsonObj);
					response.success(function(data, status, headers, config) {
						$scope.resetSearch();
						$scope.modalFeedBack = "People Registrated with Success";
						$scope.isError = false;
						$('#modal-cadastro').modal('hide');
						$('#modal-feedBack').modal('show');
						$scope.jsonObj = null;
					});
					
					response.error(function(data, status, headers, config) {
						$scope.modalFeedBack = "Error to Add a Person. " + status;
						$scope.isError = true;
						$('#modal-cadastro').modal('hide');
						$('#modal-feedBack').modal('show');
					})					
				}
			}
		};
		
		$scope.update = function(){
			$scope.jsonObj = angular.toJson($scope.person, false);
			if ($scope.id != null){
				var response = $http.put('/projetoCRUDAngular/api/people/' + $scope.id, $scope.jsonObj);
				response.success(function(data, status, headers, config) {
					$scope.resetSearch();
					$scope.modalFeedBack = "People Updated with Success";
					$scope.isError = false;
					$('#modal-cadastro').modal('hide');
					$('#modal-feedBack').modal('show');
					$scope.id = null;
			    });
				
				
				response.error(function(data, status, headers, config) {
					$scope.modalFeedBack = "Error to update a Person. " + status;
					$scope.isError = true;
					$('#modal-cadastro').modal('hide');
					$('#modal-feedBack').modal('show');
				})
			}
		};
		
		$scope.del = function() {
			if ($scope.id != null){
				var response = $http.delete('/projetoCRUDAngular/api/people/' + $scope.id);
				response.success(function(data, status, headers, config) {
					$scope.resetSearch();
					$scope.modalFeedBack = "People Deleted with Success";
					$scope.isError = false;
					$('#modal-exclusao').modal('hide');
					$('#modal-feedBack').modal('show');
					$scope.id = null;
				});
			
			
				response.error(function(data, status, headers, config) {
					$scope.modalFeedBack = "Error to delete a Person. " + status;
					$scope.isError = true;
					$('#modal-exclusao').modal('hide');
					$('#modal-feedBack').modal('show');
				})
			}
		};
		
		$scope.resetSearch = function() {
			var app = this;
			$scope.clearForm();
			
			var response = $http.get('/projetoCRUDAngular/api/people/');
			response.success(function(data) {
				$scope.people = data;
				$scope.isError = false;
		    });
			
			response.error(function(data, status, headers, config) {
				$scope.modalFeedBack = "Error to load People" + status;
				$scope.isError = true;
				$('#modal-feedBack').modal('show');
			})
		};
		
	});	
})();