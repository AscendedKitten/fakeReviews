const yelp = require('yelp-fusion');
const fetch = require("node-fetch");
const client = yelp.client('4GV26mG3wEXhR-UZ518LMLOB1kk9h6njL_D09nyIDmUDO00sNI-jXwm0sczt8DGK_lUojZ-crXKn31korqFe5-WcMySlD8SuuYF9x2YJwz9yFTdBOSD7bUjuM0RFXnYx');
const bgp = chrome.extension.getBackgroundPage();

scrapeBtn.onclick = function (element) {
  chrome.tabs.query({ 'active': true, 'lastFocusedWindow': true }, function (tabs) {
    var url = tabs[0].url;

    //let timerId = setInterval(() => yoinkAll(), 1000);
    //setTimeout(() => { clearInterval(timerId); }, 10000);

    yoinkAll();

    //insertReviews(url);
  });
};

function insertReviews(url) {

  let searchTerm;

  if(url.indexOf('?') === -1)
    searchTerm = url.match("[^\/]*$");
 else
    searchTerm = Array.from(url.matchAll(/([^\/]*)\?/g), m => m[1]);

    bgp.console.log('Attempting to fetch from ' + searchTerm);

  client.reviews(searchTerm.toString()).then(response => {
    response.jsonBody.reviews.forEach(async (review) => {
      try {
        (async () => {
          const rawResponse = await fetch('http://lvh.me:8080/reviews', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              'source': searchTerm.toString(),
              'content': review.text
            })
          });
          const content = await rawResponse.json();
          bgp.console.log(content);
        })();
      } catch (e) {
        bgp.console.log(e);
      }
    })
  }).catch(e => {
    bgp.console.log(e);
  })
}

function yoinkAll() {
  client.search({
    latitude: 39.4873,
    longitude: -37.3954,
    limit: 50
  }).then(response => {
    response.jsonBody.businesses.forEach(async (business) => {
      bgp.console.log(business.url);
    });
  }).catch(e => {
    bgp.console.log(e);
  });
}