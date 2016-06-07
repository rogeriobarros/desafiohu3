(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelOptionsDialogController', TravelOptionsDialogController);

    TravelOptionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TravelOptions', 'RegionOfOrigin'];

    function TravelOptionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TravelOptions, RegionOfOrigin) {
        var vm = this;

        vm.travelOptions = entity;
        vm.clear = clear;
        vm.save = save;
        vm.regionoforigins = RegionOfOrigin.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.travelOptions.id !== null) {
                TravelOptions.update(vm.travelOptions, onSaveSuccess, onSaveError);
            } else {
                TravelOptions.save(vm.travelOptions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('desafiohu3App:travelOptionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
