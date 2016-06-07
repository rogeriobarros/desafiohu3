(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('RegionOfOriginDialogController', RegionOfOriginDialogController);

    RegionOfOriginDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RegionOfOrigin'];

    function RegionOfOriginDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RegionOfOrigin) {
        var vm = this;

        vm.regionOfOrigin = entity;
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
            if (vm.regionOfOrigin.id !== null) {
                RegionOfOrigin.update(vm.regionOfOrigin, onSaveSuccess, onSaveError);
            } else {
                RegionOfOrigin.save(vm.regionOfOrigin, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('desafiohu3App:regionOfOriginUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
