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
            $(document).on("click", "#startBtn", function() {
                $.get("start", function(responseJson) { 
                    console.log(responseJson['max_memory_ueba']);
                    $("#maxmemory").append(responseJson['max_memory_ueba']);
                    $("#avgmemory").append(responseJson['avg_memory_ueba']);
                    $("#minmemory").append(responseJson['min_memory_ueba']);
                    $("#maxcpu").append(responseJson['max_cpu_ueba']);
                    $("#avgcpu").append(responseJson['avg_cpu_ueba']);
                    $("#mincpu").append(responseJson['min_cpu_ueba']);
                    $("#maxmemory_es").append(responseJson['max_memory_es']);
                    $("#avgmemory_es").append(responseJson['avg_memory_es']);
                    $("#minmemory_es").append(responseJson['min_memory_es']);
                    $("#maxcpu_es").append(responseJson['max_cpu_es']);
                    $("#avgcpu_es").append(responseJson['avg_cpu_es']);
                    $("#mincpu_es").append(responseJson['min_cpu_es']);
                });
            });
        </script>
        <script>
            $(document).on("click", "#stopBtn", function() {
                $.post("stop",{ param : "tostop"},function() { 
                    console.log("stopped");
                });
            });

        </script>
        <script>
            $(document).on("click", "#exportBtn", function() {
                var testdate=$('#date').val();
                var build_no=$('#build_no').val();
                var vali_name=$('#vali_name').val();
                var desc=$('#desc').val();
                console.log(testdate);
                console.log(build_no);
                console.log(vali_name);
                console.log(desc);
                $.post("export",{ param : "export"},function(responseJson) { 
                    console.log("export starting");
                    var tbody='';
                    for(var i=0;i<responseJson.length;i++){
                        tbody+="<tr>\
                                <td>" + responseJson[i]['Format'] +" </td>\
                                <td>" + responseJson[i]['QueueTime'] +" </td>\
                                <td>" + responseJson[i]['ExportTime'] +" </td>\
                                <td>" + responseJson[i]['TotalTime'] +" </td>\
                                </tr>";
                        // $("#format_td").html(responseJson[i]['Format']);
                        // $("#queue_td").html(responseJson[i]['QueueTime']);
                        // $("#export_td").html(responseJson[i]['ExportTime']);
                        // $("#total_td").html(responseJson[i]['TotalTime']);  
                    }
                    $('#dataBody').html(tbody);
                });
            });

        </script>
    </head>
    <body>
        <button id="startBtn">Start CPU and Memory Performance</button>
        <button id="stopBtn">Stop CPU and Memory Performance</button>
        <div id="somediv"><h2>UEBA:</h2></div>
        <p id="maxmemory">Max. Memory:</p>
        <p id="avgmemory">Avg. Memory:</p>
        <p id="minmemory">Min. Memory:</p>
        <p id="maxcpu">Max. CPU:</p>
        <p id="avgcpu">Avg. CPU:</p>
        <p id="mincpu">MIn. CPU:</p>
        <br>
        <br>
        <div id="somediv"><h2>ES:</h2></div>
        <p id="maxmemory_es">Max. Memory:</p>
        <p id="avgmemory_es">Avg. Memory:</p>
        <p id="minmemory_es">Min. Memory:</p>
        <p id="maxcpu_es">Max. CPU:</p>
        <p id="avgcpu_es">Avg. CPU:</p>
        <p id="mincpu_es">Min. CPU:</p>
        <div>
        <div>
        <div>
        <div>
        <label for="date">Date:</label>
        <input type="datetime-local" id="date">
        <label for="build_no">Build No.:</label>
        <input type="text" id="build_no">
        <label for="vali_name">Validation Name:</label>
        <input type="text" id="vali_name">
        <label for="desc">Description:</label>
        <input type="textarea" id="desc">
        <button id="exportBtn">Start Export Performance</button>
        <h1>Export Stats</h1>
<br>
<table>
  <tr>
    <th>Date</th>
    <th>Build No.</th>
    <th>Validation Name</th>
    <th>Format</th>
    <th>Queuing Time</th>
    <th>Export Time</th>
    <th>Total Time</th>
    <th>Description</th>
  </tr>
  <tbody id="dataBody">
  </tbody>
  <%-- <tr>
    <td id="format_td"></td>
    <td id="queue_td">test</td>
    <td id="export_td">test</td>
    <td id="total_td">test</td>
  </tr> --%>
</table>
<div id="somediv"></div>
    </body>
</html>

