(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .controller('PhotoDetailController', PhotoDetailController);

    PhotoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Photo'];

    function PhotoDetailController($scope, $rootScope, $stateParams, entity, Photo) {
        var vm = this;

        vm.photo = entity;

        var unsubscribe = $rootScope.$on('desafiohu3App:photoUpdate', function(event, result) {
            vm.photo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
