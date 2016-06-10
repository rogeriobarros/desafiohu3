'use strict';

describe('Controller Tests', function() {

    describe('TravelPackages Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTravelPackages, MockTravelOptions, MockPhoto, MockLocation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTravelPackages = jasmine.createSpy('MockTravelPackages');
            MockTravelOptions = jasmine.createSpy('MockTravelOptions');
            MockPhoto = jasmine.createSpy('MockPhoto');
            MockLocation = jasmine.createSpy('MockLocation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TravelPackages': MockTravelPackages,
                'TravelOptions': MockTravelOptions,
                'Photo': MockPhoto,
                'Location': MockLocation
            };
            createController = function() {
                $injector.get('$controller')("TravelPackagesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'desafiohu3App:travelPackagesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
