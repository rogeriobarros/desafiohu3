(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('region-of-origin', {
            parent: 'entity',
            url: '/region-of-origin',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RegionOfOrigins'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region-of-origin/region-of-origins.html',
                    controller: 'RegionOfOriginController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('region-of-origin-detail', {
            parent: 'entity',
            url: '/region-of-origin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RegionOfOrigin'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/region-of-origin/region-of-origin-detail.html',
                    controller: 'RegionOfOriginDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RegionOfOrigin', function($stateParams, RegionOfOrigin) {
                    return RegionOfOrigin.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('region-of-origin.new', {
            parent: 'region-of-origin',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region-of-origin/region-of-origin-dialog.html',
                    controller: 'RegionOfOriginDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameRegion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('region-of-origin', null, { reload: true });
                }, function() {
                    $state.go('region-of-origin');
                });
            }]
        })
        .state('region-of-origin.edit', {
            parent: 'region-of-origin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region-of-origin/region-of-origin-dialog.html',
                    controller: 'RegionOfOriginDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegionOfOrigin', function(RegionOfOrigin) {
                            return RegionOfOrigin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('region-of-origin', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('region-of-origin.delete', {
            parent: 'region-of-origin',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/region-of-origin/region-of-origin-delete-dialog.html',
                    controller: 'RegionOfOriginDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegionOfOrigin', function(RegionOfOrigin) {
                            return RegionOfOrigin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('region-of-origin', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
