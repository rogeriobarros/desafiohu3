(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('CountryDetailController', CountryDetailController);

    CountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Country'];

    function CountryDetailController($scope, $rootScope, $stateParams, entity, Country) {
        var vm = this;

        vm.country = entity;

        var unsubscribe = $rootScope.$on('desafiohu3App:countryUpdate', function(event, result) {
            vm.country = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
