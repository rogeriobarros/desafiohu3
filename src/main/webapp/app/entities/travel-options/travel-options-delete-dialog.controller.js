(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelOptionsDeleteController',TravelOptionsDeleteController);

    TravelOptionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TravelOptions'];

    function TravelOptionsDeleteController($uibModalInstance, entity, TravelOptions) {
        var vm = this;

        vm.travelOptions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TravelOptions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
