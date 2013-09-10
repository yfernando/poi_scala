var map;
var infowindow = new google.maps.InfoWindow();
var lat_long;

/*
*  Fun starts here.
*  Get current location and initiate calls to render the map and near by search.
*/
function initialize() {
    if (navigator.geolocation) {
        try {
            // get current location
            navigator.geolocation.getCurrentPosition(runSearch, showError,{timeout:5000});
        } catch (e) {
            alert(e);
        }
    } else{
        var para = document.createElement('p');
        var txtNode = document.createTextNode('Geolocation is not supported by this browser.');
        para.appendChild(txtNode);
        document.getElementById('map-canvas').appendChild(para);
    }
}

/*
*  Error messages during get current position
*/
function showError(error) {
    var para = document.createElement('p');
    var txtNode;

    switch(error.code){
        case error.PERMISSION_DENIED:
            txtNode = document.createTextNode('User denied the request for Geolocation.');
            break;
        case error.POSITION_UNAVAILABLE:
            txtNode = document.createTextNode('Location information is unavailable.');
            break;
        case error.TIMEOUT:
            txtNode = document.createTextNode('The request to get user location timed out.');
            break;
        case error.UNKNOWN_ERROR:
            txtNode = document.createTextNode('An unknown error occurred.');
            break;
    }

    para.appendChild(txtNode);
    document.getElementById('map-canvas').appendChild(para);
}

/*
*  This method renders the google map and initiates the near by search
*/
function runSearch(position) {
    // Current location based on longitude and latitude coordinates
    lat_long = position.coords.latitude + ',' + position.coords.longitude;
    var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

    // Enable the visual refresh
    google.maps.visualRefresh = true;

    // Render the map
    map = new google.maps.Map(document.getElementById('map-canvas'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: location,
        zoom: 15
    });

    // Create the request for the near-by-search based on the Poi category selected
    var request = {
        location: location,
        radius: 50000,
        types: [poiCategory]
    };

    // initiate near by search
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, callback);

}

/*
*  This callback function is called by nearbySearch(),
*  to handle the result objects and status response.
*  results: array of (google.maps.places.PlaceResult)
*  status: (google.maps.places.PlacesServiceStatus)
*/
function callback(results, status) {
    console.log("Num of Results: ", results.length);

    // for each place returned, create a marker
    if (results.length != 0 && status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
                // create a marker for each poi
                createMarker(results[i]);
        }
    } else {
        // show current location
        var para = document.createElement('p');
        var txtNode = document.createTextNode('no results found');
        para.appendChild(txtNode);
        document.getElementById("map-canvas").appendChild(para);
    }
}

/*
*  Creates a google.maps.Marker for each poi passed into the method.
*  Also adds an on-click listener to display an info window on the marker
*  and the poi detail window.
*  place: (google.maps.places.PlaceResult)
*/
function createMarker(place) {
    //var placeLoc = place.geometry.location;
    var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        visible: true
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.close();
        infowindow.setContent(place.name);
        infowindow.open(map, this);
        display_place_data(place); //method call display the poi detail window
    });
}

/*
*  Display the poi details window when clicked on the a poi
*/
function display_place_data(place){
//    $('#window-content').load('/poi/'+place.id);

    // display the poi detail window
    $('.poi-detail-window').css('display', 'block');
    $('#picture').attr("src", place.icon);
    $('#poi-name').html(place.name);
    $('#address').html(place.vicinity);
    console.log(place);

    var types = place.types;
    var ul = $('#types');
    ul.empty();
    for(var i=0;i < types.length; i++){
      var type = types[i];
      var li = $('<li></li>');
      var link = $('<a href="/poi-category/'+ type + '"><span class="label label-default">' + type + '</span></a>' );
      li.append(link);
      ul.append(li);
    }

    //display the voting buttons
    $('#votes').load('/like-button/' + place.id, function(response, status, xhr) {
      $('#vote-yes').click(function(){
        $.post('/vote/'+place.id, {voted: 'liked'}, function(data){
          $('#votes').html(data);
        })
      });

      $('#vote-no').click(function(){
          $.post('/vote/'+place.id, {voted: 'not_liked'}, function(data){
            $('#votes').html(data);
          })
      });
    });

    // get direction button
    var origin = lat_long;
    var destination = place.vicinity;
    var maps_directions_url = "https://maps.google.co.uk/maps?saddr="
                            + origin + "&daddr=" + destination
                            + "&hl=en&sll=51.528642,-0.101599&sspn=0.486987,1.274414&geocode=FZszEgMdvcv9_yF9CI_EO08YmClrh25x1xp2SDF9CI_EO08YmA%3BFY35EQMdhpT9_ynHA3yYNQV2SDGgZ-yiLa4OBA&oq=west&t=h&mra=ls&z=15";
    $('#get-directions').attr('href', maps_directions_url);

}


google.maps.event.addDomListener(window, 'load', initialize);