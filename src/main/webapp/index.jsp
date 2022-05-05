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
                $.post("export",{ param : "export", date: testdate, buildNo: build_no, valName: vali_name, description: desc},function(responseJson) { 
                    console.log("export starting");
                    var tbody='';
                    for(var i=0;i<responseJson.length;i++){
                        tbody+="<tr>\
                                <td>" + responseJson[i]['Date'] +" </td>\
                                <td>" + responseJson[i]['BuildNumber'] +" </td>\
                                <td>" + responseJson[i]['ValidationName'] +" </td>\
                                <td>" + responseJson[i]['Format'] +" </td>\
                                <td>" + responseJson[i]['QueueTime'] +" </td>\
                                <td>" + responseJson[i]['ExportTime'] +" </td>\
                                <td>" + responseJson[i]['TotalTime'] +" </td>\
                                <td>" + responseJson[i]['EntriesCount'] +" </td>\
                                <td>" + responseJson[i]['Description'] +" </td>\
                                </tr>";
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
        <h1>Export Stats</h1>
        <div>
        <label for="date">Date:</label>
        <input type="date" id="date">
        <label for="build_no">Build No.:</label>
        <input type="text" id="build_no">
        <label for="vali_name">Validation Name:</label>
        <input type="text" id="vali_name">
        <label for="desc">Description:</label>
        <input type="textarea" id="desc">
        <button id="exportBtn">Start Export Performance</button>
<br>
<br>
<div>
<table border="1">
  <tr>
    <th>Date</th>
    <th>Build No.</th>
    <th>Validation Name</th>
    <th>Format</th>
    <th>Queuing Time</th>
    <th>Export Time</th>
    <th>Total Time</th>
    <th>Entries Count</th>
    <th>Description</th>
  </tr>
  <tbody id="dataBody">
  </tbody>
</table>
</div>
<div id="somediv"></div>
    </body>
</html>

