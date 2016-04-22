(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('ItinaryDialogController', ItinaryDialogController);

    ItinaryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Itinary', 'Driver', 'Car', 'Path', 'Passenger'];

    function ItinaryDialogController ($scope, $stateParams, $uibModalInstance, entity, Itinary, Driver, Car, Path, Passenger) {
        var vm = this;
        vm.itinary = entity;
        vm.drivers = Driver.query();
        vm.cars = Car.query();
        vm.paths = Path.query();
        vm.passengers = Passenger.query();
        vm.load = function(id) {
            Itinary.get({id : id}, function(result) {
                vm.itinary = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:itinaryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.itinary.id !== null) {
                Itinary.update(vm.itinary, onSaveSuccess, onSaveError);
            } else {
                Itinary.save(vm.itinary, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
