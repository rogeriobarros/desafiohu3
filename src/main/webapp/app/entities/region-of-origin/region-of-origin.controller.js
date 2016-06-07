(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('RegionOfOriginController', RegionOfOriginController);

    RegionOfOriginController.$inject = ['$scope', '$state', 'RegionOfOrigin'];

    function RegionOfOriginController ($scope, $state, RegionOfOrigin) {
        var vm = this;
        
        vm.regionOfOrigins = [];

        loadAll();

        function loadAll() {
            RegionOfOrigin.query(function(result) {
                vm.regionOfOrigins = result;
            });
        }
    }
})();
