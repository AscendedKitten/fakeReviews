chrome.declarativeContent.onPageChanged.removeRules(undefined, function () {
  chrome.declarativeContent.onPageChanged.addRules([{
    conditions: [new chrome.declarativeContent.PageStateMatcher({
      pageUrl: { urlMatches: 'yelp(?:\.[a-z]{2,3})?' },
    })
    ],
    actions: [new chrome.declarativeContent.ShowPageAction()]
  }]);
});