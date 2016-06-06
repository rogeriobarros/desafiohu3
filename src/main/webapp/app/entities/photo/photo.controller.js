(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('PhotoController', PhotoController);

    PhotoController.$inject = ['$scope', '$state', 'Photo'];

    function PhotoController ($scope, $state, Photo) {
        var vm = this;
        
        vm.photos = [];

        loadAll();

        function loadAll() {
            Photo.query(function(result) {
                vm.photos = result;
            });
        }
    }
})();
