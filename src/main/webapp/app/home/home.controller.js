(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'TravelPackages'];

    function HomeController ($scope, Principal, LoginService, $state, TravelPackages) {
        var vm = this;
        
        vm.travelPackages = [];

        /*
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        */
        loadAll();

        function loadAll() {
            TravelPackages.query(function(result) {
                vm.travelPackages = result;
            });
        }
    }
})();
