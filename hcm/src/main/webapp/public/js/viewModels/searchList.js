/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * searchList module 
 */
define(['ojs/ojcore', 'knockout', 'appVar', 'jquery', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojarraytabledatasource', 'ojs/ojcheckboxset', 'ojs/ojcheckboxset', 'ojs/ojradioset', 'ojs/ojdatagrid', 'ojs/ojtable', 'ojs/ojdatacollection-utils', 'ojs/ojcollapsible', 'ojs/ojarraytabledatasource',
    'ojs/ojvalidation-number', 'ojs/ojpopup'], function (oj, ko, gVar) {
    /**
     * The view model for the main content view template
     */
    function searchListContentViewModel() {
        var self = this;
        self.checkValue = ko.observableArray(["0"]);
        self.items = ko.observableArray([]);
        self.lesCheck = ko.observable(false);

        self.dataSource1 = new oj.ArrayTableDataSource(self.items, {idAttribute: "person_number"});
        self.dataSource2 = new oj.ArrayTableDataSource([]);

        self.departments = ko.observableArray([]);
        self.grades = ko.observableArray([]);
        self.products = ko.observableArray([]);
        self.functions = ko.observableArray([]);
        self.locations = ko.observableArray([]);
        
        var departmentMap = {};
        var gradeMap = {};
        var productMap = {};
        var functionMap = {};
        var locationMap = {};
        
        var data = [
            {id: 0, name: 'Settings', version: '10.3.6', nodes: 2, cpu: 2, type: 'Java Cloud Service Virtual Image', balancer: 1, memory: 8},
            {id: 1, name: 'Tools', version: '10.3.6', nodes: 2, cpu: 2, type: 'Java Cloud Service Virtual Image', balancer: 1, memory: 8},
            {id: 2, name: 'Base', version: '10.3.6', nodes: 2, cpu: 2, type: 'Java Cloud Service Virtual Image', balancer: 1, memory: 8},
            {id: 3, name: 'Environment', version: '10.3.6', nodes: 2, cpu: 2, type: 'Java Cloud Service Virtual Image', balancer: 1, memory: 8},
            {id: 4, name: 'Security', version: '10.3.6', nodes: 2, cpu: 2, type: 'Java Cloud Service Virtual Image', balancer: 1, memory: 8}
        ];

        self.Init = function () {
            $("#searchTable").ojTable("refresh");
            
            gVar.listData.employees.forEach(function (val, index, arr) {
            	if(!departmentMap[arr[index].department]) {
            		departmentMap[arr[index].department] = true;
            		self.departments.push({'id': 'department'+index, 'value': arr[index].department});
            	}	
            	if(!gradeMap[arr[index].grade_name]) {
            		gradeMap[arr[index].grade_name] = true;
            		self.grades.push({'id': 'grade'+index, 'value': arr[index].grade_name});
            	}
            	if(!productMap[arr[index].product]) {
            		productMap[arr[index].product] = true;
            		self.products.push({'id': 'product'+index, 'value': arr[index].product});
            	}
            	if(!functionMap[arr[index].func]) {
            		functionMap[arr[index].func] = true;
            		self.functions.push({'id': 'func'+index, 'value': arr[index].func});
            	}
            	if(!locationMap[arr[index].location]) {
            		locationMap[arr[index].location] = true;
            		self.locations.push({'id': 'location'+index, 'value': arr[index].location});
            	}

                if (arr[index].person_number === 126) {
                    arr[index].image = "css/images/person/" + arr[index].person_number.toString() + ".png";
                    arr[index].tempLink = ko.observable("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_1");

                } else if (arr[index].person_number === 131) {
                    arr[index].image = "css/images/person/" + arr[index].person_number.toString() + ".png";
                    arr[index].tempLink = ko.observable("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_2");
                } else if (arr[index].person_number === 141) {
                    arr[index].image = "css/images/person/" + arr[index].person_number.toString() + ".png";
                    arr[index].tempLink = ko.observable("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_3");
                } else {
                    arr[index].image = "css/images/person/default.png";
                    arr[index].tempLink = ko.observable("https://xxx/analytics");
                }

                arr[index].checkValue = ko.observable(["0"]);
                var cellObj = arr[index].highlights;
                arr[index].highhtml = "";

                for (var key in cellObj) {
                    for (var k = 0; k < cellObj[key].length; k++) {
                        if (key === "attach") {
                            arr[index].highhtml += "</br>";
                            var filename = '126_Interview_sheet_tamiya.pdf';
                            if (arr[index].person_number === 141) {
                                filename = '141_Interview_sheet_tamiya.docx';
                            }
                            arr[index].highhtml += '<a target="_blank" href ="' + filename + '">' + filename + '</a>' + cellObj[key][k];
                        } else {
                            arr[index].highhtml += "| " + key + "" + ":" + cellObj[key][k] + ".";
                        }
                    }
                }
            });
            
//            self.items().removeAll();
//            self.dataSource2(self.items, {idAttribute: "person_number"});
//            console.log("output:" + JSON.stringify(gVar.listData.employees));
//            setTimeout(function () {
            self.items(gVar.listData.employees);
            self.dataSource2 = new oj.ArrayTableDataSource(self.items, {idAttribute: "person_number"});

//            }, 1000);


        };


        self.searchAction = function (searchMe) {
            var ServiceURL = gVar.ServiceURL;
            $.ajax({
                type: "GET",
                url: ServiceURL + "?q=" + searchMe,
                async: false,
                success: function (data) {
                    gVar.listData = data;
                    self.Init();
                },
                error: function (err) {
                    alert(err.statusText);
                }
            });
        };

        //// NUMBER AND DATE CONVERTER ////
        var numberConverterFactory = oj.Validation.converterFactory("number");
        this.numberConverter = numberConverterFactory.createConverter();
        self.currentColor1 = ko.observableArray([""]);
        self.currentColor2 = ko.observableArray([""]);
        self.currentColor3 = ko.observableArray([""]);
        self.currentColor4 = ko.observableArray([""]);
        self.currentColor5 = ko.observableArray([""]);
        self.currentColor6 = ko.observableArray([""]);
        self.currentColor7 = ko.observableArray([""]);
        self.currentColor8 = ko.observableArray([""]);
        self.currentColor9 = ko.observableArray([""]);
        self.currentColor10 = ko.observableArray([""]);

        self.finalFilterText = "";

        self.filterButtonClick = function () {
        };

        self.applyButtonClick = function () {

            $('#popup1').ojPopup('close', '#btnGo');

            self.finalFilterText = "";

            console.log(self.currentColor1()[0]);
            if (self.currentColor1()[0]) {
                self.finalFilterText += "AND (";
                self.finalFilterText += "department:\"" + self.currentColor1()[0] + "\"";
                for (var i = 1; i < self.currentColor1().length; i++) {
                    self.finalFilterText += " OR " + "department:\"" + self.currentColor1()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor2()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "grade_name:\"" + self.currentColor2()[0] + "\"";
                for (var i = 1; i < self.currentColor2().length; i++) {
                    self.finalFilterText += " OR " + "grade_name:\"" + self.currentColor2()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor3()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "product:\"" + self.currentColor3()[0] + "\"";
                for (var i = 1; i < self.currentColor3().length; i++) {
                    self.finalFilterText += " OR " + "product:\"" + self.currentColor3()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor4()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "function:\"" + self.currentColor4()[0] + "\"";
                for (var i = 1; i < self.currentColor4().length; i++) {
                    self.finalFilterText += " OR " + "function:\"" + self.currentColor4()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor5()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "location:\"" + self.currentColor5()[0] + "\"";
                for (var i = 1; i < self.currentColor5().length; i++) {
                    self.finalFilterText += " OR " + "location:\"" + self.currentColor5()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor6()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "oversea_experience:\"" + self.currentColor6()[0] + "\"";
                for (var i = 1; i < self.currentColor6().length; i++) {
                    self.finalFilterText += " OR " + "oversea_experience:\"" + self.currentColor6()[i] + "\"";
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor7()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "age:" + self.currentColor7()[0];
                for (var i = 1; i < self.currentColor7().length; i++) {
                    self.finalFilterText += " OR " + "age:" + self.currentColor7()[i];
                }
                self.finalFilterText += ")";
            }

            if (self.currentColor8()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "hired_year:" + self.currentColor8()[0];
                for (var i = 1; i < self.currentColor8().length; i++) {
                    self.finalFilterText += " OR " + "hired_year:" + self.currentColor8()[i];
                }
                self.finalFilterText += ")";
            }
            if (self.currentColor9()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "years_worked:" + self.currentColor9()[0];
                for (var i = 1; i < self.currentColor9().length; i++) {
                    self.finalFilterText += " OR " + "years_worked:" + self.currentColor9()[i];
                }
                self.finalFilterText += ")";
            }
            if (self.currentColor10()[0]) {
                self.finalFilterText += " AND (";
                self.finalFilterText += "highest_education_level:" + self.currentColor10()[0];
                for (var i = 1; i < self.currentColor10().length; i++) {
                    self.finalFilterText += " OR " + "highest_education_level:" + self.currentColor10()[i];
                }
                self.finalFilterText += ")";
            }

            var newSearchtext = "(" + gVar.savedSearchText + ") " + self.finalFilterText;

            console.log(newSearchtext);

            self.searchAction(newSearchtext);
        };


        self.resetButtonClick = function () {
            self.currentColor1([""]);
            self.currentColor2([""]);
            self.currentColor3([""]);
            self.currentColor4([""]);
            self.currentColor5([""]);
            self.currentColor6([""]);
            self.currentColor7([""]);
            self.currentColor8([""]);
            self.currentColor9([""]);
            self.currentColor10([""]);

            console.log(gVar.savedSearchText);

            self.searchAction(gVar.savedSearchText);
            $('#popup1').ojPopup('close', '#btnGo');
        };



        self.handleActivated = function (info) {
            self.currentColor1([""]);
            self.currentColor2([""]);
            self.currentColor3([""]);
            self.currentColor4([""]);
            self.currentColor5([""]);
            self.currentColor6([""]);
            self.currentColor7([""]);
            self.currentColor8([""]);
            self.currentColor9([""]);
            self.currentColor10([""]);
        }

        self.handleAttached = function (info) {
        }

        self.handleBindingsApplied = function (info) {
        }

        self.handleDetached = function (info) {
            self.departments([]);
            self.grades([]);
            self.products([]);
            self.functions([]);
            self.locations([]);
            departmentMap = {};
            gradeMap = {};
            productMap = {};
            functionMap = {};
            locationMap = {};
        }

    }


    return new searchListContentViewModel;
});
