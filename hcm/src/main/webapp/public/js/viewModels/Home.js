/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Home module
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'appVar', 'viewModels/searchList', 'ojs/ojknockout', 'ojs/ojselectcombobox', 'ojs/ojslider', 'ojs/ojlistview', 'ojs/ojdatacollection-common', 'ojs/ojcollectiontreedatasource', 'ojs/ojtrain', 'ojs/ojbutton', 'ojs/ojcheckboxset'
], function (oj, ko, $, gVar, searchList) {
    /**
     * The view model for the main content view template
     */
    function HomeContentViewModel() {
        var self = this;
        self.data = new Array();

        self.keyword = ko.observableArray();
        self.dataSource = ko.observable([]);
        self.items = ko.observableArray([]);
        self.selectedItems = ko.observableArray([]);
        self.tempArr = new Array();
        self.gotoList = function () {
            oj.Router.rootInstance.go('searchList');
        };

        self.value1 = ko.observable("");
        self.value2 = ko.observable("");
        self.value3 = ko.observable("");
        self.value4 = ko.observable("");
        self.value5 = ko.observable("");
        self.value6 = ko.observable("");
        self.value7 = ko.observable("");
        self.value8 = ko.observable("");
        self.value11 = ko.observable("");
        self.value12 = ko.observable("");
        self.value13 = ko.observable("");
        self.value14 = ko.observable("");
        self.value15 = ko.observable("");
        self.value16 = ko.observable("");
        self.value17 = ko.observable("");
        self.value9 = ko.observableArray([]);
        self.value10 = ko.observableArray([]);
        self.value18 = ko.observableArray([]);
        self.value19 = ko.observableArray([]);
        self.currentStepValue = ko.observable('全部');
        self.stepArray = ko.observableArray(
                [{label: '高専卒', id: '高専卒'},
                    {label: '四大卒', id: '四大卒'},
                    {label: '大学院卒', id: '大学院卒'},
                    {label: '全部', id: '全部'}]);

        self.currentSkillValue = ko.observableArray([]);
        self.skillArray =
                ko.observableArray(
                        [{label: '1', value: '1', id: 'stp1'},
                            {label: '2', value: '2', id: 'stp2'},
                            {label: '3', value: '3', id: 'stp3'},
                            {label: '4', value: '4', id: 'stp4'},
                            {label: '5', value: '5', id: 'stp5'}]);

        self.isOversea = ko.observableArray(['0']);
        self.advcheckValue = ko.observableArray(['0']);


        self.tags = ko.observableArray([
            {value: "中国語", label: "中国語"},
            {value: "中国語 広東語", label: "中国語 広東語"},
            {value: "中国語 北京語", label: "中国語 北京語"},
            {value: "中国 勤務地", label: "中国 勤務地"},
            {value: "中国語検定", label: "中国語検定"},
            {value: "ビルマ", label: "ビルマ"},
            {value: "ミャンマー語検定（MLT）", label: "ミャンマー語検定（MLT）"},
            {value: "ミャンマー 国籍", label: "ミャンマー 国籍"},
            {value: "ミャンマー 事業所", label: "ミャンマー 事業所"},
            {value: "ミャンマー 勤務地", label: "ミャンマー 勤務地"}
        ]);

        self.AgevalueArray = ko.observableArray([25, 45]);
        self.Agemin = ko.observable(1);
        self.Agemax = ko.observable(80);
        self.Agestep = ko.observable(1);

        self.hvalueArray = ko.observableArray([1990, 2017]);
        self.hmin = ko.observable(1990);
        self.hmax = ko.observable(2017);
        self.hstep = ko.observable(1);

        var $boxOne = $('#advSerchLayout');
        var flagger = 1;

        self.advSearching = function () {
            if (!$('#advSerchLayout').hasClass('vertTranslate'))
            {
                $('#advSerchContent').removeClass('animated fadeOut');
                $('#advSerchContent').removeClass('show_hide');
                $('#advSerchLayout').addClass('vertTranslate');
                $('#advSerchContent').addClass('animated fadeIn');
            } else {
                $('#advSerchLayout').removeClass('vertTranslate');
                $('#advSerchContent').removeClass('animated fadeIn');
                $('#advSerchContent').addClass('animated fadeOut');
            }
        };

        self.searchBtnAction = function () {
            self.searchAction(self.keyword());
        };

        self.searchAction = function (searchMe) {
            
            gVar.savedSearchText = searchMe;
            var ServiceURL = gVar.ServiceURL;
            $.ajax({
                type: "GET",
                url: ServiceURL + "?q=" + searchMe,
                async: false,
                success: function (data) {
                    console.log(data);
                    gVar.listData = data;
                    oj.Router.rootInstance.go('searchList');
                    searchList.Init();
                },
                error: function (err) {
                    alert(err.statusText);
                }
            });
        };

        self.comSearching = function () {
        	var finalSearchText = "";
        	var isMultipleKey = false;
        	
        	if(self.value1()) {
        		finalSearchText += "(id:"+self.value1()+")";
        		isMultipleKey = true;
        	}
        	if(self.value2()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "nation:\""+self.value2()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.value3()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "name:\""+self.value3()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.currentStepValue() && !'全部' === self.currentStepValue()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
    			finalSearchText += "highest_education_level:\""+self.currentStepValue()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.AgevalueArray()[1]) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "age:[" + self.AgevalueArray()[0] + " TO " + self.AgevalueArray()[1] + "])";
        		isMultipleKey = true;
        	}
        	if(self.hvalueArray()[0]) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "hired_year:[" + self.hvalueArray()[0] + " TO " + self.hvalueArray()[1] + "])";
        		isMultipleKey = true;
        	}
        	if(self.value7()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "department:\""+self.value7()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.value8()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "job:\""+self.value8()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.value9()[0]) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "grade_name:\""+self.value9()[0] + "\"";
        		for (var i = 1; i < self.value9().length; i++) {
        			finalSearchText += " OR " + "grade_name:\"" + self.value9()[i] + "\"";
                }
        		finalSearchText += ")";
        		isMultipleKey = true;
        	}
        	if(self.value10()[0]) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "grade_step_name:\""+self.value10()[0] + "\"";
        		for (var i = 1; i < self.value10().length; i++) {
        			finalSearchText += " OR " + "grade_step_name:\"" + self.value10()[i] + "\"";
                }
        		finalSearchText += ")";
        		isMultipleKey = true;
        	}
        	if(self.value11()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "product:\""+self.value11()+"\")";
        		isMultipleKey = true;
        	}
        	if(self.value12()) {
        		if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "function:\""+self.value12()+"\")";
        		isMultipleKey = true;
        	}
            if (self.isOversea()[0] === '1') {
            	if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
            	finalSearchText += "oversea_experience: \"Yes\")";
            	isMultipleKey = true;
            }/* else {
            	finalSearchText += "(oversea_experience: No)";
            }*/
            if(self.value14()) {
            	if(isMultipleKey) {
        			finalSearchText += " AND (";
        		}
        		else {
        			finalSearchText += "(";
        		}
        		finalSearchText += "location:\""+self.value14()+"\")";
        		isMultipleKey = true;
        	}
            
            console.log(finalSearchText);
            self.searchAction(finalSearchText);

        };


        self.valueChangeHandler = function (context, ui) {
            if (ui.option === "value") {
                //recall with ui.value
//                self.searchAction();
            }
        };
        var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
        self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);



        self.handleActivated = function (info) {
            // Implement if needed
//            $("#homeError").hide();
        };
        /**
         * Optional ViewModel method invoked after the View is inserted into the
         * document DOM.  The application can put logic that requires the DOM being
         * attached here.
         * @param {Object} info - An object with the following key-value pairs:
         * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
         * @param {Function} info.valueAccessor - The binding's value accessor.
         * @param {boolean} info.fromCache - A boolean indicating whether the module was retrieved from cache.
         */
        self.handleAttached = function (info) {
            // Implement if needed
//            $.getJSON("js/JSON/allCloudInfomation.json").done(function (data) {
//               
//            });
        };
        /**
         * Optional ViewModel method invoked after the bindings are applied on this View. 
         * If the current View is retrieved from cache, the bindings will not be re-applied
         * and this callback will not be invoked.
         * @param {Object} info - An object with the following key-value pairs:
         * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
         * @param {Function} info.valueAccessor - The binding's value accessor.
         */
        self.handleBindingsApplied = function (info) {
            // Implement if needed
        };
        /*
         * Optional ViewModel method invoked after the View is removed from the
         * document DOM.
         * @param {Object} info - An object with the following key-value pairs:
         * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
         * @param {Function} info.valueAccessor - The binding's value accessor.
         * @param {Array} info.cachedNodes - An Array containing cached nodes for the View if the cache is enabled.
         */
        self.handleDetached = function (info) {
            // Implement if needed
            self.tags();
            self.dataSource();
            self.items();
            self.data = new Array();
        };
    }

    return new HomeContentViewModel();
});
