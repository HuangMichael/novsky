(function($) {
		selections = [{
			id: 1,
			text: 'Enhancement'
		}, {
			id: 2,
			text: 'Bug'
		}, {
			id: 3,
			text: 'Duplicate'
		}, {
			id: 4,
			text: 'Invalid'
		}, {
			id: 5,
			text: 'Wont Fix '
		}];
		var extract_preselected_ids = function(element) {
			var preselected_ids = [];
			if(element.val()) $(element.val().split(",")).each(function() {
				preselected_ids.push({
					id: this
				});
			});
			console.log(preselected_ids);
			return preselected_ids;
		};
		var preselect = function(preselected_ids) {
			var pre_selections = [];
			for(index in selections)
				for(id_index in preselected_ids)
					if(selections[index].id == preselected_ids[id_index].id) pre_selections.push(selections[index]);
			return pre_selections;
		};
		$('#myselect - 1 ').select2({
			placeholder: 'Select Resources ',
			minimumInputLength: 0,
			multiple: true,
			allowClear: true,
			data: function() {
				return {
					results: selections
				}
			},
			initSelection: function(element, callback) {
				var preselected_ids = extract_preselected_ids(element);
				var preselections = preselect(preselected_ids);
				callback(preselections);
			}
		}).select2('val ', [2, 4, 5]); // 2,4,5 are the pre-selected IDs    
		$('#myselect - 2 ').select2({
					placeholder: 'Select Resources ',minimumI nputLength: 0,multiple: true,allowClear: true,data: function(){return {results: selections}},  initSelection: function(element, callback){var preselected_ids = extract_preselected_ids(element); //1,3,4 are the pre-selected IDs as per HTML attributesvar preselections = preselect(preselected_ids);callback(preselections);}    });})(jQuery);