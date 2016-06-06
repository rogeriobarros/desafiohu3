(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesController', TravelPackagesController);

    TravelPackagesController.$inject = ['$scope', '$state', 'TravelPackages'];

    function TravelPackagesController ($scope, $state, TravelPackages) {
        var vm = this;
        
        vm.travelPackages = [];

        loadAll();

        function loadAll() {
            TravelPackages.query(function(result) {
                vm.travelPackages = result;
            });
        }
    }
})();
