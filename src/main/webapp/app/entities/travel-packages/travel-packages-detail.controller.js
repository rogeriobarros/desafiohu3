(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesDetailController', TravelPackagesDetailController);

    TravelPackagesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TravelPackages', 'TravelOptions', 'Photo'];

    function TravelPackagesDetailController($scope, $rootScope, $stateParams, entity, TravelPackages, TravelOptions, Photo) {
        var vm = this;

        vm.travelPackages = entity;

        var unsubscribe = $rootScope.$on('desafiohu3App:travelPackagesUpdate', function(event, result) {
            vm.travelPackages = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
