(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('itinary', {
            parent: 'entity',
            url: '/itinary?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.itinary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/itinary/itinaries.html',
                    controller: 'ItinaryController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('itinary');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('itinary-detail', {
            parent: 'entity',
            url: '/itinary/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.itinary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/itinary/itinary-detail.html',
                    controller: 'ItinaryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('itinary');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Itinary', function($stateParams, Itinary) {
                    return Itinary.get({id : $stateParams.id});
                }]
            }
        })
        .state('itinary.new', {
            parent: 'itinary',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/itinary/itinary-dialog.html',
                    controller: 'ItinaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                pricePerSeat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('itinary', null, { reload: true });
                }, function() {
                    $state.go('itinary');
                });
            }]
        })
        .state('itinary.edit', {
            parent: 'itinary',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/itinary/itinary-dialog.html',
                    controller: 'ItinaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Itinary', function(Itinary) {
                            return Itinary.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('itinary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('itinary.delete', {
            parent: 'itinary',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/itinary/itinary-delete-dialog.html',
                    controller: 'ItinaryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Itinary', function(Itinary) {
                            return Itinary.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('itinary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
