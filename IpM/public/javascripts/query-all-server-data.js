		
			$(function() {
				var availableTags = [];
				$.getJSON("/searchAllServerData", function(data) {
					$.each(data, function(key, val) {
						$.each(val, function(inner_key, inner_val) {
							if (inner_key == "conventionalName") {
								if (availableTags.indexOf(inner_val) == -1){
									availableTags.push(inner_val);
								}
							}
						});
					});
					$('#cName').typeahead({                                
						  name: 'Type a Name',                                                          
						  local: availableTags,                                         
						  limit: 10                                                                   
						});				
				});

			});


			function addRow(tableID, textRow, serverData) {
				// Get a reference to the table
				var table = document.getElementById(tableID);

				var tableRef = table.getElementsByTagName("tbody")[0];
				// Insert a row in the table at row index 0
				var newRow = tableRef.insertRow(0);

				// Insert a cell in the row at index 0
				var newCell = newRow.insertCell(0);

				var element1 = document.createElement("span");
				element1.id = "serverData_" + serverData['id'];
				element1.textContent = serverData['hostName'] + '@@'
						+ serverData['domain'] + ':' + serverData['port']
						+ ' <' + serverData['ipAddress'] + '>';
				newCell.appendChild(element1);
			}

			function emptyTable(tableID) {
				var table = document.getElementById(tableID);
				var tableElements = table.getElementsByTagName("tbody");
				var new_tbody = document.createElement('tbody');
				if (tableElements.length != 0) {
					var old_tbody = tableElements[0];
					table.replaceChild(new_tbody, old_tbody)
				} else {
					table.appendChild(new_tbody);
				}
			}

			function fillTable(e) {
				if (e.keyCode == 13) {
					emptyTable("IpMTable");
					if ($("#cName").val() != undefined) {
						$.getJSON("/searchServerData/" + $("#cName").val(),
								function(data) {
									$.each(data, function(key, val) {
										addRow("IpMTable", $("#cName").val(),
												val);
									});
								});
					}
					return false;
				}
			}
