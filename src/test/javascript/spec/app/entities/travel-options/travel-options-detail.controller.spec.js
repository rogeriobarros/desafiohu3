'use strict';

describe('Controller Tests', function() {

    describe('TravelOptions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTravelOptions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTravelOptions = jasmine.createSpy('MockTravelOptions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TravelOptions': MockTravelOptions
            };
            createController = function() {
                $injector.get('$controller')("TravelOptionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'desafiohu3App:travelOptionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
