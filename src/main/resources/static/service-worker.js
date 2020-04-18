/**
 * Author: @tea
 */
var cacheName = 'helloWorld';

self.addEventListener('fetch', function (event) {
	event.respondWith(
		caches.match(event.request)
			.then(function (response) {
				// if matching, return result from cache
				if (response) {
					return response;
				}
				// if not matching, fetch from network
				var requestToCache = event.request.clone();
				return fetch(requestToCache)
					.then(function (response) {
						// error
						if (!response || response.status !== 200) {
							return response;
						}
						// everything fine
						var responseToCache = response.clone();
						caches.open(cacheName)
							.then(function (cache) {
								cache.put(requestToCache, responseToCache);
							});
						return response;
					});
			})
	);
});
