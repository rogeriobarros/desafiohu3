(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('travel-packages', {
            parent: 'entity',
            url: '/travel-packages',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TravelPackages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-packages/travel-packages.html',
                    controller: 'TravelPackagesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('travel-packages-detail', {
            parent: 'entity',
            url: '/travel-packages/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TravelPackages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-packages/travel-packages-detail.html',
                    controller: 'TravelPackagesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TravelPackages', function($stateParams, TravelPackages) {
                    return TravelPackages.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('travel-packages.new', {
            parent: 'travel-packages',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-packages/travel-packages-dialog.html',
                    controller: 'TravelPackagesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idTravelPackage: null,
                                titleTravelPackage: null,
                                descriptionTravelPackage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('travel-packages', null, { reload: true });
                }, function() {
                    $state.go('travel-packages');
                });
            }]
        })
        .state('travel-packages.edit', {
            parent: 'travel-packages',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-packages/travel-packages-dialog.html',
                    controller: 'TravelPackagesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TravelPackages', function(TravelPackages) {
                            return TravelPackages.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-packages', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('travel-packages.delete', {
            parent: 'travel-packages',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-packages/travel-packages-delete-dialog.html',
                    controller: 'TravelPackagesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TravelPackages', function(TravelPackages) {
                            return TravelPackages.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-packages', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
