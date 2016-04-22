(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('ItinaryDetailController', ItinaryDetailController);

    ItinaryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Itinary', 'Driver', 'Car', 'Path', 'Passenger'];

    function ItinaryDetailController($scope, $rootScope, $stateParams, entity, Itinary, Driver, Car, Path, Passenger) {
        var vm = this;
        vm.itinary = entity;
        vm.load = function (id) {
            Itinary.get({id: id}, function(result) {
                vm.itinary = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:itinaryUpdate', function(event, result) {
            vm.itinary = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
