(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('ItinaryDeleteController',ItinaryDeleteController);

    ItinaryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Itinary'];

    function ItinaryDeleteController($uibModalInstance, entity, Itinary) {
        var vm = this;
        vm.itinary = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Itinary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
