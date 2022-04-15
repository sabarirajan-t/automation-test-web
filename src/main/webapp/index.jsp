<%-- <html>
<head>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
        $(document).on("click", "#submitbutton", function() {               // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.get("test", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
        var $select = $("#testselect");                           // Locate HTML DOM element with ID "someselect".
        $select.find("option").remove();                          // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
            $("<option>").val(key).text(value).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
        });
    });
});
        </script>
    </head>
<body>
<%-- <form action="test" method="get"> --%>
<%-- <label>Date:</label>
<input type="date" id="date" name="date">
<br>
<label>Build Number:</label>
<input type="text" id="buildno" name="buildno" placeholder="Enter build no.">
<br>
<label>Validation Name:</label>
<input type="text" id="validationname" name="validationname" placeholder="Enter validation name">
<br>
<label>Description:</label>
<input type="text" id="description" name="description" placeholder="Description">
<br>
<input type="button" value="test"   name="submitbutton">
<br>
<br>
<h1>Export Stats</h1>
<br>
<table>
  <tr>
    <th>Date</th>
    <th>Build No.</th>
    <th>Validation Name</th>
    <th>Trial</th>
    <th>Format</th>
    <th>Queuing Time</th>
    <th>Export Time</th>
    <th>Total Time</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
    <td>test</td>
  </tr>
</table>
<%-- </form> --%>
<%-- <select id="testselect"></select>

</body>
</html> --%> 

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#somebutton", function() { 
                $.get("test", function(responseText) {  
                    $("#somediv").text(responseText);       
                });
            });
        </script>
    </head>
    <body>
        <button id="somebutton">For cpu and Memory performance</button>
        <div id="somediv"></div>
        <p id="maxmemory">Max. Memory:</div>
        <p id="maxmemory">Avg. Memory:</div>
        <p id="maxmemory">Max. CPU:</div>
        <p id="maxmemory">Max. CPU:</div>
    </body>
</html>

