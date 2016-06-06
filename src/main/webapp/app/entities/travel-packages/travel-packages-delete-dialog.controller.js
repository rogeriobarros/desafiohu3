(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesDeleteController',TravelPackagesDeleteController);

    TravelPackagesDeleteController.$inject = ['$uibModalInstance', 'entity', 'TravelPackages'];

    function TravelPackagesDeleteController($uibModalInstance, entity, TravelPackages) {
        var vm = this;

        vm.travelPackages = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TravelPackages.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
