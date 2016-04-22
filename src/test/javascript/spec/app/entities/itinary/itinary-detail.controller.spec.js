'use strict';

describe('Controller Tests', function() {

    describe('Itinary Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockItinary, MockDriver, MockCar, MockPath, MockPassenger;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockItinary = jasmine.createSpy('MockItinary');
            MockDriver = jasmine.createSpy('MockDriver');
            MockCar = jasmine.createSpy('MockCar');
            MockPath = jasmine.createSpy('MockPath');
            MockPassenger = jasmine.createSpy('MockPassenger');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Itinary': MockItinary,
                'Driver': MockDriver,
                'Car': MockCar,
                'Path': MockPath,
                'Passenger': MockPassenger
            };
            createController = function() {
                $injector.get('$controller')("ItinaryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carpoolingCsid2016App:itinaryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
