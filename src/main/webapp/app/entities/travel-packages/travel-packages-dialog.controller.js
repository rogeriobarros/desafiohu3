(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelPackagesDialogController', TravelPackagesDialogController);

    TravelPackagesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TravelPackages', 'TravelOptions', 'Photo', 'Location'];

    function TravelPackagesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TravelPackages, TravelOptions, Photo, Location) {
        var vm = this;

        vm.travelPackages = entity;
        vm.clear = clear;
        vm.save = save;
        vm.traveloptions = TravelOptions.query();
        vm.photos = Photo.query();
        vm.locations = Location.query({filter: 'travalpackages-is-null'});
        $q.all([vm.travelPackages.$promise, vm.locations.$promise]).then(function() {
            if (!vm.travelPackages.locationId) {
                return $q.reject();
            }
            return Location.get({id : vm.travelPackages.locationId}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });

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
