(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('RegionOfOriginDeleteController',RegionOfOriginDeleteController);

    RegionOfOriginDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegionOfOrigin'];

    function RegionOfOriginDeleteController($uibModalInstance, entity, RegionOfOrigin) {
        var vm = this;

        vm.regionOfOrigin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RegionOfOrigin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
