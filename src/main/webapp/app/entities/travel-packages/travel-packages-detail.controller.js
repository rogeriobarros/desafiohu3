(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesDetailController', TravelPackagesDetailController);

    TravelPackagesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TravelPackages', 'TravelOptions', 'Photo', 'Location'];

    function TravelPackagesDetailController($scope, $rootScope, $stateParams, entity, TravelPackages, TravelOptions, Photo, Location) {
        var vm = this;

        vm.travelPackages = entity;
        
        $scope.slides =  vm.travelPackages.photos
        $scope.optionsDaily = vm.travelPackages.options.map(function(a) {return a.daily;}); 
        var regionsFrom = vm.travelPackages.options.map(function(a) {return a.froms;});
        
        var array = [];
        regionsFrom.map(function(a) {
        	for (var i = 0; i < a.length; i++) {
        		array.push(a[i].nameRegion);
			}
        	return 0;
        });
        array.sort();
        $scope.regionsFrom = array
        console.log($scope.regionsFrom);
        
        var unsubscribe = $rootScope.$on('desafiohu3App:travelPackagesUpdate', function(event, result) {
            vm.travelPackages = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
