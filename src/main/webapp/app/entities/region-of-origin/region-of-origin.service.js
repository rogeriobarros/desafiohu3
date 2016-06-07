(function() {
    'use strict';
    angular
        .module('desafiohu3App')
        .factory('RegionOfOrigin', RegionOfOrigin);

    RegionOfOrigin.$inject = ['$resource'];

    function RegionOfOrigin ($resource) {
        var resourceUrl =  'api/region-of-origins/:id';

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
