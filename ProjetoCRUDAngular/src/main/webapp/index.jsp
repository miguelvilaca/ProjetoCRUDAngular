<html ng-app="projetoCRUDAngular">
<head>
<title>Projeto CRUD Angular</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script src="js/jquery-2.2.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/angular.js"></script>
<script src="js/dirPagination.js"></script>
<script src="js/app.js"></script>
<body ng-controller="PeopleHttpCtrl as projetoCRUDAngular">
	<div role="main" class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>{{title}}</h2>
			</div>
			<div class="panel-body">
				<div id="peopleList" class="bs-component">
					<form class="form-inline">
						<div class="form-group col-sm-12">
							<div class="input-group input-group-sm">
								<div class="input-group-btn">
									<button class="btn btn-default disabled">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
								<input type="text" ng-model="search" style="float: center;"
									size="50px" class="form-control"
									placeholder="Enter the Id or Name, or CPF">
							</div>

						</div>
					</form>
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th ng-click="sort('id')" class="text-center"><a href="#">Id
										<span class="glyphicon sort-icon" ng-show="sortKey=='id'"
										ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
								</a></th>
								<th class="text-center" ng-click="sort('cpf')"><a href="#">CPF
										<span class="glyphicon sort-icon" ng-show="sortKey=='cpf'"
										ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
								</a></th>
								<th class="text-center" ng-click="sort('nome')"><a href="#">Name
										<span class="glyphicon sort-icon" ng-show="sortKey=='nome'"
										ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
								</a></th>

								<th class="text-center" ng-click="sort('idade')"><a
									href="#">Age <span class="glyphicon sort-icon"
										ng-show="sortKey=='idade'"
										ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span></a>
								</th>
								<th class="text-center"></th>
							</tr>
						</thead>
						<tbody>
							<tr
								dir-paginate="person in people|orderBy:sortKey:reverse|filter:search|itemsPerPage:5">
								<td class="text-center">{{person.id}}</td>
								<td class="text-center">{{person.cpf}}</td>
								<td class="text-center">{{person.name}}</td>
								<td class="text-center">{{person.age}}</td>
								<td class="text-center">
									<button type="button" ng-click="edit(person.id)"
										class="btn btn-success">
										<span class="glyphicon glyphicon-edit"></span>
									</button>
									<button type="button" ng-click="exclusion(person.id)"
										class="btn btn-danger">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
								</td>
							</tr>
						</tbody>
					</table>
					<button type="button" ng-click="add()"
						class="btn btn-warning pull-left">
						<span class="glyphicon glyphicon-ok"></span> New
					</button>
					<div class="text-center">
						<dir-pagination-controls max-size="5" direction-links="true"
							boundary-links="true" />
					</div>
				</div>
				<div class="modal fade" id="modal-cadastro" style="display: none;">
					<div class="modal-dialog">
						<div class="modalApp-container">
							<h1>New Registration</h1>
							<br />
							<form id="main">
								<div>
									<input type="text" placeholder="Enter the Name"
										ng-model="person.name" name="name">
								</div>

								<div>
									<input type="text" name="cpf" placeholder="Enter the CPF"
										ng-model="person.cpf">
								</div>

								<div>
									<input type="text" name="age" placeholder="Enter the Age"
										ng-model="person.age">
								</div>

								<div>
									<input type="submit" class="login modalApp"
										ng-click="save()" value="{{btNew ? 'Save' : 'Update'}}">
									<input type="submit" class="login modalApp"
										value="Cancelar" ng-click="cancel('#modal-cadastro')">
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal fade" id="modal-exclusao" style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Confirmed?</h4>
							</div>
							<div class="modal-body">
								<p>Are you sure?</p>
								<div class="row">
									<div class="col-12-xs text-center">
										<button class="btn btn-success btn-md" ng-click="del()">Yes</button>
										<button class="btn btn-danger btn-md"
											ng-click="cancel('#modal-exclusao')">No</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade col-6-xs" id="modal-feedBack"
					style="display: none;">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title text-center">
									<span
										class="glyphicon {{isError ? 'glyphicon-warning-sign':'glyphicon-ok'}}"
										style="color: {{isError? 'red':'green'"></span>
								</h4>
							</div>
							<div class="modal-body">
								<p>{{modalFeedBack}}</p>
								<div class="row">
									<div class="col-6-xs text-center">
										<button
											class="btn {{isError ? 'btn-danger' : 'btn-success'}}  btn-md"
											ng-click="cancel('#modal-feedBack')">OK</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>