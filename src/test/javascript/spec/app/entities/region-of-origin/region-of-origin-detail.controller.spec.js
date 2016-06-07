'use strict';

describe('Controller Tests', function() {

    describe('RegionOfOrigin Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRegionOfOrigin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRegionOfOrigin = jasmine.createSpy('MockRegionOfOrigin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RegionOfOrigin': MockRegionOfOrigin
            };
            createController = function() {
                $injector.get('$controller')("RegionOfOriginDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'desafiohu3App:regionOfOriginUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
