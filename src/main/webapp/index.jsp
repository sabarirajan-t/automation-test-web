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
        <title>Test</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#somebutton", function() {
                $.get("start", function(responseJson) { 
                    console.log(responseJson['max_memory']);
                    $("#maxmemory").append(responseJson['max_memory']);
                    $("#avgmemory").append(responseJson['avg_memory']);
                    $("#maxcpu").append(responseJson['max_cpu']);
                    $("#avgcpu").append(responseJson['avg_cpu']);
                });
            });
        </script>
        <script>
            $(document).on("click", "#somebutton1", function() {
                $.post("stop",{ param : "tostop"},function() { 
                    console.log("stopped");
                });
            });

        </script>
    </head>
    <body>
        <button id="somebutton">Start CPU and Memory Performance</button>
        <button id="somebutton1">Stop CPU and Memory Performance</button>
        <div id="somediv"><h2>UEBA:</h2></div>
        <p id="maxmemory">Max. Memory:</p>
        <p id="avgmemory">Avg. Memory:</p>
        <p id="maxcpu">Max. CPU:</p>
        <p id="avgcpu">Avg. CPU:</p>
        <br>
        <br>
        <div id="somediv"><h2>ES:</h2></div>
        <p id="maxmemory">Max. Memory:</p>
        <p id="avgmemory">Avg. Memory:</p>
        <p id="maxcpu">Max. CPU:</p>
        <p id="avgcpu">Avg. CPU:</p>
    </body>
</html>

