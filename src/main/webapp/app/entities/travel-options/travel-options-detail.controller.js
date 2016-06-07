(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('TravelOptionsDetailController', TravelOptionsDetailController);

    TravelOptionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TravelOptions', 'RegionOfOrigin'];

    function TravelOptionsDetailController($scope, $rootScope, $stateParams, entity, TravelOptions, RegionOfOrigin) {
        var vm = this;

        vm.travelOptions = entity;

        var unsubscribe = $rootScope.$on('desafiohu3App:travelOptionsUpdate', function(event, result) {
            vm.travelOptions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
