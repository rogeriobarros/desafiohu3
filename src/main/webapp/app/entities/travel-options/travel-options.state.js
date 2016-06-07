(function() {
    'use strict';

    angular
        .module('desafiohu3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('travel-options', {
            parent: 'entity',
            url: '/travel-options?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TravelOptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-options/travel-options.html',
                    controller: 'TravelOptionsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('travel-options-detail', {
            parent: 'entity',
            url: '/travel-options/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TravelOptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-options/travel-options-detail.html',
                    controller: 'TravelOptionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TravelOptions', function($stateParams, TravelOptions) {
                    return TravelOptions.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('travel-options.new', {
            parent: 'travel-options',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-options/travel-options-dialog.html',
                    controller: 'TravelOptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titleTravelOption: null,
                                descriptionTravelOption: null,
                                daily: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('travel-options', null, { reload: true });
                }, function() {
                    $state.go('travel-options');
                });
            }]
        })
        .state('travel-options.edit', {
            parent: 'travel-options',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-options/travel-options-dialog.html',
                    controller: 'TravelOptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TravelOptions', function(TravelOptions) {
                            return TravelOptions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-options', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('travel-options.delete', {
            parent: 'travel-options',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-options/travel-options-delete-dialog.html',
                    controller: 'TravelOptionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TravelOptions', function(TravelOptions) {
                            return TravelOptions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-options', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
