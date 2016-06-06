(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('CountryController', CountryController);

    CountryController.$inject = ['$scope', '$state', 'Country'];

    function CountryController ($scope, $state, Country) {
        var vm = this;
        
        vm.countries = [];

        loadAll();

        function loadAll() {
            Country.query(function(result) {
                vm.countries = result;
            });
        }
    }
})();
