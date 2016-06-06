(function() {
    'use strict';
    angular
        .module('desafiohu3App')
        .factory('TravelOptions', TravelOptions);

    TravelOptions.$inject = ['$resource'];

    function TravelOptions ($resource) {
        var resourceUrl =  'api/travel-options/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
