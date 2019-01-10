/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * empDetail module
 */
define(['ojs/ojcore', 'knockout', 'appVar', 'jquery', 'ojs/ojknockout', 'ojs/ojbutton'
], function (oj, ko, gVar, $) {
    /**
     * The view model for the main content view template
     */
    function empDetailContentViewModel() {
        var self = this;

        self.personArray = gVar.printPeople;
        self.peopleRadios = new Array();
        self.person = ko.observable("");

        self.de_image = ko.observable("");
        self.de_link = ko.observable("");
        self.de_department = ko.observable("");
        self.de_name = ko.observable("");
        self.de_grade = ko.observable("");
        self.de_step = ko.observable("");
        self.BI1 = ko.observable("");
        self.BI2 = ko.observable("");
        self.de_selfCom = ko.observable("");
        self.de_bossCom = ko.observable("");
        self.WE1 = ko.observable("");
        self.WE2 = ko.observable("");
        self.WE3 = ko.observable("");
        self.EH1 = ko.observable("");
        self.EH2 = ko.observable("");

        for (var k = 0; k < self.personArray.length; k++) {
            var tempObj = {
                id: self.personArray[k].person_number.toString(),
                label: self.personArray[k].name
            };
            self.peopleRadios.push(tempObj);

            if (k === 0) {
                self.person(tempObj.id);
            }
        }
        self.getByteLen = function (val) {
            var len = 0;
            for (var i = 0; i < val.length; i++) {
                if (val[i].match(/[^x00-xff]/ig) != null) //全角
                    len += 2;
                else
                    len += 1;
            }
            return len;
        };

        self.initView = function (obj) {

            console.log(obj);

            if (obj.person_number === 126) {
                self.de_image("css/images/person/" + obj.person_number.toString() + ".png");
                self.de_link("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_1");
            } else if (obj.person_number === 131) {
                self.de_image("css/images/person/" + obj.person_number.toString() + ".png");
                self.de_link("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_2");
            } else if (obj.person_number === 141) {
                self.de_image("css/images/person/" + obj.person_number.toString() + ".png");
                self.de_link("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_3");
            } else {
                 self.de_link("https://xxx");
                self.de_image("css/images/person/default.png");
            }

            var BIAC = "";
            var BITH = "";

            if (undefined !== obj.assignments[0]) {
                for (var k = 0; k < obj.assignments.length; k++) {
                    var singleLine = '<p>' + obj.assignments[k].start_date + '&nbsp&nbsp' + obj.assignments[k].department + '</p>';
                    BIAC += singleLine;
                }
            }


            if (undefined !== obj.trainings[0]) {
                for (var k = 0; k < obj.trainings.length; k++) {
                    var singleLine = '<p>' + obj.trainings[k].start_date + '-' + obj.trainings[k].end_date + '&nbsp&nbsp' + obj.trainings[k].training_course + '</p>';
                    BITH += singleLine;
                }
            }

            var WEPM = "";
            var WEJF = "";
            var WELo = "";

            if (undefined !== obj.profiles[0]) {
                for (var k = 0; k < obj.profiles.length; k++) {
                    var singlePMLine = '<p>' + obj.profiles[k].product_start_date + '-' + obj.profiles[k].product_end_date + '&nbsp&nbsp' + obj.profiles[k].product + '(' + obj.profiles[k].role + ')' + '</p>';
                    var singleJFLine = '<p>' + obj.profiles[k].func_start_date + '-' + obj.profiles[k].func_end_date + '&nbsp&nbsp' + obj.profiles[k].function + '</p>';
                    var singlelocLine = '<p>' + obj.profiles[k].loc_start_date + '-' + obj.profiles[k].loc_end_date + '&nbsp&nbsp' + obj.profiles[k].city + '</p>';
                    WEPM += singlePMLine;
                    WEJF += singleJFLine;
                    WELo += singlelocLine;
                }
            }

            var EHo = "";
            var EHen = "";

            if (undefined !== obj.evaluations[0]) {
                for (var k = 0; k < obj.evaluations.length; k++) {
                    var ratingString = obj.evaluations[k].rating;
                    var singleEHLine = '<p>' + obj.evaluations[k].performance_document + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + obj.evaluations[k].rating + '</p>';
                    if (self.getByteLen(ratingString) === 2) {
                        EHo += singleEHLine;
                    } else {
                        EHen += singleEHLine;
                    }
                }
            }

            self.de_department(obj.department);
            self.de_name(obj.name);
            self.de_grade(obj.grade_name);
            self.de_step(obj.grade_step_name);
            self.BI1(BIAC);
            self.BI2(BITH);
            self.de_selfCom(obj.emp_career_plan);
            self.de_bossCom(obj.manager_comment);
            self.WE1(WEPM);
            self.WE2(WEJF);
            self.WE3(WELo);
            self.EH1(EHo);
            self.EH2(EHen);
        };

        self.personCell = {};
        // observable bound to the Buttonset:
        self.initView(self.personArray[0]);

        // Note: optionChange handler not needed unless there's a need that is unmet
        // by the 2-way binding.
        this.handlePersonChange = function (event, ui) {
            if (ui.option === "checked") {
                // do stuff...
                for (var m = 0; m < self.personArray.length; m++) {
                    if (ui.value === self.personArray[m].person_number.toString()) {
                        console.log("new tab checked");
                        self.initView(self.personArray[m]);
                    }
                }
            }
        };
    }

    return empDetailContentViewModel;
});
