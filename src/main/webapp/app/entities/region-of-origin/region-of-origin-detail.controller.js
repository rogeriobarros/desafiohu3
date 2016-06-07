(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('RegionOfOriginDetailController', RegionOfOriginDetailController);

    RegionOfOriginDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RegionOfOrigin'];

    function RegionOfOriginDetailController($scope, $rootScope, $stateParams, entity, RegionOfOrigin) {
        var vm = this;

        vm.regionOfOrigin = entity;

        var unsubscribe = $rootScope.$on('desafiohu3App:regionOfOriginUpdate', function(event, result) {
            vm.regionOfOrigin = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
