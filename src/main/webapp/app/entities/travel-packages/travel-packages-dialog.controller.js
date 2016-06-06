(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesDialogController', TravelPackagesDialogController);

    TravelPackagesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TravelPackages'];

    function TravelPackagesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TravelPackages) {
        var vm = this;

        vm.travelPackages = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.travelPackages.id !== null) {
                TravelPackages.update(vm.travelPackages, onSaveSuccess, onSaveError);
            } else {
                TravelPackages.save(vm.travelPackages, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('desafiohu3App:travelPackagesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
